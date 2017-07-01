package com.crossit.collegeoffinearts.Tab.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentObject {


    public String contents;
    public String user_id;
    public String user_name;
    public String time;
    public String comment_id;

    public CommentObject()
    {

    }
    public CommentObject(String contents, String user_id, String user_name, String comment_id) {

        this.contents = contents;
        this.user_id = user_id;
        this.user_name = user_name;
        this.comment_id = comment_id;
        //현재 시간
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("YY-MM-dd");
        this.time = sdfNow.format(date);
    }

    public CommentObject(String contents, String user_id, String user_name) {

        this.contents = contents;
        this.user_id = user_id;
        this.user_name = user_name;
        //현재 시간
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("YY-MM-dd");
        this.time = sdfNow.format(date);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }
}
