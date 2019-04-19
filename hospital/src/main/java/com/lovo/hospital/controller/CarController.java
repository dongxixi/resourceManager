package com.lovo.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CarController {

	@RequestMapping("carAll")
	public String gCarAll(){
		return "carAll";
	}

	@RequestMapping("carAdd")
	public String carAdd(){
		return "carAdd";
	}
	@RequestMapping("carUpdate")
	public String carUpdate(){
		return "carUpdate";
	}

	@RequestMapping("index")
	public String index(){
		return "index";
	}

	@RequestMapping("thing")
	public String thing(){
		return "thing";
	}

	@RequestMapping("thingAll")
	public String thingAll(){
		return "thingAll";
	}

}
