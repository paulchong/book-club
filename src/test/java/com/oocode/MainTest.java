package com.oocode;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void t1() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");

        assertEquals("if search string is NOT ALL UPPER case " +
                        "then it means search matching start of title",
                asList("Hello Wally", "Hello World"),
                bookClub.search("Hel"));
        assertEquals(asList("Hello Wally", "Hello World"),
                bookClub.search("hel"));
    }


    // PC: added new test to illustrate failure that existing test was missing
    @Test
    public void canSearchByInitials2() {
        BookClub bookClub = new BookClub();

        bookClub.addReview("Adios Wally", "great");
        bookClub.addReview("Aurevoir Wally", "boring");

        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Adios Wally", "Aurevoir Wally"),
                bookClub.search("AW"));
    }

    @Test
    public void canSearchByInitials() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");

        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Hello Wally", "Hello World"),
                bookClub.search("HW"));
    }

    // PC: test for when book name starts with lower case
    @Test
    public void t3() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("hello world", "great");
        bookClub.addReview("hello wally", "boring");

        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("Hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("hel"));
        assertEquals(asList("hello wally", "hello world"),
                bookClub.search("HW"));
    }

    // PC: test for when multiple reviews added to one book
    @Test
    public void t4() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello World", "the best book ever");

        assertEquals(asList("great", "the best book ever"),
                bookClub.reviewsFor("Hello World"));
    }

    //  PC: tests for whether a book is classic. in this case, only Hello World is classic.
    //  PC: there should be a better way to do this. what are we actually testing? that the call to the external
    // service is working, or that the service is actually returning what we think it will.
    @Test
    public void t5() {
        BookClub bookClub = new BookClub();
        bookClub.addReview("Hello World", "great");
        bookClub.addReview("Hello Wally", "boring");
        // PC: Should pass but it depends what the external service
        // determines is a "classic", which might change
        //  PC: singletonList is a list with a single object and is immutable. Q: why is it used here?
        assertEquals(singletonList("Hello World"),
                bookClub.classics("HW"));
        assertEquals(singletonList("Hello World"),
                bookClub.classics("He"));
    }

    // PC: custom test for book object
    @Test
    public void t6() {
        BookClub bookClub = new BookClub();
        bookClub.createBook("sunrise");
        bookClub.createBook("sunset");
        System.out.print(bookClub.printBookList());
        assertEquals(1, bookClub.getNumBooks());
    }


    @Test
    public void canSearch2ByInitials() {
        BookClub bookClub = new BookClub();
        Book book1 = new Book("Hello World");
        Book book2 = new Book("Hello Wally");
        bookClub.addBookReview(book1,"Great");
        bookClub.addBookReview(book2,"boring");
        assertEquals("if search string is ALL UPPER case " +
                        "then it means search by initials",
                asList("Hello Wally", "Hello World"),
                bookClub.search2("HW"));
    }
}
