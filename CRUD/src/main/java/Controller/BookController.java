package Controller;

import Domain.Book;
import Exceptions.ValidatorException;
import Repository.Repo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BookController {
    private Repo<Integer, Book> repo;

    public BookController(Repo<Integer, Book> repo) {
        this.repo = repo;
    }

    /**
     * Receives a Book and calls the save method from the repository with that param
     *
     * @param c
     * @throws ValidatorException
     */

    public void addBook(Book c) throws Exception {
        repo.save(c);
    }

    /**
     * Receives in Integer and calls the method delete from the repository with that param
     *
     * @param id
     * @throws ValidatorException
     */

    public void deleteBook(Integer id) throws Exception {

        repo.delete(id);
    }

    /**
     * Receives a Book and calls the update method from the repository with that param
     *
     * @param c
     * @throws ValidatorException
     */

    public void updateBook(Book c) throws ValidatorException,Exception {

        repo.update(c);
    }

    /**
     * Returns a Set containing all the Books currently in the java.Repository
     *
     * @return
     */
    public Set<Book> getAllBooks() throws ValidatorException {
        Iterable<Book> books = repo.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());

    }

    /**
     * Receives a String s and filters the Books from the java.Repository
     * Returns a Set of Books having the category equal to s
     * @param s - category
     * @return filter
     */
    public Set<Book> filterBooksByCategory(String s) throws ValidatorException {
        Iterable<Book> books = repo.findAll();
        return StreamSupport.stream(books.spliterator(), false).filter(book -> book.getGenre().equals(s)).collect(Collectors.toSet());
    }

}
