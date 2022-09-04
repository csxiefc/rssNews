package net.mingsoft.cms.action.search.utils;

public class PageUtil {
	
    /**
     * 总页数
     * @param allCount	总条目数
     * @param rows	每页行数
     * @return	总页数
     */
    public static long getPageCount(long allCount, long rows) {
    	long size = allCount/rows;//总条数/每页显示的条数=总页数
    	long mod = allCount % rows;//最后一页的条数
        if(mod != 0)
            size++;
        return allCount == 0 ? 1 : size;
    }
    
    /**
     * 获取开始位置 包含，起始索引从0开始 
     * @param currentsize	当前页
     * @param rows	每页行数
     * @return	开始位置
     */
    public static long getFromIndex(long currentPage, long rows) {
        return (currentPage-1) * rows;
    }
    
    /** 获取结束位置 包含，起始索引从0开始
     * @param allCount	总条目数
     * @param currentPage	当前页
     * @param rows	每页行数
     * @return	结束位置
     */
    public static long getToIndex(long allCount, long currentPage, long rows) {
        return  Math.min(allCount, currentPage * rows);
    }
    
    /**
     * 获取当前页
     * @param start	开始位置
     * @param rows	每页行数
     * @return	当前页
     */
    public static long getCurrentPage(long start, long rows){
    	return start/rows+1;
    }

}
