package com.lovo.fire_company.dao;


import com.lovo.fire_company.entity.ResourceStatisticsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * 用户车辆库存
 * @author 狄亚宁
 */
public interface ResourceStatisticsDao extends CrudRepository<ResourceStatisticsEntity,String> {

}
