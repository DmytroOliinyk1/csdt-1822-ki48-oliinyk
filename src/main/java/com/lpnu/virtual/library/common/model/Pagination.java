package com.lpnu.virtual.library.common.model;

import lombok.Data;

@Data
public class Pagination {
    public static final Integer DEFAULT_PAGE_SIZE = 5;

    private Integer page;
    private Integer nextPage;
    private Integer prevPage;
    private Integer pageSize;
    private String searchId;
    private Integer size;

    public Pagination() {
        this.page = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.nextPage = this.page;
        this.prevPage = this.page;
    }

    public Pagination(Integer page, String searchId) {
        this.page = page;
        this.searchId = searchId;
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.nextPage = this.page;
        this.prevPage = 1;
        if (this.page > 1) {
            this.prevPage = this.page - 1;
        }
    }

    public Integer getPages() {
        return (int) Math.ceil((double) this.getSize() / (double) this.getPageSize());
    }

    public void increasePage() {
        this.page += 1;
    }

    public Integer getNext() {
        return this.page + 1;
    }

    public Integer getPrev() {
        return this.page - 1;
    }

    public Boolean isLastPage() {
        Integer lastPage = (int) Math.ceil(Double.valueOf(this.size) / Double.valueOf(this.pageSize));
        Boolean res = this.size > 0 && this.page >= lastPage;
        return this.page >= lastPage;
    }

    public Boolean isFirstPage() {
        return this.page == 1;
    }

    public Boolean hasElements() {
        return this.size <= 0;
    }
}
