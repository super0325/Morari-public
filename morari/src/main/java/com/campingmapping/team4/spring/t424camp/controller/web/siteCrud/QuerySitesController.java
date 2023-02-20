package com.campingmapping.team4.spring.t424camp.controller.web.siteCrud;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campingmapping.team4.spring.t424camp.model.entity.Site;
import com.campingmapping.team4.spring.t424camp.model.service.SiteService;

@Controller
@RequestMapping("/admin/camp")
public class QuerySitesController {

	@Autowired
	private SiteService siteService;
	
	
	@GetMapping("/SitesOfCamp")
	public String queryByCityIDsResult() {
		return "camp/admin/SitesOfCamp" ;
	}
	
	@GetMapping("/sitesOfCamp.controller/{campID}")
	@ResponseBody
	public Set<Site> queryByCamp(@PathVariable("campID") int campID, Model m) {

	    Set<Site> sites = siteService.findSiteByCampId(campID);
	    Set<Site> sortedSites = new TreeSet<Site>(new Comparator<Site>() {
	        @Override
	        public int compare(Site site1, Site site2) {
	            return Integer.compare(site1.getSiteID(), site2.getSiteID());
	        }
	    });
	    sortedSites.addAll(sites);

	    return sortedSites;
	}






}

