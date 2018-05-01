package Tests;


import Controller.BookController;
import Controller.ClientController;
import Controller.InventoryController;
import Domain.Book;
import Domain.Client;
import Domain.Inventory;
import Exceptions.ValidatorException;
import Repository.InMemoRepo;
import Repository.Repo;
import Validator.BookValidator;
import Validator.ClientValidator;
import Validator.InventoryValidator;
import Validator.Validator;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestDomain {

    @Test
    public void testInventory() throws Exception {
        Validator<Book> bookV = new BookValidator();
        Repo repo = new InMemoRepo(bookV);
        BookController ctrl = new BookController(repo);
        Validator<Client> clientV = new ClientValidator();
        Repo repo2 = new InMemoRepo(clientV);
        ClientController ctrl2 = new ClientController(repo2);
        Validator<Inventory> invV = new InventoryValidator();
        Repo repo3 = new InMemoRepo(invV);
        InventoryController ctrl3 = new InventoryController(repo, repo2, repo3);
        Book b1 = new Book(1, "book1", "author1", 50, "comedy");
        try {
            ctrl.addBook(b1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        Client c1 = new Client(1, "client1", 20);

        try {
            ctrl2.addClient(c1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        assert (ctrl3.getAllInventory().size() == 0);
        Client cl = new Client(1, "Oana", 10);
        ctrl2.addClient(cl);
        assert (ctrl2.getAllClients().size() == 1);
        Book boo = new Book(1, "A1", "T1", 10, "Romance");
        ctrl.addBook(boo);
        assert (ctrl.getAllBooks().size() == 1);

        //
        Inventory inv1 = new Inventory(7, 1, 1);
        try {
            ctrl3.buy(inv1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        assert (ctrl3.getAllInventory().size() == 1);

    }

    @Test
    public void testBook() throws Exception {
        Validator<Book> bookV = new BookValidator();
        Repo repo = new InMemoRepo(bookV);
        BookController ctrl = new BookController(repo);
        assert (ctrl.getAllBooks().size() == 0);
        Book b1 = new Book(1, "book1", "author1", 50, "comedy");
        try {
            ctrl.addBook(b1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        assert (ctrl.getAllBooks().size() == 1);
        Book b2 = new Book(1, "book2", "author2", 20, "comedy");
        try {
            ctrl.addBook(b2);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        Set<Book> set = ctrl.getAllBooks();
        List<Book> list = new ArrayList<Book>(set);
        assert (list.get(0).getAuthor() == "author1");
        try {
            ctrl.updateBook(b2);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        Set<Book> set2 = ctrl.getAllBooks();
        List<Book> list2 = new ArrayList<Book>(set);
        assert (list2.get(0).getAuthor() == "author1");
        try {
            ctrl.deleteBook(1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        assert (ctrl.getAllBooks().size() == 0);

    }

    @Test
    public void testClient() throws Exception {
        Validator<Client> bookV = new ClientValidator();
        Repo repo = new InMemoRepo(bookV);
        ClientController ctrl = new ClientController(repo);
        assert (ctrl.getAllClients().size() == 0);
        Client c1 = new Client(1, "Client1", 20);

        try {
            ctrl.addClient(c1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }

        assert (ctrl.getAllClients().size() == 1);

        Client c2 = new Client(1, "Client2", 30);
        assert (ctrl.getAllClients().size() == 1);

        try {
            ctrl.updateClient(c2);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        Set<Client> set = ctrl.getAllClients();
        List<Client> list = new ArrayList<Client>(set);
        assert (list.get(0).getName() == "Client2");

        try {
            ctrl.deleteClient(1);
        } catch (ValidatorException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
        assert (ctrl.getAllClients().size() == 0);
    }
}