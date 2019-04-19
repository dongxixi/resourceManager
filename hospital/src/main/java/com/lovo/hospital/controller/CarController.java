package com.lovo.hospital.controller;

import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.service.CarService;
import javafx.beans.binding.BooleanBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * carController
 *
 * @author 狄亚宁
 */
@Controller
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping("carAll")
    public String gCarAll() {
        return "carAll";
    }

    @RequestMapping("carAdd")
    public String carAdd() {
        return "carAdd";
    }

    @RequestMapping("carUpdate")
    public String carUpdate() {
        return "carUpdate";
    }

    /**
     * 查询所有car带条件
     *
     * @param currPage 当前页
     * @param carNum   车牌
     * @param driver   司机
     * @param state    状态
     * @return PaginationBean
     */
    @RequestMapping("findCarByCondition")
    @ResponseBody
    public PaginationBean<CarEntity> findCarByCondition(Integer currPage, String carNum, String driver, Integer state) {

        //每页显示10行
        int showNum = 10;
        List<CarEntity> carList = carService.findCarByCondition(currPage, showNum, carNum, driver, state);
        int totalPage = carService.findTotalPageByCondition(carNum, driver, state, showNum);

        PaginationBean<CarEntity> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
        paginationBean.setDataList(carList);
        paginationBean.setTotalPage(totalPage);
        return paginationBean;
    }

    /**
     * 添加car
     *
     * @param carNum 车牌
     * @param driver 司机
     * @return
     */
    @RequestMapping("addCar")
    public ModelAndView addCar(String carNum, String driver) {
        CarEntity carEntity = carService.saveCar(carNum, driver);

        ModelAndView modelAndView = new ModelAndView("carAll");
        return modelAndView;
    }

    /**
     * car详情
     *
     * @param id
     * @return car bean
     */
    @RequestMapping("updateInfoCar")
    public ModelAndView updateInfoCar(String id) {
        CarEntity carEntity = carService.infoCarById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("carUpdate");
        modelAndView.addObject("car", carEntity);
        return modelAndView;
    }

    @RequestMapping("updateCar")
    public ModelAndView updateCar(String id, String driver,String carNum,Integer state) {
        CarEntity carEntity1 = new CarEntity();
        carEntity1.setId(id);
        carEntity1.setState(state);
        carEntity1.setDriver(driver);
        carEntity1.setCarNum(carNum);
        CarEntity carEntity = carService.updateCar(carEntity1);
        ModelAndView modelAndView = new ModelAndView();
        if (carEntity != null) {
            modelAndView.setViewName("carAll");
        }
        return modelAndView;
    }

    @RequestMapping("deleteCar")
    public ModelAndView deleteCar(String id){
        carService.deleteCar(id);
        ModelAndView modelAndView=new ModelAndView("carAll");
        return modelAndView;
    }
}
