package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.PensonnelDao;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PensonnelServiceImpl implements PersonnelService {

    @Autowired
    private PensonnelDao personnelDao;

    /**
     * 通过搜索条件和分页显示表格
     * @param pname 姓名
     * @param pnum 编号
     * @param position 职位
     * @param pageNum 当前页数
     * @param showNum 每页显示页数
     * @return
     */
    @Override
    public List<PersonnelEntity> getAllListBySearch(String pname, String pnum, String position, Integer pageNum, Integer showNum) {
        int startIndex=(pageNum-1)*showNum;
        return personnelDao.getAllListBySerch(pname,pnum,position,startIndex,showNum);
    }

    /**
     * 通过搜索条件查询数据总页数
     * @param pname 姓名
     * @param pnum 编号
     * @param position 职位
     * @param  showNum 每页显示条数
     * @return
     */
    @Override
    public int findTotalPageByCondition(String pname, String pnum, String position,Integer showNum) {
        int totalCount =personnelDao.findTotalPageByCondition(pname, pnum, position);
        Integer totalPage = (totalCount + showNum - 1) / showNum;
        return totalPage;
    }

    /**
     * 新增一条
     * @param pname
     * @param tel
     * @param sex
     * @param position
     * @return
     */
    @Override
    public PersonnelEntity saveOnePersonnel(String pname,String tel,String sex,String position) {
        PersonnelEntity personnelEntity = new PersonnelEntity();
        personnelEntity.setPnum("X001");
        personnelEntity.setPname(pname);
        personnelEntity.setTel(tel);
        personnelEntity.setSex(sex);
        personnelEntity.setPosition(position);
        personnelEntity.setState(2);
        return personnelDao.save(personnelEntity);
    }

    @Override
    public void deleteOne(String id) {

      personnelDao.deleteById(id);
    }

    @Override
    public PersonnelEntity selectOne(String id) {
      return  personnelDao.findById(id).get();
    }
}
