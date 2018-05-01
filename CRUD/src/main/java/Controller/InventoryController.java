package Controller;

import Domain.Book;
import Domain.Client;
import Domain.Inventory;
import Exceptions.ValidatorException;
import Repository.Repo;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class InventoryController {
    private Repo<Integer, Book> books;
    private Repo<Integer, Client> clients;
    private Repo<Integer, Inventory> inventory;

    public InventoryController(Repo<Integer, Book> books, Repo<Integer, Client> clients, Repo<Integer, Inventory> inventory) {
        this.books = books;
        this.clients = clients;
        this.inventory = inventory;
    }

    /**
     * Receives an Inventory. Checks if the Client and Book are valid
     * If they are, we call the method save method from the java.Repository
     * and raise the spent money for the Client
     *
     * @param inv
     * @throws ValidatorException
     */

    public List<Integer> getIDs() throws ValidatorException {
        Iterable<Book> b= this.books.findAll();
        Stream<Book> targetStream = StreamSupport.stream(b.spliterator(), false);
        List<Integer>list=targetStream.map(c -> c.getIdEntity()).collect(Collectors.toList());
        return list;
    }

    public List<Integer> getIDCs() throws ValidatorException {
        Iterable<Client> c= this.clients.findAll();
        Stream<Client> targetStream = StreamSupport.stream(c.spliterator(), false);
        List<Integer> list=targetStream.map(cc -> cc.getIdEntity()).collect(Collectors.toList());
        return list;
    }

    public void buy(Inventory inv) throws Exception {

        Optional<Client> c = this.clients.findOne(inv.getIdClient());
        Optional<Book> b = this.books.findOne(inv.getIdBook());
        if (!c.equals(null) && !b.equals(null)) {
            Client cl = c.get();
            Book bo = b.get();
            cl.setSpentMoney(cl.getSpentMoney() + bo.getPrice());
            inventory.save(inv);
        }
    }

    /**
     * Returns a Set containing all the Inventories currently in the java.Repository
     *
     * @return
     */
    public Set<Inventory> getAllInventory() throws ValidatorException {
        Iterable<Inventory> clients = inventory.findAll();

        return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
    }

}
