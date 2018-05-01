package client.service;

import common.Book;
import common.Client;
import common.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceClient implements ServiceInterface {
    @Autowired
    private ServiceInterface service;

    @Override
    public List<Client> findAllClients() {
        return service.findAllClients();
    }

    @Override
    public void updateClient(Client b) {
        service.updateClient(b);
    }

    @Override
    public void removeClient(Integer id) {
        service.removeClient(id);
    }

    @Override
    public void addClient(Client b) {
        service.addClient(b);
    }

//    @Override
//    public List<Inventory> findInventory() {
//        return service.findInventory();
//    }
//
//    @Override
//    public void buy(Inventory b) {
//        service.buy(b);
//    }


    @Override
    public List<Book> findAll() {
        return service.findAll();
    }

    @Override
    public void update(Book b) {
        service.update(b);
    }

    @Override
    public void remove(Integer id) {
        service.remove(id);
    }

    @Override
    public void add(Book b) {
        service.add(b);
    }

}
