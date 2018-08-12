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
                bookClub.search("Hel"));
        assertEquals(asList("Hello Wally", "Hello World"),
                bookClub.search("hel"));
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
                bookClub.search("AW"));
    }

    @Test
    public void searchReturnsBooksWhenTitleIsLowercase() {
        Book book5 = new Book("hello world");
        Book book6 = new Book("hello wally");
        bookClub.addReview(book5, review1);
        bookClub.addReview(book6, review2);
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("Hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("HW"));
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


//    @Test
//    public void canSearch2ByStringRecentlyReviewedFilter() {
//        BookClub bookClub = new BookClub();
//        Book book1 = new Book("Hello World");
//        Book book2 = new Book("Hello Wally");
//        Review review1 = new Review("this is a review for Book1", "01/01/2008");
//        Review review2 = new Review("this is another review for Book1", "01/01/2001");
//        Review review3 = new Review("this is a review for Book2", "01/01/2008");
//        bookClub.addBookReview(book1, review1);
//        bookClub.addBookReview(book1, review2);
//        bookClub.addBookReview(book2, review3);
//        assertEquals("if ",
//                asList("Hello World"),
//                bookClub.search2("Hello"));
//    }
//
//    @Test
//    public void canSearch2ByInitialsRecentlyReviewedFilter() {
//        BookClub bookClub = new BookClub();
//        Book book1 = new Book("Hello World");
//        Book book2 = new Book("Hello Wally");
//        Review review1 = new Review("this is a review for Book1", "01/01/2018");
//        Review review2 = new Review("this is another review for Book1", "01/01/2001");
//        Review review3 = new Review("this is a review for Book2", "01/01/2008");
//        bookClub.addBookReview(book1, review1);
//        bookClub.addBookReview(book1, review2);
//        bookClub.addBookReview(book2, review3);
//        assertEquals("if ",
//                asList("Hello World"),
//                bookClub.search2("HW"));
//    }

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

//    @Test
//    public void canAddBookReview() {
//        BookClub bookClub = new BookClub();
//        Book book1 = new Book("blah");
//        Review review1 = new Review("this is a review post", "01/01/2000");
//        Review review2 = new Review("this is another review post", "01/01/2001");
//        bookClub.addBookReview(book1, review1);
//        bookClub.addBookReview(book1, review2);
//        assertEquals("if",
//                asList(review1, review2),
//                bookClub.bookReviewsFor(book1));
//    }
//
//    @Test
//    public void recentlyReviewed() {
//        BookClub bookClub = new BookClub();
//        Book book1 = new Book("Hello World");
//        Review review1 = new Review("this is a review post", "01/01/2018");
//        Review review2 = new Review("this is another review post", "01/01/2001");
//        bookClub.addBookReview(book1, review1);
//        bookClub.addBookReview(book1, review2);
//        assertEquals("if",
//                true,
//                bookClub.wasRecentlyReviewed("Hello World"));
//    }
//
//    @Test
//    public void canFilterForClassics() {
//        BookClub bookClub = new BookClub();
//        Book book1 = new Book("Hello World");
//        Book book2 = new Book("Hello Wally");
//        Review review1 = new Review("this is a review for Book1", "01/01/2018");
//        Review review2 = new Review("this is another review for Book1", "01/01/2001");
//        Review review3 = new Review("this is a review for Book2", "01/01/2008");
//        bookClub.addBookReview(book1, review1);
//        bookClub.addBookReview(book1, review2);
//        bookClub.addBookReview(book2, review3);
//        assertEquals(singletonList("Hello World"),
//                bookClub.search2("HW"));
//        assertEquals(singletonList("Hello World"),
//                bookClub.search2("He"));
//    }

}
