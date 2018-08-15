// updated by Paul Chong for the APE Final Assignment (July 2018)
package com.oocode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BookClub {
    // List of books and its reviews
    private final Map<Book, List<Review>> bookMap = new HashMap<>();

    // Adds review to bookMap
    public void addReview(Book book, Review review) {
        bookMap.putIfAbsent(book, new ArrayList<>());
        bookMap.get(book).add(review);
    }
    // gets reviews for a specific book
    public List<Review> getReviewsFor(Book book) {
        return bookMap.getOrDefault(book, Collections.emptyList());
    }

    // returns date one year before the date specified
    public Date getOneYearEarlierDate(Date date){
        Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);  //subtract one year
        return calendar.getTime();
    }

    // determines whether a book was recently reviewed
    public boolean wasRecentlyReviewed(String bookName) {
        Book book = bookMap.keySet().stream().filter(b -> bookName.equals(b.getName())).findAny().orElse(null);
        List reviews = bookMap.get(book); // list of reviews for the book argument
        Date today = new Date();
        Date yearAgo = getOneYearEarlierDate(today);
        boolean reviewedInLastYear = false;

        for (int i = 0; i < reviews.size(); i++) {
            Review review = (Review) reviews.get(i);
            if (review.getDate().after(yearAgo)) {
                reviewedInLastYear = true;
            }
        }
        return reviewedInLastYear;
    }

    // main search method
    public List<String> searchSelector(String searchQuery) {

        if (searchQuery.equals("")) {
            throw new IllegalArgumentException();
        }
        // search by initials
        if (searchQuery.toUpperCase().equals(searchQuery)) {
            return this.searchByInitials(searchQuery);
        }
        // search by start of title
        return this.searchByTitleString(searchQuery);
    }

    public List<String> searchByInitials(String searchQuery) {
        return bookMap.keySet().stream()
                .map(Book::getName)
                .filter(b -> Arrays.stream(b.split(" "))
                        .map(e -> ("" + e.charAt(0)).toLowerCase())
                        .collect(Collectors.joining())
                        .startsWith(searchQuery.toLowerCase()))
                .filter(b -> isClassic(b)||wasRecentlyReviewed(b)) // checks whether book is a classic or was recently reviewed
                .sorted() // new code that sorts collection before keys are returned
                .collect(Collectors.toList());
    }

    public List<String> searchByTitleString(String searchQuery) {
        return bookMap.keySet().stream()
                .map(Book::getName)
                .filter(b -> b.toLowerCase().startsWith(searchQuery.toLowerCase()))
                .filter(b -> isClassic(b)||wasRecentlyReviewed(b))
                .sorted()
                .collect(Collectors.toList());
    }

    // returns a list of book which are classics.
    public List<String> getClassics(String searchQuery) {
        return searchSelector(searchQuery).stream().filter(BookClub::isClassic).sorted()
                .collect(Collectors.toList());
    }

    // calls external service with a book name, and returns whether or not the book is a classic
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

