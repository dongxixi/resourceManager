package com.lovo.hospital.service;

import com.lovo.hospital.entity.LogEntity;

import java.util.List;

public interface ILogService {
    public List<LogEntity> findPageby(String username, String description, String strTime, String endTime, int currPage, int pageNum);
    public int findTotalPage(String username, String description, String strTime, String endTime);
    public void add(LogEntity logEntity);

}
