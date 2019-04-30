package com.lovo.police_office.controller;

import com.lovo.police_office.bean.PaginationBean;
import com.lovo.police_office.entity.PersonnelEntity;
import com.lovo.police_office.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	@RequestMapping("personpage")
	@ResponseBody
	public PaginationBean<PersonnelEntity> personpage(String name, String pnum, String position, Integer currPage){
		int showNum =10;
		Integer state =null;
		List<PersonnelEntity>personnelEntityList =  personnelService.getAllListBySearch(name,pnum,position,state,currPage,showNum);
		int totalPage=personnelService.findTotalPageByCondition(name, pnum, position,showNum,state);

		PaginationBean<PersonnelEntity>paginationBean =new PaginationBean<>();
		paginationBean.setCurrPage(currPage);
		paginationBean.setDataList(personnelEntityList);
		paginationBean.setTotalPage(totalPage);
		return paginationBean;
	}



	@RequestMapping("personadd")
	public String personadd(){
		return "personadd";
	}


	/**
	 * 添加
	 * @return
	 */
	@RequestMapping("addperson")
	public ModelAndView addperson(String name,String tel,String sex,Integer workTime,String position){
		String telhead =tel.substring(0,3);
		String telend =tel.substring(7,11);
		Date d =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHH");
		String dd =sdf.format(d);
		String pnum=dd+telhead+telend;
		PersonnelEntity personnelEntity = personnelService.saveOnePersonnel(name,pnum, tel, sex,workTime, position);
		ModelAndView modelAndView = new ModelAndView("personpage");

		return modelAndView;
	}

	/**
	 * 单个信息详情界面
	 * @return
	 */
	@RequestMapping("selectperson")
	public ModelAndView personinfo(String id){
       PersonnelEntity personnelEntity =  personnelService.selectOnePerson(id);
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("personnelEntity",personnelEntity);//把获取的数据，添加到传输界面
        modelAndView.setViewName("personinfo");//传输界面指向显示界面
		return modelAndView;
	}

	/**
	 * 进入修改的页面
	 * @param id
	 * @return
	 */
	@RequestMapping("selectpersonupdate")
	public ModelAndView personinfoupdate(String id){
		PersonnelEntity personnelEntity =  personnelService.selectOnePerson(id);
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("personnelEntity",personnelEntity);//把获取的数据，添加到传输界面
		modelAndView.setViewName("personinfoupdate");//传输界面指向显示界面
		return modelAndView;
	}

	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("personupdate")
	public ModelAndView personupdate(String id,String name,String pnum,Integer workTime, String tel,String sex,String position){
		ModelAndView modelAndView = new ModelAndView();
		PersonnelEntity personnelEntity1 =new PersonnelEntity();
		personnelEntity1.setId(id);
		personnelEntity1.setName(name);
		personnelEntity1.setPnum(pnum);
		personnelEntity1.setWorkTime(workTime);
		personnelEntity1.setTel(tel);
		personnelEntity1.setSex(sex);
		personnelEntity1.setPosition(position);
		PersonnelEntity personnelEntity = personnelService.updateOnePerson(personnelEntity1);
		if(personnelEntity!=null){
			modelAndView.setViewName("personpage");
		}

		return modelAndView;
	}

	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("persondelete")
	@ResponseBody
	public String persondelete(String id){
		PersonnelEntity personnelEntity = personnelService.selectOnePerson(id);
		if (personnelEntity.getState()==0) {
			//该人员没有派出
			personnelService.deleteOnePerson(id);
			return "1";
		}else{
			return "-1";
		}
	}

	@RequestMapping("selecttel")
	@ResponseBody
	public boolean  findTelByTel(String tel){
		boolean findtel =  personnelService.findPersonnelEntitiesByTel(tel);
		return findtel;
	}

	/*@RequestMapping("selecttel2")
	@ResponseBody
	public Integer  findTelByTel2(String tel){
		Integer findtel =  personnelService.findTelByTel(tel);
		return findtel;
	}*/


}
