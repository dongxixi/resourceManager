package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.LogDao;
import com.lovo.hospital.entity.LogEntity;
import com.lovo.hospital.service.ILogService;
import com.lovo.hospital.util.DateUtil;
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
