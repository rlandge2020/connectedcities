package com.mastercard.connectedcities.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mastercard.connectedcities.service.ConnectedCitiesService;

/**
 * 
 * @author Rahul Landge
 *
 */
@Controller
@ComponentScan("com.mastercard.connectedcities.service")
public class ConnectedCitiesController {

	@Autowired
	ConnectedCitiesService connectedCitiesService;

	@RequestMapping("connected")
	public  @ResponseBody boolean connectedCities(@RequestParam("origin") String origin,
			@RequestParam("destination") String destination) throws UnsupportedEncodingException {
		System.out.println("Inside controller");
		return connectedCitiesService.areTheCitiesConnected(origin, destination);

	}
}
