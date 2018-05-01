package server.repository;

import common.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class BookJdbcRepo implements Repository<Integer, Book> {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcOperations jdbcOperations;

//    @Override
//    public List<Book> findAll() {
//        String sql = "select * from Book";
//        return
//                jdbcOperations.query(sql, (rs, i) -> {
//                    int id = rs.getInt("id");
//                    String author = rs.getString("author");
//                    String title = rs.getString("title");
//                    String genre = rs.getString("genre");
//                    int price = rs.getInt("price");
//                    return new Book(id, title, author, price, genre);
//                });
//    }

    public BookJdbcRepo() {
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM Book";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Book.class));
    }


    @Override
    public void update(Book book) {
        String sql = "update book set title=?, author=?,price=?,genre=? where id=?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPrice(), book.getGenre(), book.getIdEntity());
    }

    @Override
    public void remove(Integer id) {
        String sql = "delete from book where id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public void add(Book book) {
        String sql = "insert into Book (id,title,author,price,genre) values (?,?,?,?,?)";
        System.out.println("din save client service impl");
        jdbcTemplate.update(sql, book.getIdEntity(), book.getTitle(), book.getAuthor(), book.getPrice(), book.getGenre());

    }

}
