package com.lovo.hospital.service;

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
}
