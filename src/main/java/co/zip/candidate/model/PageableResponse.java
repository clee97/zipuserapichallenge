package co.zip.candidate.model;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageableResponse<T> {

    private List<T> data;
    
    private int currentPage;
    
    private int pageSize;
    
    private int totalPages;
    
    private Integer prevPage;
    
    private Integer nextPage;
    
    private String prevLink;
    
    private String nextLink;
    
    public PageableResponse(Page<T> page) {
        data = page.toList();
        totalPages = page.getTotalPages();
        currentPage = page.getNumber();
        
        pageSize = page.getNumberOfElements();
        if (page.hasNext()) {
            nextPage = page.getNumber() + 1;
        }
        if (page.hasPrevious()) {
            prevPage = page.getNumber() - 1;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getPrevLink() {
        return prevLink;
    }

    public void setPrevLink(String prevLink) {
        this.prevLink = prevLink;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(Integer prevPage) {
        this.prevPage = prevPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }
    
}
