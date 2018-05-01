package common;

import java.io.Serializable;

public class Book extends Base implements Serializable {
    private String title;
    private String author;
    private Integer price;
    private String genre;
    private Integer id;

    public Integer getIdEntity() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book() {
    }

    public Book(Integer idBook, String title, String author, Integer price, String g) {

        this.id=idBook;
        this.title = title;
        this.author = author;
        this.price = price;
        genre = g;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                ", id=" + id +
                '}';
    }
}
