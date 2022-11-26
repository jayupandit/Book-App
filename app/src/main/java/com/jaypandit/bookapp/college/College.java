package com.jaypandit.bookapp.college;

public class College {

    private String collegeName;
    private String board;
    private String  book;
    private String area;
    private String branch;
    private String state;
    private String country;

    public String getCollegeName() {
        return collegeName;
    }

    public College setCollegeName(String collegeName) {
        this.collegeName = collegeName;
        return this;
    }

    public String getBoard() {
        return board;
    }

    public College setBoard(String  board) {
        this.board = board;
        return this;
    }

    public String getBook() {
        return book;
    }

    public College setBook(String  book) {
        this.book = book;
        return this;
    }

    public String getArea() {
        return area;
    }

    public College setArea(String area) {
        this.area = area;
        return this;
    }

    public String getBranch() {
        return branch;
    }

    public College setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public String getState() {
        return state;
    }

    public College setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public College setCountry(String country) {
        this.country = country;
        return this;
    }
}
