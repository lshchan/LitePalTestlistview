package com.example.litepaltest;

/**
 * Created by Administrator on 2017-2-24.
 */

public class Bookview {
    private int id;
    private String name;
    private String author;

    public Bookview(int id,String name,String author){
        this.id=id;
        this.name=name;
        this.author=author;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getAuthor (){
        return author;
    }
}
