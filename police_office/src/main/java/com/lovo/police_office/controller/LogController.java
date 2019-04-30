package com.lovo.police_office.controller;

import com.lovo.police_office.bean.PaginationBean;
import com.lovo.police_office.entity.LogEntity;
import com.lovo.police_office.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LogController {
    @Autowired
    private ILogService logService;
    @RequestMapping("showlog")
    public String showlog(){
        return "showlog";
    }

    @RequestMapping("findLog")
    @ResponseBody
    /**
     * 得到的时间类型是2019-12-31T23:59
     */
    public PaginationBean<LogEntity> findLog(String username, String description, String strTime, String endTime, int currPage){
        int showNum=10;
        List<LogEntity> allLogList = logService.findPageby(username, description, strTime, endTime, currPage, showNum);
        int totalPage = logService.findTotalPage(username, description, strTime, endTime);
        totalPage= (totalPage + showNum - 1) / showNum;
        //通过PaginationBean传数据
        PaginationBean<LogEntity> logEntityPaginationBean = new PaginationBean<>();
        logEntityPaginationBean.setCurrPage(currPage);
        logEntityPaginationBean.setTotalPage(totalPage);
        logEntityPaginationBean.setDataList(allLogList);
        return logEntityPaginationBean;
    }


}
