package com.campingmapping.team4.spring.t424camp.controller.web;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Camp;
import com.campingmapping.team4.spring.t424camp.model.entity.Site;
import com.campingmapping.team4.spring.t424camp.model.service.CampService;
import com.campingmapping.team4.spring.t424camp.model.service.SiteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GetPictureController {

	@Autowired
	private CampService campService;

	@Autowired
	private SiteService siteService;

	@GetMapping(path = "/getCampPicture/{campID}", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public byte[] getCampPicture1(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("campID") int campID) throws IOException {
		Camp camp = campService.findById(campID);
		String fileName = camp.getCampPicturesPath();

		InputStream in = request.getServletContext().getResourceAsStream("/WEB-INF/resources/images/" + fileName);
		return IOUtils.toByteArray(in);
	}

	@GetMapping(path = "/getSitePicture/{siteID}", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public byte[] getSitePicture1(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("siteID") int siteID) throws IOException {
		Site site = siteService.findBySiteId(siteID);
		String fileName = site.getSitePicturesPath();

		InputStream in = request.getServletContext().getResourceAsStream("/WEB-INF/resources/images/" + fileName);
		return IOUtils.toByteArray(in);
	}

}
