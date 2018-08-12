package com.oocode;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void canSearchByStartOfTitle() {
        BookClub bookClub = new BookClub();
        Book book1 = new Book("Hello World");
        Book book2 = new Book("Hello Wally");
        Review review1 = new Review("great", "01/01/2018");
        Review review2 = new Review("boring", "01/01/2018");
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
        BookClub bookClub = new BookClub();
        Book book1 = new Book("Adios Wally");
        Book book2 = new Book("Aurevoir Wally");
        Review review1 = new Review("great", "01/01/2018");
        Review review2 = new Review("boring", "01/01/2018");
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review2);
        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Adios Wally", "Aurevoir Wally"),
                bookClub.search("AW"));
    }


    // PC: test for when book name starts with lower case
    @Test
    public void searchReturnsBooksWhenTitleIsLowercase() {
        BookClub bookClub = new BookClub();
        Book book1 = new Book("hello world");
        Book book2 = new Book("hello wally");
        Review review1 = new Review("great", "01/01/2018");
        Review review2 = new Review("boring", "01/01/2018");
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review2);
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("Hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("HW"));
    }

    @Test
    public void canAddMultipleReviewsToSingleBook() {
        BookClub bookClub = new BookClub();
        Book book1 = new Book("Hello World");
        Review review1 = new Review("great", "01/01/2018");
        Review review2 = new Review("the best book ever", "01/01/2018");
        bookClub.addReview(book1, review1);
        bookClub.addReview(book1, review2);
        assertEquals(asList(review1, review2),
                bookClub.reviewsFor(book1));
    }

    //  PC: tests for whether a book is classic. in this case, only Hello World is classic.
    //  PC: there should be a better way to do this. what are we actually testing? that the call to the external
    // service is working, or that the service is actually returning what we think it will.
    @Test
    public void getClassicsMethodReturnsClassics() {
        BookClub bookClub = new BookClub();
        Book book1 = new Book("Hello World");
        Book book2 = new Book("Hello Wally");
        Review review1 = new Review("great", "01/01/2018");
        Review review2 = new Review("boring", "01/01/2018");
        bookClub.addReview(book1, review1);
        bookClub.addReview(book2, review2);
        assertEquals(singletonList("Hello World"),
                bookClub.getClassics("HW"));
        assertEquals(singletonList("Hello World"),
                bookClub.getClassics("He"));
    }

    // PC: custom test for book object
//    @Test
//    public void t6() {
//        BookClub bookClub = new BookClub();
//        bookClub.createBook("sunrise");
//        bookClub.createBook("sunset");
//        System.out.print(bookClub.printBookList());
//        assertEquals(1, bookClub.getNumBooks());
//    }
    

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
        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                "post test here",
                review1.getPost());
    }

    //write a test for confirming date is working correctly. currently failing
    @Test
    public void canGetReviewPostDate() {
        Review review1 = new Review("post test here","01/01/2000");
        System.out.print(review1.toString());
        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                "01/01/2000",
                review1.getDate());
    }

    @Test
    public void reviewDate() {
        BookClub bookClub = new BookClub();
        assertEquals("if",
                new Date(),
                bookClub.getOneYearEarlierDate(new Date()));
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
