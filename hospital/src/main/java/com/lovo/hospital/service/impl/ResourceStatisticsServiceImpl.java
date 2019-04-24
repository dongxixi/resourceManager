package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.ResourceStatisticsDao;
import com.lovo.hospital.entity.ResourceStatisticsEntity;
import com.lovo.hospital.service.ResourceStatisticsService;
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
