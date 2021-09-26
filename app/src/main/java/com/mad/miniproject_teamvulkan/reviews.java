package com.mad.miniproject_teamvulkan;

public class reviews {

    private String name , revID;
    private String comment;
    private String proName;

    public reviews(String name, String revID, String comment, String proName) {
        this.name = name;
        this.revID = revID;
        this.comment = comment;
        this.proName = proName;
    }
//    public reviews(String name, String revID, String comment) {
//        this.name = name;
//        this.revID = revID;
//        this.comment = comment;
//    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getRevID() {
        return revID;
    }

    public void setRevID(String revID) {
        this.revID = revID;
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
