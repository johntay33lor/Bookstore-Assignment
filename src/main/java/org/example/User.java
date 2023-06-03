package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private String email;
    private double balance; //added user balance to test if they can purchase book

    private List<Book> purchasedBooks = new ArrayList<>();

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public User(String username, String password, String email, double balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
    }

    public User(String username, String password, String email, List<Book> purchasedBooks) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.purchasedBooks = purchasedBooks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Book> getPurchasedBooks() {
        return purchasedBooks;
    }

    public void setPurchasedBooks(List<Book> purchasedBooks) {
        this.purchasedBooks = purchasedBooks;
    }
    public boolean hasPurchasedBook(Book book) {
        return purchasedBooks.contains(book); // Check if the book is present in the purchased books set
    }
    public void purchaseBook(Book book) {
        // Perform the book purchase logic here
        if (!purchasedBooks.contains(book)) {
            purchasedBooks.add(book); // Add the book to the purchased books set
            // Deduct the book price from the user's balance or perform any other necessary operations
            balance -= book.getPrice();
        }
    }
}