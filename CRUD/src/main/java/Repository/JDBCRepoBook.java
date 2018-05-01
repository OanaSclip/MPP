package Repository;

import Domain.Book;
import Exceptions.ValidatorException;
import Validator.Validator;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JDBCRepoBook implements Repo<Integer, Book> {
    private static final String URL = "jdbc:postgresql://localhost:5432/BookStore";
    private static final String user = "postgres";
    private static final String password = "root";
    private Validator<Book> validator;

    public JDBCRepoBook(Validator<Book> validator) {
        this.validator = validator;
    }


    @Override
    public Optional<Book> findOne(Integer id) throws ValidatorException {
        Iterable<Book> books = findAll();

        Stream<Book> targetStream = StreamSupport.stream(books.spliterator(), false);
        return targetStream.filter(c -> c.getIdEntity().equals(id)).findFirst();

    }

    @Override
    public Iterable<Book> findAll() throws ValidatorException {
        Map<Integer, Book> books = new HashMap<>();
        String sql = "select * from book";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                int money = rs.getInt("price");
                Book b = new Book(id, title, author, money, genre);
                books.put(id, b);
            }
            return books.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());

        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
    }

    @Override
    public Optional save(Book book) throws ValidatorException, IOException {
        validator.validate(book);
        String sql = "insert into book (id,author, title,genre,price) values (?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, book.getIdEntity());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getTitle());
            statement.setString(4, book.getGenre());
            statement.setInt(5, book.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
        return null;
    }

    @Override
    public Optional update(Book book) throws ValidatorException, IOException {
        validator.validate(book);
        String sql = "UPDATE book set title=?, author=?,genre=?,price=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());
            statement.setInt(4, book.getPrice());
            statement.setInt(5, book.getIdEntity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
        return null;
    }


    @Override
    public Optional<Book> delete(Integer id) throws IOException {
        String sql = "DELETE from book where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
            throw new IOException("cannont connect to the database!\n");

        }

        return null;
    }

}

