package Domain;


public class Book extends Base<Integer> {
    private String title;
    private String author;
    private Integer price;
    private String genre;

    public Book() {
    }

    public Book(Integer idBook, String title, String author, Integer price, String g) {

        super(idBook);
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
        return "Book: " +
                super.toString() +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", genre='" + genre + '\'' +
                '\n';
    }

    @Override
    public String toStr() {
        return
                super.toStr() +","+
                        title +","+
                        author +","+
                        price +","+
                        genre +
                        '\n';
    }
}
