package com.lovo.hospital.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class PersonController {

	/**
	 * 跳到日照页面
	 * @return
	 */
	@RequestMapping("personpage")
	public String personpage(){
		return "personpage";
	}

	@RequestMapping("personadd")
	public String personadd(){
		return "personadd";
	}

	@RequestMapping("personinfo")
	public String personinfo(){
		return "personinfo";
	}

	@RequestMapping("personupdate")
	public String personupdate(){
		return "personupdate";
	}
	@RequestMapping("circumstantiality")
	public String circumstantiality(){
		return "circumstantiality";
	}
}
