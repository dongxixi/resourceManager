package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.PensonnelDao;
import com.lovo.hospital.dao.ResourceStatisticsDao;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.entity.ResourceStatisticsEntity;
import com.lovo.hospital.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PensonnelServiceImpl implements PersonnelService {

    @Autowired
    private PensonnelDao personnelDao;
    @Autowired
    private ResourceStatisticsDao resourceStatisticsDao;

    /**
     * 通过搜索条件和分页显示表格
     * @param name 姓名
     * @param pnum 编号
     * @param position 职位
     * @param pageNum 当前页数
     * @param showNum 每页显示页数
     * @return
     */
    @Override
    public List<PersonnelEntity> getAllListBySearch(String name, String pnum, String position, Integer pageNum, Integer showNum) {
        int startIndex=(pageNum-1)*showNum;
        return personnelDao.getAllListBySerch(name,pnum,position,startIndex,showNum);
    }

    /**
     * 通过搜索条件查询数据总页数
     * @param name 姓名
     * @param pnum 编号
     * @param position 职位
     * @param  showNum 每页显示条数
     * @return
     */
    @Override
    public int findTotalPageByCondition(String name, String pnum, String position,Integer showNum) {
        int totalCount =personnelDao.findTotalPageByCondition(name, pnum, position);
        Integer totalPage = (totalCount + showNum - 1) / showNum;
        return totalPage;
    }

    /**
     * 新增一条
     * @param name
     * @param tel
     * @param sex
     * @param position
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PersonnelEntity saveOnePersonnel(String name,String pnum,String tel,String sex,Integer workTime,String position) {
        PersonnelEntity personnelEntity = new PersonnelEntity();
        personnelEntity.setPnum(pnum);
        personnelEntity.setName(name);
        personnelEntity.setTel(tel);
        personnelEntity.setSex(sex);
        personnelEntity.setWorkTime(workTime);
        personnelEntity.setPosition(position);
        //默认状态为未派出，0
        personnelEntity.setState(0);
        PersonnelEntity save = personnelDao.save(personnelEntity);
        //在资源表里面car加1
        ResourceStatisticsEntity rs = resourceStatisticsDao.findById("1").get();
        //在原有的基础上加1
        rs.setcVacantNum(rs.getpRescuingNum() + 1);
        resourceStatisticsDao.save(rs);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//事务回滚
    public void deleteOnePerson(String id) {

      personnelDao.deleteById(id);
        //在资源表里面car-1
        ResourceStatisticsEntity rs = resourceStatisticsDao.findById("1").get();
        //在原有的基础上-1
        rs.setcVacantNum(rs.getpRescuingNum() - 1);
        resourceStatisticsDao.save(rs);

    }

    @Override
    public PersonnelEntity selectOnePerson(String id) {
      return  personnelDao.findById(id).get();
    }

    @Override
    public PersonnelEntity updateOnePerson(PersonnelEntity personnelEntity) {
        return personnelDao.save(personnelEntity);

    }
}
