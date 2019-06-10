package com.cqut.icode.entities;

/**
 * @author 谭强
 * @date 2019/6/7
 */
public class Page {
    /** client 指定*/
    private Integer limit;
    /** client 指定*/
    private Integer offset;
    /** 获取到多少条数据*/
    private Integer size;


    /** 总共多少条数据*/
    private Long total;

    private String sortField;

    private String sortOrder;

    public Page(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;

        sortOrder = "asc";
    }

    public String getLimitAndOffset() {
        return " limit " + limit + " offset " + offset;
    }

    public String getSort() {
        System.out.println("`````````");
        System.out.println(sortField);
        if (sortField != null && !"".equals(sortField)) {
            return " order by " + sortField + " " + sortOrder;
        }

        return "";
    }

    @Override
    public String toString() {
        return "{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", size=" + size +
                ", total=" + total +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }

    public Page(Integer limit, Integer offset, String sortField, String sortOrder) {
        this.limit = limit;
        this.offset = offset;

        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
