package ink.alf.village.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 13793
 */
public class ActivitiPagerInfo implements Serializable {
    private int totalPages;
    private int pageCount;
    private int page;
    private int total;
    private List<ActivitiBean> lists;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ActivitiBean> getLists() {
        return lists;
    }

    public void setLists(List<ActivitiBean> lists) {
        this.lists = lists;
    }
}
