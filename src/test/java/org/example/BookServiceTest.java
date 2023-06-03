package org.example;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class BookServiceTest {

    @Mock
    private List<Book> bookDatabase;

    @InjectMocks
    private BookService bookService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        System.out.println("Starting tests for method with @Before");
    }

    @BeforeClass
    public static void setClass() {
        System.out.println("Setting up @BeforeClass");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Compiling class after testing with @AfterClass");
    }

    @After
    public void tearDown() {
        System.out.println("Executing clean up operations after each test with @After");
    }

    //test searchBook()
    @Test
    public void searchBookTitleMatch() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 30.50);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookDatabase.stream()).thenReturn(books.stream()); //set up mock behavior for book database
        List<Book> results = bookService.searchBook("To Kill a Mockingbird");  //searching for book by title
        assertEquals(1, results.size());    //verifying search results
        assertTrue(results.contains(book1));
    }

    @Test
    public void searchBookAuthorMatch() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 30.50);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookDatabase.stream()).thenReturn(books.stream()); //set up mock behavior for the book database
        List<Book> results = bookService.searchBook("F. Scott Fitzgerald"); //searching for book by author
        assertEquals(1, results.size());    //verifying search results
        assertTrue(results.contains(book2));
    }

    @Test
    public void searchBookGenreMatch() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Mystery", 20.99);
        Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 30.50);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookDatabase.stream()).thenReturn(books.stream()); //set up mock behavior for book database
        List<Book> results = bookService.searchBook("Mystery"); //searching for book by genre
        assertEquals(1, results.size());    //verifying search results
        assertTrue(results.contains(book1));
    }

    @Test
    public void searchBookNoMatch() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 30.50);
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        when(bookDatabase.stream()).thenReturn(books.stream()); //set up mock behavior for book database
        List<Book> searchResult = bookService.searchBook("Tuck Everlasting");   //searching for book with no match
        assertTrue(searchResult.isEmpty()); //verifying that the search result is empty
    }

    //test purchaseBook()
    @Test
    public void purchaseBookExists() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        User user = new User("JohnDoe12", " MyPassword123", "johndoe@example.com");
        when(bookDatabase.contains(book1)).thenReturn(true);    //set up mock behavior for book database
        boolean result = bookService.purchaseBook(user, book1); //make book purchase
        assertTrue(result); //verify purchase was successful
    }

    @Test
    public void purchaseBookDoesNotExist() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        User user = new User("JohnDoe12", " MyPassword123", "johndoe@example.com");
        when(bookDatabase.contains(book1)).thenReturn(false);   //set up mock behavior for the book database
        boolean result = bookService.purchaseBook(user, book1); //make book purchase
        assertFalse(result);    //verify that the purchase was unsuccessful
    }

    @Test
    public void purchaseBookInsufficientBalance() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        User user = new User("JohnDoe12", " MyPassword123", "johndoe@example.com", 15.30);

        // Set up the mock behavior for bookDatabase.contains() to return false
        when(bookDatabase.contains(book1)).thenReturn(false);

        //  Call the purchaseBook() method
        boolean result = bookService.purchaseBook(user, book1);

        // Verify that the result is false
        assertFalse(result);
    }

    //test addBook()
    @Test
    public void addBookPositive() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        when(bookDatabase.contains(book1)).thenReturn(false);   //set up mock behavior for book database
        boolean result = bookService.addBook(book1);    //add book
        assertTrue(result); //verify adding book was successful
        verify(bookDatabase).add(book1);
    }

    @Test
    public void addBookNegativeBookInDatabaseAlready() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        when(bookDatabase.contains(book1)).thenReturn(true);    //set up mock behavior in book database
        boolean result = bookService.addBook(book1);    //add book
        assertFalse(result);    //verify adding book was unsuccessful
        verify(bookDatabase, never()).add(book1);
    }

    @Test
    public void addBookEdge() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        when(bookDatabase.contains(book1)).thenReturn(true);    //set up mock behavior in book database
        boolean result = bookService.addBook(book1);    //add book
        assertFalse(result);    //verify book was unsuccessful
        verify(bookDatabase, never()).add(book1);

        // Test adding a new book to the empty database
        Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 30.50);
        when(bookDatabase.contains(book2)).thenReturn(false);   //set up mock behavior in book database
        boolean result2 = bookService.addBook(book2);   //add book
        assertTrue(result2);    //verify adding book was successful
        verify(bookDatabase).add(book2);
    }

    //test removeBook()
    @Test
    public void removeBookPositive() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        when(bookDatabase.remove(book1)).thenReturn(true);  //set up mock behavior in book database
        boolean result = bookService.removeBook(book1); //remove book
        assertTrue(result); //verify book removal was successful
        verify(bookDatabase).remove(book1);
    }

    @Test
    public void removeBookNegativeBookNotInDatabase() {
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        when(bookDatabase.remove(book1)).thenReturn(false); //set up mock behavior in book database
        boolean result = bookService.removeBook(book1); //remove book
        assertFalse(result);    //verify book removal was unsuccessful
        verify(bookDatabase).remove(book1);
    }

    @Test
    public void removeBookEdge() {
        // Test removing a book that is not in the database
        Book book1 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        when(bookDatabase.remove(book1)).thenReturn(false); //set up mock behavior in book database
        boolean result = bookService.removeBook(book1); //remove book
        assertFalse(result);    //verify book removal was unsuccessful
        verify(bookDatabase).remove(book1);

        // Test removing a book that is in the database
        Book book2 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", 30.50);
        when(bookDatabase.remove(book2)).thenReturn(true);  //set up mock behavior in book database
        boolean result2 = bookService.removeBook(book2);    //remove book
        assertTrue(result2);    //verify book removal was successful
        verify(bookDatabase).remove(book2);
    }

    //test addBookReview() - Optional so testing with @Ignore
    @Ignore
    @Test
    public void addBookReviewPositive() {
        List<Book> books = new ArrayList<>();
        Book book = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", 20.99);
        books.add(book);
        User user = new User("SparkleDreamer87", " Myst3ry@2023", "sparkle.dreamer87@gmail.com", books);
        user.purchaseBook(book);
        String review = "Great book!";
        boolean result = bookService.addBookReview(user, book, review); //add book review
        assertTrue(result); //verify adding review was successful
        assertTrue(book.getReviews().contains(review));
    }

    @Test
    public void addBookReviewNegativeUserHasNotPurchasedBook() {
        List<Book> books = new ArrayList<>();
        Book book = new Book("Echoes of Eternity", "Elysia Winterborne", "Fantasy", 20.95);
        User user = new User("SparkleDreamer87", " Myst3ry@2023", "sparkle.dreamer87@gmail.com", books);
        String review = "Great book!";
        boolean result = bookService.addBookReview(user, book, review); //add book review
        assertFalse(result);    //verify adding review was unsuccessful
        assertFalse(book.getReviews().contains(review));
    }

    @Test
    public void addBookReviewEdgeCase() {
        Book book = new Book("Echoes of Eternity", "Elysia Winterborne", "Fantasy", 20.95);
        User user = new User("JohnDoe12", " MyPassword123", "johndoe@example.com", 50.34);
        String review = "Great book!";
        boolean result = bookService.addBookReview(user, book, review); //add book review
        assertFalse(result);    //verifying adding review was unsuccessful
        assertFalse(book.getReviews().contains(review));

        // Purchase the book and try adding the review again
        user.purchaseBook(book);    //make sure user purchases book
        boolean result2 = bookService.addBookReview(user, book, review);    //add review again - after purchase
        assertTrue(result2);    //verify adding review was successful
        assertTrue(book.getReviews().contains(review));
    }
}