package common;

import java.util.List;

public interface ServiceInterface {
    List<Book> findAll();

    void update(Book b);

    void remove(Integer id);

    void add(Book b);

    List<Client> findAllClients();

    void updateClient(Client b);

    void removeClient(Integer id);

    void addClient(Client b);
//
//    List<Inventory> findInventory();
//
//    void buy(Inventory b)

}
