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

public class BookClub {
    // PC: this is the object (HashMap) that holds the book name (string) and list of reviews (list<string>)
    private final Map<String, List<String>> listMap = new HashMap<>();

    // PC: custom objects for books.  This will replace listMap
    private final Map<Book, List<String>> bookMap = new HashMap<>();

    //Creates Book object
//    public void createBook(String name){
//        bookMap.putIfAbsent(new Book(name), new ArrayList<>());
//    }
    public void addBookReview(Book book, String n) {
        bookMap.putIfAbsent(book, new ArrayList<>());
        bookMap.get(book).add(n);
    }
//    public int getNumBooks(){
//        return this.bookList.size();
//    }
//    public String printBookList(){
//        String output = "";
//        for(int i = 0; i < bookList.size(); i++){
//            output = output + (i+1) + ". " + bookList.get(i).getName();
//            output = output + "\n";
//        }
//        return output;
//    }


    public List<String> search2(String z) {
        if (z.equals("")) {
            throw new IllegalArgumentException();
        }
        if (z.toUpperCase().equals(z)) {
            System.out.print("hi");
            return bookMap.keySet().stream().map(Book::getName)
                    .filter(b -> Arrays.stream(b.split(" "))
                            .map(e -> ("" + e.charAt(0)).toLowerCase())
                            .collect(Collectors.joining())
                            .startsWith(z.toLowerCase()))
                    .sorted() // new code that sorts collection before keys are returned
                    .collect(Collectors.toList());
        }

        return bookMap.keySet().stream().map(Book::getName)
                .filter(b -> b.toLowerCase().startsWith(z.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }


    // PC: first check whether the book (z) already exists.  If it doesn't, then create a key for book (z).
    // PC: second, take the book (z) and add review (n) to it.
    public void addReview(String z, String n) {
        listMap.putIfAbsent(z, new ArrayList<>());
        listMap.get(z).add(n);
//        System.out.println(listMap); // PC test
    }

    // PC: return the list of reviews for book (z).  If book (z) doesn't exist, then return an emptyList
    public List<String> reviewsFor(String z) {
        return listMap.getOrDefault(z, Collections.emptyList());
    }

    // PC: Given a search term, return a list of book names, but only those which are classics.
    // PC: method is not to be changed for the search enhancement (Q2)
    public List<String> classics(String n) {
        return search(n).stream().filter(BookClub::isClassic).sorted()
                .collect(Collectors.toList());
    }

    public List<String> search(String z) {
        if (z.equals("")) {
            throw new IllegalArgumentException();
        }
        // PC: if the search term is all upper case, then conduct a search by book initials
        // PC: keySet returns a set of all the keys (book names) in listMap
        // PC: .stream() is functional code that iterates through the collection, in this case the list of book names
        // PC: .filter filters the items in the collection by the rules in its parentheses
        // PC: code in the filter parentheses:
        //      ".split(" ") tokenizes the book name (b) into unigrams (e). For each of those unigrams (e),
        //      map() takes the first letter of each unigram
        // PC: .joining() joins the initials into a string.  this joined string is then matched with the search term (z)
        // PC: collect() permits the transformation of elements within a stream, like converting to another object type,
        //      or manipulating it like summing, average, etc.  This case it converts the steam into a list object.
        if (z.toUpperCase().equals(z)) {
            return listMap.keySet().stream()
                    .filter(b -> Arrays.stream(b.split(" "))
                                    .map(e -> ("" + e.charAt(0)).toLowerCase())
                                    .collect(Collectors.joining())
                            .startsWith(z.toLowerCase()))
                    .sorted() // new code that sorts collection before keys are returned
                    .collect(Collectors.toList());
        }
        // PC: if search term isn't uppercase then get a set of all the keys (book names) in listMap
        // PC: stream() iterates through this set of keys
        // PC: .filter filters the items in the collection by the rules in its parentheses
        // PC: function in the parentheses takes each key (book name) converts it to lowercase, then checks whether it
        //      starts with the same letter as the search term.  If so, then it is added to the collection.
        // PC: .sorted() sorts the collection from the filter
        // PC: collect() transforms the collection into a list
        // PC: in sum, this returns a list of book names that start with the same letter as the term in the search query
        return listMap.keySet().stream()
                .filter(b -> b.toLowerCase().startsWith(z.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    // PC: calls external service with a book name, and returns whether or not the book is a classic
    // PC: uses URLConnection, which is an object that can be reused (i.e. for reading or writing)
    // PC: my guess is that this probably doesn't need to be changed too much, but worth thinking about.
    private static boolean isClassic(String z) {
        URLConnection conn;
        try {
            conn = new URL("https://pure-coast-78546.herokuapp.com/isClassic/"
                    + URLEncoder.encode(z, "UTF-8")).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"))
                    .contains("true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

