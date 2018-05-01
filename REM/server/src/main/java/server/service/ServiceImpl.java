package server.service;

import common.Book;
import common.Client;
import common.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import server.repository.BookJdbcRepo;
import server.repository.ClientJdbcRepo;
import server.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements ServiceInterface {

    @Autowired
    Repository<Integer, Book> repoBook;
    @Autowired
    Repository<Integer, Client> repoClient;
//    @Autowired
//    Repository<Integer, Inventory> repoInventory;

    public ServiceImpl() {
        repoBook = new BookJdbcRepo();
        repoClient=new ClientJdbcRepo();
    }

    @Override
    public List<Book> findAll() {
        return repoBook.findAll();
    }

    @Override
    public void update(Book b) {
        repoBook.update(b);
    }

    @Override
    public void remove(Integer id) {
        repoBook.remove(id);
    }

    @Override
    public void add(Book b) {
        repoBook.add(b);
    }

    @Override
    public List<Client> findAllClients() {
        return repoClient.findAll();
    }

    @Override
    public void updateClient(Client b) {
        repoClient.update(b);
    }

    @Override
    public void removeClient(Integer id) {
        repoClient.remove(id);
    }

    @Override
    public void addClient(Client b) {
        repoClient.add(b);
    }
//
//    @Override
//    public List<Inventory> findInventory() {
//        return repoInventory.findAll();
//    }
//
//    @Override
//    public void buy(Inventory b) {
//        repoInventory.add(b);
//    }
}
