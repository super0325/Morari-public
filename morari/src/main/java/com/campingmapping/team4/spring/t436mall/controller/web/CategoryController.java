package com.campingmapping.team4.spring.t436mall.controller.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/Category")
public class CategoryController {

	@Autowired
	ProductOrderController pdoC;

	private HashMap<String, String> ReturnLinepay = new HashMap<String, String>();

	@GetMapping("/getlinepay.controller")
	public String getlinepayAction(
			@RequestParam("transactionId") String getlinepay) {

		String orderid = null;
		String orderprice = ReturnLinepay.get(getlinepay);
		// 建立連接
		try {
			URL url = new URL("https://sandbox-api-pay.line.me/v2/payments/"
					+ getlinepay + "/confirm");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// 設置請求方法
			con.setRequestMethod("POST");

			// 設置header，加入Content-Type:application/json
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-LINE-ChannelId", "1657918027");
			con.setRequestProperty("X-LINE-ChannelSecret",
					"2b4a8fd0185a8c0934e4f72b3dc529f1");

			String json;
			HashMap<String, String> giveLinepay = new HashMap<String, String>();
			giveLinepay.put("amount", orderprice);
			giveLinepay.put("currency", "TWD");

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			json = gson.toJson(giveLinepay);
			// 設置允許寫出
			con.setDoOutput(true);

			// 寫出json數據
			OutputStream os = con.getOutputStream();
			byte[] input = json.getBytes("utf-8");
			os.write(input, 0, input.length);
			os.close();

			// 讀取伺服器返回的數據
			BufferedReader br = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "utf-8"));
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
				String jsonStr = responseLine;
				try {
					// 把line回傳的orderid暫存
					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(jsonStr);
					orderid = root.path("info").path("orderId").asText();
					pdoC.updateProductOrderSatusById("已付款", orderid);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ProtocolException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return "mall/guest/selectorderbyuserid";
	}

	@GetMapping("/paybyline")
	@ResponseBody
	public String adasdfasdf(@RequestParam("JsonOrderList") String jsonData) {

		String json = jsonData;

		String paymentUrl = null;
		try {
			// 建立連接
			URL url = new URL(
					"https://sandbox-api-pay.line.me/v2/payments/request");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// 設置請求方法
			con.setRequestMethod("POST");

			// 設置header，加入Content-Type:application/json
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("X-LINE-ChannelId", "1657918027");
			con.setRequestProperty("X-LINE-ChannelSecret",
					"2b4a8fd0185a8c0934e4f72b3dc529f1");

			// 設置允許寫出
			con.setDoOutput(true);

			// 寫出json數據
			OutputStream os = con.getOutputStream();
			byte[] input = json.getBytes("utf-8");
			os.write(input, 0, input.length);
			os.close();

			// 讀取伺服器返回的數據
			BufferedReader br = new BufferedReader(
					new InputStreamReader(con.getInputStream(), "utf-8"));
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
				String jsonStr = responseLine;
				try {
					// 把line回傳的id暫存
					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(jsonStr);
					String transactionId = root.path("info")
							.path("transactionId").asText();
					ObjectMapper jmapper = new ObjectMapper();
					JsonNode data = jmapper.readTree(json);
					String amount = data.path("amount").asText();

					ReturnLinepay.put(transactionId, amount);
					paymentUrl = root.path("info").path("paymentUrl")
							.path("web").asText();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymentUrl;
	}

}
