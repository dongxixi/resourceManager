package com.lovo.hospital.controller;
import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class PersonController {

	@Autowired
	private PersonnelService personnelService;
	/**
	 * 首页-条件查询分页显示界面
	 * @return
	 */
	/**
	 * 主界面
	 * @return
	 */
	@RequestMapping("personAll")
	public String personAll(){
		return "personpage";
	}
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("personadd")
	public String personadd(){
		return "personadd";
	}


	@RequestMapping("personpage")
	@ResponseBody
	public PaginationBean<PersonnelEntity> personpage(String pname, String pnum, String position,Integer currPage){
		int showNum =10;
		List<PersonnelEntity>personnelEntityList =  personnelService.getAllListBySearch(pname,pnum,position,currPage,showNum);
		int totalPage=personnelService.findTotalPageByCondition(pname, pnum, position,showNum);

		PaginationBean<PersonnelEntity>paginationBean =new PaginationBean<>();
		paginationBean.setCurrPage(currPage);
		paginationBean.setDataList(personnelEntityList);
		paginationBean.setTotalPage(totalPage);
		return paginationBean;
	}





	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("addperson")
	public ModelAndView addperson(String name,String tel,String sex,String position){
		PersonnelEntity personnelEntity = personnelService.saveOnePersonnel(name, tel, sex, position);
		ModelAndView modelAndView = new ModelAndView("personpage");
		return modelAndView;
	}

	/**
	 * 单个信息详情界面
	 * @return
	 */
	@RequestMapping("selectperson")
	public ModelAndView personinfo(String id){
       PersonnelEntity personnelEntity =  personnelService.selectOne(id);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("personnelEntity",personnelEntity);//把获取的数据，添加到传输界面
        modelAndView.setViewName("personinfo");//传输界面指向显示界面
		return modelAndView;
	}

	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("personupdate")
	public String personupdate(){
		return "personupdate";
	}

	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("persondelete")
	public ModelAndView persondelete(String id){
		personnelService.deleteOne(id);
		ModelAndView modelAndView = new ModelAndView("personpage");
		return modelAndView;

	}
}
