package Consolee;

import Controller.BookController;
import Controller.ClientController;
import Controller.InventoryController;
import Domain.Book;
import Domain.Client;
import Domain.Inventory;
import Exceptions.ValidatorException;
import java.util.Scanner;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class RunMenu extends Command {
    private BookController ctrlB;
    private ClientController ctrlCl;
    private InventoryController ctrlI;

    public RunMenu(Integer key, String desc, BookController _ctrl, ClientController _ctrl2, InventoryController _ctrl3) {
        super(key, desc);
        this.ctrlB = _ctrl;
        this.ctrlCl = _ctrl2;
        this.ctrlI = _ctrl3;
    }

    /**
     * Waits for user input for the parameters needed. Creates a new Book
     * and calls the addBook method from the BookController
     */
    public void addBook() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID Book: ");
        String id = s.nextLine();
        System.out.println("Title: ");
        String t = s.nextLine();
        System.out.println("Author: ");
        String a = s.nextLine();
        System.out.println("Genre: ");
        String g = s.nextLine();
        System.out.println("Price: ");
        Integer p = s.nextInt();
        Book b = new Book(Integer.parseInt(id), t, a, p, g);
        try {
            this.ctrlB.addBook(b);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Waits for user input for the parameters needed
     * and calls the deleteBook method from the BookController
     */
    public void removeBook() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID to remove: ");
        Integer id = s.nextInt();
        try {
            this.ctrlB.deleteBook(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Waits for user input for the parameters needed. Creates a new Book
     * and calls the updateBook method from the BookController
     */
    public void updateBook() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID to update: ");
        String id = s.nextLine();
        System.out.println("New title: ");
        String t = s.nextLine();
        System.out.println("New author: ");
        String a = s.nextLine();
        System.out.println("New price: ");
        String p = s.nextLine();
        System.out.println("New genre: ");
        String g = s.nextLine();
        Book b = new Book(Integer.parseInt(id), t, a, Integer.parseInt(p), g);
        try {
            this.ctrlB.updateBook(b);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Calls the getAllBooks from the BookController and prints them Books
     */
    public void getBooks() throws ValidatorException {
        Set<Book> books = this.ctrlB.getAllBooks();
        books.stream().forEach(System.out::println);
    }

    /**
     * Waits for user input for the parameters needed. Creates a new Client
     * and calls the addClient method from the ClientController
     */
    public void addClient() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID Client: ");
        String id = s.nextLine();
        System.out.println("Client name: ");
        String n = s.nextLine();
        System.out.println("Money Spent: ");
        String p = s.nextLine();
        Client c = new Client(Integer.parseInt(id), n, Integer.parseInt(p));
        try {
            this.ctrlCl.addClient(c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Waits for the user input. Calls the deleteClient method from ClientController
     */
    public void removeClient() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID to remove: ");
        Integer id = s.nextInt();
        try {
            this.ctrlCl.deleteClient(id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Waits for user input for the parameters needed. Creates a new Client
     * and calls the updateClient method from the ClientController
     */
    public void updateClient() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID to update: ");
        String id = s.nextLine();
        System.out.println("New name: ");
        String n = s.nextLine();
        System.out.println("New money spent: ");
        String m = s.nextLine();
        Client c = new Client(Integer.parseInt(id), n, Integer.parseInt(m));
        try {
            this.ctrlCl.updateClient(c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Calls the getAllClients method from the ClientController and prints them Clients
     */
    public void getAllClients() throws ValidatorException {
        Set<Client> clients = this.ctrlCl.getAllClients();
        clients.stream().forEach(System.out::println);
    }

    public void getClientsSortFiler() throws ValidatorException {
        List<Client> clients = this.ctrlCl.clientsFilterSorted();
        clients.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }

    /**
     * Waits for the user input for the necessary parameters.
     * Creates an Inventory and calls the buy method from InventoryController
     */
    public void buyBook() throws ValidatorException {
        System.out.println(ctrlI.getIDs());
        System.out.println(ctrlI.getIDCs());
        Scanner s = new Scanner(System.in);
        System.out.println("ID Invetory: ");
        String id = s.nextLine();
        System.out.println("ID Book: ");
        String idB = s.nextLine();
        System.out.println("ID Client: ");
        String idC = s.nextLine();
        Inventory inv = new Inventory(Integer.parseInt(id), Integer.parseInt(idC), Integer.parseInt(idB));
        try {
            this.ctrlI.buy(inv);
        } catch (ValidatorException e) {
            e.printStackTrace();
        } catch (IOException e) {

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Calls the getAllInventory method from the InventoryController and prints them Inventories
     */
    public void listInventory() throws ValidatorException {
        Set<Inventory> inv = this.ctrlI.getAllInventory();
        inv.stream().forEach(System.out::println);
    }

    /**
     * Calls the sortByName method from the ClientController and prints them Clients
     */
    public void listSortedClients() throws ValidatorException {
        Set<Client> clients = this.ctrlCl.sortByName();
        clients.stream().sorted().forEach(System.out::println);
    }

    /**
     * Waits the the user input parameters. Calls the filterBooksByCategory method
     * from BookController and prints them books
     */
    public void listFilteredBooks() throws ValidatorException {
        Scanner s = new Scanner(System.in);
        System.out.println("Genre: ");
        String genre = s.nextLine();
        Set<Book> books = this.ctrlB.filterBooksByCategory(genre);
        books.forEach(System.out::println);
    }

    /**
     * Calls different methods depending on the key of the Command
     */
    @Override
    public void exec() {
        try {
            switch (key) {
                case 1: {
                    addBook();
                    break;
                }
                case 2: {
                    removeBook();
                    break;
                }
                case 3: {
                    updateBook();
                    break;
                }
                case 4: {
                    getBooks();
                    break;
                }
                case 5: {
                    addClient();
                    break;
                }
                case 6: {
                    removeClient();
                    break;
                }
                case 7: {
                    updateClient();
                    break;
                }
                case 8: {
                    getAllClients();
                    break;
                }
                case 9: {
                    buyBook();
                    break;
                }
                case 10: {
                    listInventory();
                    break;
                }
                case 11: {
                    listSortedClients();
                    break;
                }
                case 12: {
                    listFilteredBooks();
                    break;
                }
                case 13: {
                    getClientsSortFiler();
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error");
        }
    }
}
