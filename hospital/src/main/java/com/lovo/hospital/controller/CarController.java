package com.lovo.hospital.controller;

import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CarController {
	@Autowired
	private CarService carService;

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

	@RequestMapping("findCarByCondition")
	@ResponseBody
	public PaginationBean<CarEntity> findCarByCondition(int currPage, String carNum, String dirverName, int state){

		//每页显示10行
		int showNum=10;
		List<CarEntity> carList = carService.findCarByCondition(currPage, showNum, carNum, dirverName, state);
		int totalPage = carService.findTotalPageByCondition(carNum, dirverName, state);

		PaginationBean<CarEntity> paginationBean=new PaginationBean<>();
		paginationBean.setCurrPage(currPage);
		paginationBean.setDataList(carList);
		paginationBean.setTotalPage(totalPage);
		return paginationBean;
	}
}
