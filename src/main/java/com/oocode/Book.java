// updated by Paul Chong for the APE Final Assignment (July 2018)

package com.oocode;

public class Book {
    private String name;

    //constructor
    public Book (String bookName){
        this.name = bookName;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return "Book Name: " + name + "\n";
    }

}
