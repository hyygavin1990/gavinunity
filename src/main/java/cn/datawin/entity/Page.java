package cn.datawin.entity;

import java.util.List;

public class Page {
	private int pageNumber = 1;
	private int pageSize = 10;

	private long totalCount;
	private long totalPage;



	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public long getTotalPage() {
		return totalPage;
	}



	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 根据最大数量设定各参数
	 * 
	 * @param totalCount
	 */
	public void setParams(long totalCount) {
		this.totalCount = totalCount;
		long totalPage = (long) Math.ceil((double) totalCount / pageSize);
		this.totalPage = totalPage;
		this.pageNumber = (int) Math.max(Math.min(totalPage, pageNumber), 0);
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * 分割一个list
	 * 
	 * @param list
	 * @return
	 */
	public List<?> subList(List<?> list) {
		if (list != null) {
			setParams(list.size());
			int start = Math.min(Math.max(0, pageSize * (pageNumber - 1)), list.size());
			int end = Math.min(Math.max(0, start + pageSize), list.size());
			return list.subList(start, end);
		}
		return null;
	}

}