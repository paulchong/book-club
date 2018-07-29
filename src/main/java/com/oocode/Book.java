// updated by Paul Chong for the APE Final Assignment (July 2018)

package com.oocode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Book {
    private String name;

    //constructor
    public Book (String givenName){
        this.name = givenName;
    }


    public String getName(){
        return this.name;
    }

}
