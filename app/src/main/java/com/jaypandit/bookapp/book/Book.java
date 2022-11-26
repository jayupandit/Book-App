package com.jaypandit.bookapp.book;

public class Book {

    String classs;
    String bookName;
    String bookAuthor;
    String bookPublisher;
    String bookPublishYear;
    String bookPrize;
    String bookLanguage;

    public String getBookLanguage() {
        return bookLanguage;
    }

    public Book setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
        return this;
    }

    public String getClasss() {
        return classs;
    }

    public Book setClasss(String classs) {
        this.classs = classs;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public Book setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public Book setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
        return this;
    }

    public String getBookPublishYear() {
        return bookPublishYear;
    }

    public Book setBookPublishYear(String bookPublishYear) {
        this.bookPublishYear = bookPublishYear;
        return this;
    }

    public String getBookPrize() {
        return bookPrize;
    }

    public Book setBookPrize(String bookPrize) {
        this.bookPrize = bookPrize;
        return this;
    }
}
