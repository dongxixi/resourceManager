package com.lovo.hospital.controller;

import com.alibaba.fastjson.JSONObject;
import com.lovo.hospital.dto.ResourceStatisticsDto;
import com.lovo.hospital.entity.ResourceStatisticsEntity;
import com.lovo.hospital.service.ResourceStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;

@Controller
public class ResourceStatisticsController {
    @Autowired
    private ResourceStatisticsService resourceStatisticsService;

    @RequestMapping("/getResourceStatisticsJson")
    @ResponseBody
    public String getResourceStatisticsJson(){
        ResourceStatisticsEntity resourceStatisticsEntity = resourceStatisticsService.getResourceStatisticsEntity();
        ResourceStatisticsDto resourceStatisticsDto = new ResourceStatisticsDto();
        resourceStatisticsDto.setId(resourceStatisticsEntity.getId());
        resourceStatisticsDto.setPRescuingNum(String.valueOf(resourceStatisticsEntity.getpRescuingNum()));
        resourceStatisticsDto.setCVacantNum(String.valueOf(resourceStatisticsEntity.getcVacantNum()));
        String strJson = JSONObject.toJSONString(resourceStatisticsDto);
        return strJson;
    }
}
