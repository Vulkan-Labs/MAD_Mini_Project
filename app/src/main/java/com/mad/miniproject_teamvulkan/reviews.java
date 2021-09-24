package com.mad.miniproject_teamvulkan;

public class reviews {

    private String name;
    private String comment;

    public reviews(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public reviews() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
