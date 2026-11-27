package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final Map<Long, Book> books = new LinkedHashMap<>();
    private final AtomicLong ids = new AtomicLong(1);

    public BookService() {
        books.put(1L, new Book(1L, "The Hobbit", "J.R.R. Tolkien"));
    }

    public synchronized List<Book> findAll() {
        return List.copyOf(books.values());
    }

    public synchronized Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public synchronized Book add(Book book) {
        long id = ids.incrementAndGet();
        Book saved = new Book(id, book.title(), book.author());
        books.put(id, saved);
        return saved;
    }
}
