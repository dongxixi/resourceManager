package com.lovo.hospital.controller;

import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.CarService;
import com.lovo.hospital.service.DispatchService;
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

    @RequestMapping("eventGetCarList")
    @ResponseBody
    public Map<String, Object> getCarList(Integer pageNo) {

        List<CarEntity> carList = carService.findCarByCondition(pageNo, 5, null, null, 0);
        int pageTotal = carService.findTotalPageByCondition(null, null, null, 5);

        Map<String, Object> map = new HashMap<>();
        map.put("pageTotal", pageTotal);
        map.put("list", carList);

        return map;
    }

    @RequestMapping("eventGetPersonList")
    @ResponseBody
    public Map<String, Object> getPersonList(Integer pageNo ,Integer state) {

        List<PersonnelEntity> personList = personService.getAllListBySearch(null, null, null, state,pageNo, 5);
        int pageTotal = personService.findTotalPageByCondition(null, null, null, 5,state);

        Map<String, Object> map = new HashMap<>();
        map.put("pageTotal", pageTotal);
        map.put("list", personList);

        return map;
    }

    @Autowired
    private DispatchService dispatchService;

    @RequestMapping("eventDispatch")
    public ModelAndView dispatch(String dispatchId, String personInCharge, String personUpdateList, String carUpdateList) {
        ModelAndView mv = new ModelAndView();

        int flag = dispatchService.dispatch(dispatchId, personInCharge, personUpdateList, carUpdateList);

        String msg = "";
        switch (flag) {
            case 0:
//                msg = "派出成功";
                break;
            case 1:
                msg = "派出单错误";
//                break;
            case 2:
                msg = "车辆数量错误";
//                break;
            case 3:
                msg = "人员数量错误";
//                break;
        mv.addObject("msg", msg);
        }

        if (flag == 0) {
            mv.setViewName("redirect:/eventAll");
        } else {
            mv.setViewName("redirect:/eventError");
        }
        return mv;
    }
}
