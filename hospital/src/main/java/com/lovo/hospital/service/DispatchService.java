package com.lovo.hospital.service;

import com.lovo.hospital.entity.DispatchEntity;

import java.util.List;

public interface DispatchService {
    /**
     *
     * @param dispatchId 派出单编号
     * @param personInCharge
     * @param personUpdateList
     * @param carUpdateList
     * @return  1:表示没有找到对应派出单
     *          2:表示选择的车辆数量不正确
     *          3:表示选择的入数不正确
     *          0:表示派出成功
     */
    int dispatch(String dispatchId, String personInCharge, String personUpdateList, String carUpdateList);

    DispatchEntity getDispatchById(String id);

    /**
     * 通过事件ID得到该事件的资源请求集合
     * @param id 事件ID
     * @return
     */
    List<DispatchEntity> getDispatchByEventId(String id, Integer state);

}
