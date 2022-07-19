package org.example.logic;

import org.example.models.Book;
import org.example.models.Person;

public class Paging {
    private int page;

    private int itemsPerPage;

    private boolean sortByYear;

    private String sortBy;

    private boolean hasNext;

    private boolean hasPrevious;

    private Paging() {
    }

//    private Paging(int page, int itemsPerPage, Boolean sortByYear, String sortBy, boolean hasNext, boolean hasPrevious) {
//        this.page = page;
//        this.itemsPerPage = itemsPerPage;
//        this.sortByYear = sortByYear;
//        this.sortBy = sortBy;
//        this.hasNext = hasNext;
//        this.hasPrevious = hasPrevious;
//    }

//    public static Paging pageFabric(Object page, Object itemsPerPage, Object sortByYear, int size, Class clazz) {
//        String sortBy = "fullName";
//        if (sortByYear == null)
//            sortByYear = false;
//        if (sortByYear.equals(true))
//            sortBy = "birthYear";
//
//
//        if (page == null)
//            page = 0;
//        if (itemsPerPage == null)
//            itemsPerPage = 15;
//
//        boolean hasNext = (Integer) page < size / ((Integer) itemsPerPage + 1);
//        boolean hasPrevious = (Integer) page > 0;
//
//        return new Paging((Integer) page, (Integer) itemsPerPage, (Boolean) sortByYear, sortBy, hasNext, hasPrevious);
//    }

    public static Paging pageFabric(Object page, Object itemsPerPage, Object sortByYear, int size, Class clazz) {
        Paging paging = new Paging();
        paging.sortCheck(sortByYear, clazz);
        paging.pageCheck(page, itemsPerPage, size);

        return paging;
    }

    public void sortCheck(Object sortByYear, Class clazz) {
        if (sortByYear == null)
            sortByYear = false;
        this.sortByYear = (Boolean) sortByYear;

        if (clazz == Person.class) {
            this.sortBy = "fullName";
            if (sortByYear.equals(true))
                sortBy = "birthYear";
        }
        if (clazz == Book.class) {
            this.sortBy = "title";
            if (sortByYear.equals(true))
                sortBy = "year";
        }
    }
    public void pageCheck(Object page, Object itemsPerPage, int size) {
        if (page == null)
            page = 0;
        this.page = (Integer) page;

        if (itemsPerPage == null)
            itemsPerPage = 15;
        this.itemsPerPage = (Integer) itemsPerPage;

        this.hasNext = this.page < size / (this.itemsPerPage + 1);
        this.hasPrevious = this.page > 0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Boolean getSortByYear() {
        return sortByYear;
    }

    public void setSortByYear(Boolean sortByYear) {
        this.sortByYear = sortByYear;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(Boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}
