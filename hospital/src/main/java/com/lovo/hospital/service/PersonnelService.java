package com.lovo.hospital.service;

import com.lovo.hospital.entity.PersonnelEntity;

import java.util.List;

public interface PersonnelService {
    /**
     *
     * @param name 姓名
     * @param pnum 编号
     * @param position 职位
     * @param pageNum 当前页数
     * @param showNum 每页显示页数
     * @return
     */
    public List<PersonnelEntity> getAllListBySearch(String name, String pnum, String position,Integer pageNum,Integer showNum);

    /**
     * 通过搜索条件查询出来的总页数
     * @param name 姓名
     * @param pnum 编号
     * @param position 职位
     * @param  showNum 每页显示条数
     * @return 总条数
     */
    public  int findTotalPageByCondition(String name, String pnum, String position,Integer showNum);


    /**
     * 新增一个
     * @param name
     * @param tel
     * @param sex
     * @param position
     * @return
     */
    public  PersonnelEntity saveOnePersonnel(String name,String tel,String sex,String position);

    /**
     * 删除一条数据
     * @param id
     */
    public void deleteOne(String id);

    /**
     * 查看一条数据
     * @param id
     */
    public PersonnelEntity selectOne(String id);

    /**
     * 修改一条数据
     * @param personnelEntity
     * @return
     */
    public PersonnelEntity updateOne(PersonnelEntity personnelEntity);


}
