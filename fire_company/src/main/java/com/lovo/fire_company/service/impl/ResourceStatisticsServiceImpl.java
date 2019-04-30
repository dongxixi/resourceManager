package com.lovo.fire_company.service.impl;


import com.lovo.fire_company.dao.ResourceStatisticsDao;
import com.lovo.fire_company.entity.ResourceStatisticsEntity;
import com.lovo.fire_company.service.ResourceStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "resourceStatisticsService")
public class ResourceStatisticsServiceImpl implements ResourceStatisticsService {
    @Autowired
    private ResourceStatisticsDao resourceStatisticsDao;

    @Override
    public ResourceStatisticsEntity getResourceStatisticsEntity() {
        List<ResourceStatisticsEntity> resourceStatisticsEntityList = (List<ResourceStatisticsEntity>) resourceStatisticsDao.findAll();
        return resourceStatisticsEntityList.get(0);
    }
}
