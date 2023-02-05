package com.jaypandit.bookapp.master;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookSell {

    String bookSetName,schoolName,className,board;
    ArrayList<String> bookList,bookFreeList,bookTopicList;
    ArrayList<Integer> bookPaidList;
    int total,bookNo;


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("bookSetName", bookSetName);
        result.put("schoolName", schoolName);
        result.put("className", className);
        result.put("bookList", bookList);
        result.put("bookFreeList", bookFreeList);
        result.put("bookPaidList", bookPaidList);
        result.put("topicList",bookTopicList);
        result.put("total",total);
        result.put("bookNo",bookNo);
        result.put("board",board);

        return result;
    }

    public ArrayList<String> getBookTopicList() {
        return bookTopicList;
    }

    public BookSell setBookTopicList(ArrayList<String> bookTopicList) {
        this.bookTopicList = bookTopicList;
        return this;
    }

    public String getBoard() {
        return board;
    }

    public BookSell setBoard(String board) {
        this.board = board;
        return this;
    }

    public int getBookNo() {

        return bookNo;
    }

    public BookSell setBookNo(int bookNo) {
        this.bookNo = bookNo;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public BookSell setTotal(int total) {
        this.total = total;
        return this;
    }

    public String getBookSetName() {
        return bookSetName;
    }

    public BookSell setBookSetName(String bookSetName) {
        this.bookSetName = bookSetName;
        return this;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public BookSell setSchoolName(String schoolName) {
        this.schoolName = schoolName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public BookSell setClassName(String className) {
        this.className = className;
        return this;
    }

    public ArrayList<String> getBookList() {
        return bookList;
    }

    public BookSell setBookList(ArrayList<String> bookList) {
        this.bookList = bookList;
        return this;
    }

    public ArrayList<Integer> getBookPaidList() {
        return bookPaidList;
    }

    public BookSell setBookPaidList(ArrayList<Integer> bookPaidList) {
        this.bookPaidList = bookPaidList;
        return this;
    }

    public ArrayList<String> getBookFreeList() {
        return bookFreeList;
    }

    public BookSell setBookFreeList(ArrayList<String> bookFreeList) {
        this.bookFreeList = bookFreeList;
        return this;
    }
}
