package com.oocode;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class MainTest {
    private BookClub bookClub = new BookClub();
    private Book book1 = new Book("Hello World");
    private Book book2 = new Book("Hello Wally");
    private Review review1 = new Review("great", "01/01/2018");
    private Review review2 = new Review("boring", "01/01/2018");
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void canSearchByStartOfTitle() {
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review2);
        assertEquals("if search string is NOT ALL UPPER case " +
                        "then it means search matching start of title",
                asList("Hello Wally", "Hello World"),
                bookClub.searchSelector("Hel"));
        assertEquals(asList("Hello Wally", "Hello World"),
                bookClub.searchSelector("hel"));
    }

    @Test
    public void canSearchByInitials() {
        Book book3 = new Book("Adios Wally");
        Book book4 = new Book("Aurevoir Wally");
        bookClub.addReview(book3, review1);
        bookClub.addReview(book4, review2);
        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Adios Wally", "Aurevoir Wally"),
                bookClub.searchSelector("AW"));
    }

    @Test
    public void searchReturnsBooksWhenTitleIsLowercase() {
        Book book5 = new Book("hello world");
        Book book6 = new Book("hello wally");
        bookClub.addReview(book5, review1);
        bookClub.addReview(book6, review2);
        assertEquals(asList("hello wally", "hello world"),
                bookClub.searchSelector("Hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.searchSelector("hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.searchSelector("HW"));
    }

    @Test
    public void canAddMultipleReviewsToSingleBook() {
        bookClub.addReview(book1, review1);
        bookClub.addReview(book1, review2);
        assertEquals(asList(review1, review2),
                bookClub.getReviewsFor(book1));
    }

    @Test
    public void getClassicsMethodReturnsClassics() {
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review2);
        assertEquals(singletonList("Hello World"),
                bookClub.getClassics("HW"));
        assertEquals(singletonList("Hello World"),
                bookClub.getClassics("He"));
    }


    @Test
    public void dateFilterIsAppliedWhenSearchingByStartOfTitle() {
        Review review3 = new Review("great book, not recently reviewed", "01/01/2008");
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review3);
        assertEquals(asList("Hello World"), bookClub.searchSelector("Hello"));
    }

    @Test
    public void dateFilterIsAppliedWhenSearchingByInitials() {
        Review review3 = new Review("great book, not recently reviewed", "01/01/2008");
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review3);
        assertEquals( asList("Hello World"), bookClub.searchSelector("HW"));
    }

    @Test
    public void canGetReviewPost() {
        Review review1 = new Review("post test here");
        assertEquals("post test here",
                review1.getPost());
    }

    @Test
    public void canGetReviewPostDate() {
        Review review1 = new Review("post test here","01/01/2000");
        assertEquals("01/01/2000", formatter.format(review1.getDate()));
    }

    @Test
    public void returnsOneYearEarlierDate() throws ParseException {
        Date date = formatter.parse("01/01/2018");
        assertEquals(formatter.parse("01/01/2017"),
                bookClub.getOneYearEarlierDate(date));
    }

    @Test
    public void canAddReview() {
        bookClub.addReview(book1, review1);
        bookClub.addReview(book1, review2);
        assertEquals(asList(review1, review2),bookClub.getReviewsFor(book1));
    }

    @Test
    public void recentlyReviewedBookReturnsTrue() {
        bookClub.addReview(book1, review1);
        bookClub.addReview(book1, review2);
        assertEquals(true, bookClub.wasRecentlyReviewed("Hello World"));
    }

    @Test
    public void canCreateBook() {
        Book aBook = new Book("The Sun Also Rises");
        assertEquals("Book Name: The Sun Also Rises"+"\n", aBook.toString());
    }

    @Test
    public void canGetBookName() {
        Book aBook = new Book("The Sun Also Rises");
        assertEquals("The Sun Also Rises", aBook.getName());
    }

    @Test
    public void canCreateReview() {
        Review aReview = new Review("Best book I've read this year","01/01/2018");
        assertEquals("Date: 01/01/2018"+"\nPost: Best book I've read this year", aReview.toString());
    }

    @Test
    public void canCreateReviewPostOnly() {
        Review aReview = new Review("Best book I've read this year");
        Date dateToday = new Date();
        assertEquals("Date: " + formatter.format(dateToday) + "\nPost: Best book I've read this year", aReview.toString());
    }

    @Test
    public void canGetReviewDate() throws ParseException {
        Review aReview = new Review("Best book I've read this year","01/01/2018");
        assertEquals(formatter.parse("01/01/2018"), aReview.getDate());
    }

    @Test (expected = IllegalArgumentException.class)
    public void passingEmptyQueryThrowsException() {
        bookClub.searchSelector("");
    }

}
