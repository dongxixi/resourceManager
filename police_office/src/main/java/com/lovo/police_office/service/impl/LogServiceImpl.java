package com.lovo.police_office.service.impl;


import com.lovo.police_office.dao.LogDao;
import com.lovo.police_office.entity.LogEntity;
import com.lovo.police_office.service.ILogService;
import com.lovo.police_office.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service(value = "logService")
public class LogServiceImpl implements ILogService {
    @Autowired
    private LogDao logDao;

    @Override
    public List<LogEntity> findPageby(String username, String description, String strTime, String endTime, int currPage, int pageNum) {
        int startIndex = (currPage - 1) * pageNum;
        Timestamp strTimeTamp = DateUtil.stringToTimestamp(strTime);
        Timestamp endTimeTamp = DateUtil.stringToTimestamp(endTime);
        return logDao.findPageby(username, description, strTimeTamp, endTimeTamp, startIndex, pageNum);
    }

    @Override
    public int findTotalPage(String username, String description, String strTime, String endTime) {
        Timestamp strTimeTamp = DateUtil.stringToTimestamp(strTime);
        Timestamp endTimeTamp = DateUtil.stringToTimestamp(endTime);
        return logDao.findTotalPage(username, description, strTimeTamp, endTimeTamp);
    }

    @Override
    public void add(LogEntity logEntity) {
        logDao.save(logEntity);
    }

}
