package com.campingmapping.team4.spring.t436mall.controller.web;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.campingmapping.team4.spring.utils.config.GoogleFileUtil;

@Controller
public class GFUController {

	@PostMapping("/uploadpicturetogoogle.controller")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		String fileName="pd"+UUID.randomUUID().toString();
		return GoogleFileUtil.uploadFile(fileName,file);
	}
}
