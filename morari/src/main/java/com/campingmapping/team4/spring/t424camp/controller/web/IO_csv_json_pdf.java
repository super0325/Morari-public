package com.campingmapping.team4.spring.t424camp.controller.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.dao.repository.OrderRepository;
import com.campingmapping.team4.spring.t424camp.model.entity.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

@Controller
@RequestMapping("/admin/camp")
public class IO_csv_json_pdf {
	
	@Autowired
	private OrderRepository orderRepository;

	
	//output csv
	public void exportToCsv() throws IOException {
	    List<Order> orderList = orderRepository.findAll();
	    StringBuilder orderData = new StringBuilder();
	    for (Order order : orderList) {
	        orderData.append(order.toString());
	        orderData.append("\n");
	    }
	    try (Writer writer = new OutputStreamWriter(new FileOutputStream("C:/Users/User/Desktop/Order.csv"), "UTF-8")) {
	        writer.write(orderData.toString());
	    } catch (UnsupportedEncodingException e1) {
	        e1.printStackTrace();
	    } catch (FileNotFoundException e1) {
	        e1.printStackTrace();
	    } catch (java.io.IOException e1) {
	        e1.printStackTrace();
	    }
	}
	//output csv
	@GetMapping("/outputCsv")
	public String outputFileCsv() throws IOException {
		exportToCsv();
		return "camp/admin/AdminOrderIndex";
	}
	
	
	//output json
	@GetMapping("/outputJson")
	public String outputFileJson() {
		List<Order> orderList = orderRepository.findAll();
		ObjectMapper objectMapper = new ObjectMapper();
		try (FileWriter writer = new FileWriter("C:/Users/User/Desktop/Order.json")) {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, orderList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (java.io.IOException e1) {
			e1.printStackTrace();
		}
		return "camp/admin/AdminOrderIndex";
	}

	
	//output pdf
	@GetMapping("/outputPdf")
	@ResponseBody
	public  byte[] searchorder(@RequestParam("key") String value) throws IOException {
		
		FileInputStream inputStream;
		byte[] data = null;
		try {
			inputStream = new FileInputStream("src/main/resources/static/font/font.txt");
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	
}
