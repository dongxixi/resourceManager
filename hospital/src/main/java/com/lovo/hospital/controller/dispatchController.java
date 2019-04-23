package com.lovo.hospital.controller;

import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.CarService;
import com.lovo.hospital.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class dispatchController {

    @Autowired
    private CarService carService;

    @Autowired
    private PersonnelService personService;

    @RequestMapping("getCarList")@ResponseBody
    public Map<String, Object> getCarList(Integer pageNo) {

        List<CarEntity> carList = carService.findCarByCondition(pageNo, 2, null, null, null);
        int pageTotal = carService.findTotalPageByCondition(null, null, null, 2);

        Map<String, Object> map = new HashMap<>();
        map.put("pageTotal", pageTotal);
        map.put("list", carList);

        return map;
    }

    @RequestMapping("getPersonList")@ResponseBody
    public Map<String, Object> getPersonList(Integer pageNo) {

        List<PersonnelEntity> personList = personService.getAllListBySearch(null, null, null, pageNo,2);
        int pageTotal = personService.findTotalPageByCondition(null, null, null, 2);

        Map<String, Object> map = new HashMap<>();
        map.put("pageTotal", pageTotal);
        map.put("list", personList);

        return map;
    }

    @RequestMapping("dispatch")
    public ModelAndView dispatch(String thingId, String personInCharge, String personUpdateList, String carUpdateList) {
        ModelAndView mv = new ModelAndView();


        return mv;
    }
}
