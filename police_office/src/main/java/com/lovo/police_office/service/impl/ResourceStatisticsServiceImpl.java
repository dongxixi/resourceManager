package com.lovo.police_office.service.impl;


import com.lovo.police_office.dao.ResourceStatisticsDao;
import com.lovo.police_office.entity.ResourceStatisticsEntity;
import com.lovo.police_office.service.ResourceStatisticsService;
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
