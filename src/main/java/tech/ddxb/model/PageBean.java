package tech.ddxb.model;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * sql search page bean
 * 自定义返回PageBean，替代其自带的PageInfo.java
 * <p>
 * 因为PageInfo.java只是一个示例，
 * 所以他定义得有点重量级，属性有点多，而且定义的数据和工程之前的数据不一致，
 * 我们可以参考它，定义适合我们自己的PageBean
 *
 * @param <T>
 * @author hxp
 * @date 2016年11月9日
 */
public class PageBean<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //	private int ret = 0;
//    private String msg = "ok";
    // 当前页码
    private int current_page = 1;
    // 每页记录数
    private int page_size = 10;
    // 总页数
    private int total_pages = 1;
    // 总记录数
    private long total_rows = 1;
    private boolean has_previous_page = false;
    private boolean has_next_page = true;
    // 当前页的记录数量
    private int size;
    // 结果集
    List<T> data;

    /**
     * 包装Page对象，因为直接返回Page对象，在JSON处理以及其他情况下会被当成List来处理，
     * 而出现一些问题。
     *
     * @param list page结果
     */
    public PageBean(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.current_page = page.getPageNum();
            this.page_size = page.getPageSize();
            this.total_rows = page.getTotal();
            this.total_pages = page.getPages();
            this.has_previous_page = current_page > 1;
            this.has_next_page = current_page < total_pages;
            this.size = page.size();
            this.data = page;
        }
    }

//	public int getRet() {
//		return ret;
//	}
//
//	public void setRet(int ret) {
//		this.ret = ret;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public long getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(long total_rows) {
        this.total_rows = total_rows;
    }

    public boolean isHas_previous_page() {
        return has_previous_page;
    }

    public void setHas_previous_page(boolean has_previous_page) {
        this.has_previous_page = has_previous_page;
    }

    public boolean isHas_next_page() {
        return has_next_page;
    }

    public void setHas_next_page(boolean has_next_page) {
        this.has_next_page = has_next_page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
