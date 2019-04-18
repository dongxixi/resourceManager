package com.lovo.hospital.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class TestController {

	/**
	 * 跳到日照页面
	 * @return
	 */
	@RequestMapping("gotologPage")
	public String gotoMainPage(){
		return "showlog";
	}
	
}
