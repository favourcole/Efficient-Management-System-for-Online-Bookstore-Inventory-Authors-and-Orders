package edu.leicester.co2103.part1s2.domain;

import java.util.List;

public class OrderInput {
    private String customerName;
    private List<BookInput> bookList;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<BookInput> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookInput> bookList) {
        this.bookList = bookList;
    }
}
