package am.aua.com.dbproject.Utils;

import java.io.Serializable;


public class BookItem implements Serializable {
    private int id;
    private String name;
    private String title;
    private String author;
    private int year;
    private String description;
    private int pages;
    private String coverUrl;
    private int total_amount;
    private int available_amount;
    private String first_name;
    private String last_name;

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {

        this.first_name = first_name;
    }

    public String getFirst_name() {

        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

//    private int sub_category;


    public BookItem(String name, String title, String author, int year, int sub_category,String description,int qunatity) {
        this.name = name;
        this.title = title;
        this.author = author;
        this.year = year;
        this.available_amount =qunatity;
        this.description = description;
//        this.sub_category = sub_category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return first_name + " " + last_name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailable_amount() {
        return available_amount;
    }

    public void setAvailable_amount(int available_amount) {
        this.available_amount = available_amount;
    }
}
