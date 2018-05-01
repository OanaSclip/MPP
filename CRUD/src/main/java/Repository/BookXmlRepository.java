package Repository;

import Domain.Book;
import Exceptions.ValidatorException;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookXmlRepository extends InMemoRepo<Integer, Book> {
    private String filename;

    public BookXmlRepository(Validator<Book> validator, String filename) throws Exception {
        super(validator);
        this.filename = filename;
        loadData();
    }

    private static String getTextByTagName(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }

    private void loadData() throws Exception {
        List<Book> books = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Books.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                Element bookElement = (Element) child;
                String id = bookElement.getAttribute("id");

                String title = getTextByTagName(bookElement, "title");
                String author = getTextByTagName(bookElement, "author");
                String price = getTextByTagName(bookElement, "price");
                String genre = getTextByTagName(bookElement, "genre");
                Book book = new Book(Integer.parseInt(id), title, author, Integer.parseInt(price), genre);
                books.add(book);
            }
        }

        for (Book book : books) {
            try {
                super.save(book);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public Optional<Book> findOne(Integer integer) {
        Optional<Book> optional = super.findOne(integer);
        if (optional.isPresent()) {
            return optional;
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Book> findAll() {
        return super.findAll();
    }

    private static void appendChildToElement(Document doc, Element parent, String tag, String value) {
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.appendChild(element);
    }

    public Optional<Book> save(Book book) throws Exception, ValidatorException {
        Optional<Book> optional = super.save(book);
        if (optional.isPresent()) {
            return optional;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Books.xml");
        Element root = xmlDoc.getDocumentElement();

        Element ourBook = xmlDoc.createElement("book");
        ourBook.setAttribute("id", String.valueOf(book.getIdEntity()));
        appendChildToElement(xmlDoc, ourBook, "title", book.getTitle());
        appendChildToElement(xmlDoc, ourBook, "author", book.getAuthor());
        appendChildToElement(xmlDoc, ourBook, "price", String.valueOf(book.getPrice()));
        appendChildToElement(xmlDoc, ourBook, "genre", book.getGenre());

        root.appendChild(ourBook);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult("./Data/Books.xml"));

        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Integer id) throws Exception {
        Optional<Book> optional = super.delete(id);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Books.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                Element bookElement = (Element) child;
                String idBook = bookElement.getAttribute("id");
                if (Integer.parseInt(idBook) == id) {
                    root.removeChild(child);
                }
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult("./Data/Books.xml"));


        return Optional.empty();
    }

    private static void replaceNode(Document doc, Node old, Element parent, String tag, String value) {
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.replaceChild(element, old);
    }

    private static Node old(Element element, String tag) {
        return element.getElementsByTagName(tag).item(0);
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException, Exception {
        Optional<Book> optional = super.update(entity);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
        Document xmlDoc = dbBuilder.parse("./Data/Books.xml");
        Element root = xmlDoc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child instanceof Element) {
                Element bookElement = (Element) child;
                String idBook = bookElement.getAttribute("id");
                if (Integer.parseInt(idBook) == entity.getIdEntity()) {
                    Node auth = old(bookElement, "author");
                    replaceNode(xmlDoc, auth, bookElement, "author", entity.getAuthor());
                    Node titl = old(bookElement, "title");
                    replaceNode(xmlDoc, titl, bookElement, "title", entity.getTitle());
                    Node pric = old(bookElement, "price");
                    replaceNode(xmlDoc, pric, bookElement, "price", String.valueOf(entity.getPrice()));
                    Node genr = old(bookElement, "genre");
                    replaceNode(xmlDoc, genr, bookElement, "genre", entity.getGenre());
                }
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(root), new StreamResult("./Data/Books.xml"));

        return Optional.empty();
    }
}
