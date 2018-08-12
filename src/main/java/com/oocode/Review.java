package com.oocode;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Review {
    private String reviewPost;
    private Date postDate;
    private String expectedPattern = "dd/MM/yyyy";
    private SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);

    //constructor - post only
    public Review (String post){
        this.reviewPost = post;
        this.postDate = new Date();
    }

    //constructor - post and date
    public Review (String post, String date){
        this.reviewPost = post;
        try {
            this.postDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPost(){
        return this.reviewPost;
    }
    public Date getDate() { return this.postDate; }

    @Override
    public String toString() {
        return "Date: " + formatter.format(postDate)+ "\nPost: " + reviewPost;
    }



}
