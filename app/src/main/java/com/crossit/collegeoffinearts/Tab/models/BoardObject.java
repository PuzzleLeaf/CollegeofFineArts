package com.crossit.collegeoffinearts.Tab.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardObject {

    String title;
    String contents;
    String user_id;
    String user_email;
    String user_name;
    String time;
    String image;
    String imageDir;
    String board_id;
    String count;

    int tempChecker;



    public BoardObject()
    {

    }

    public BoardObject(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }


    public BoardObject(String title, String contents, String user_id, String user_email, String user_name, String board_id) {
        this.title = title;
        this.contents = contents;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;

        this.board_id = board_id;
        this.count = "1";
        this.image = "non";
        this.imageDir = "non";

        //현재 시간
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("YY-MM-dd");
        this.time = sdfNow.format(date);
    }
    public BoardObject(String title, String contents, String user_id, String user_email, String user_name, String board_id, String image, String imageDir) {
        this.title = title;
        this.contents = contents;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;

        this.board_id = board_id;
        this.count = "1";
        this.image = image;
        this.imageDir = imageDir;

        //현재 시간
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("YY-MM-dd");
        this.time = sdfNow.format(date);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBoard_id() {
        return board_id;
    }

    public void setBoard_id(String board_id) {
        this.board_id = board_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

    public int getTempChecker() {
        return tempChecker;
    }

    public void setTempChecker(int tempChecker) {
        this.tempChecker = tempChecker;
    }
}
