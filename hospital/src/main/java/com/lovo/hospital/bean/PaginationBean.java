package com.lovo.hospital.bean;

import java.util.List;

/**
 * 分页Bean
 * @author 狄亚宁
 * @param <T> 泛型
 */
public class PaginationBean<T> {	
	
	/**
	 * 页面数据
	 */
	private List<T> dataList;
	
	/**
	 * 当前第几页
	 */
	private Integer currPage;
	
	/**
	 * 总页数
	 */
	private Integer totalPage;
	
	

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
