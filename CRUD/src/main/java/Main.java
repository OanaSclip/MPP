import Consolee.Menu;
import Consolee.RunMenu;
import Controller.BookController;
import Controller.ClientController;
import Controller.InventoryController;
import Domain.Book;
import Domain.Client;
import Domain.Inventory;
import Repository.*;
import Validator.BookValidator;
import Validator.ClientValidator;
import Validator.InventoryValidator;
import Validator.Validator;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void runMenu(BookController ctrl, ClientController ctrl1, InventoryController ctrl2) throws IOException {
        Menu menu = new Menu();


        menu.addCmd(new RunMenu(1, "Add Book", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(2, "Remove Book", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(3, "Update Book", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(4, "List All Books", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(5, "Add Client", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(6, "Remove Client", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(7, "Update Client", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(8, "List All Clients", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(9, "Buy a book", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(10, "Inventory", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(11, "Sort Clients", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(12, "Filter Books by genre", ctrl, ctrl1, ctrl2));
        menu.addCmd(new RunMenu(13, "Filter Clients by spentmoney ordered by name", ctrl, ctrl1, ctrl2));


        menu.showMenu();

    }

    public static void main(String[] args) throws IOException {


        Book b1 = new Book(1, "T1", "A1", 100, "R");
        Book b2 = new Book(2, "T2", "A2", 123, "A");
        Book b3 = new Book(3, "T3", "A3", 145, "H");
        Book b4 = new Book(4, "T4", "A4", 200, "K");

        Client c1 = new Client(1, "Ingrid", 250);
        Client c2 = new Client(2, "Oana", 200);
        Client c3 = new Client(3, "Sergiu", 340);
        Client c4 = new Client(4, "Marcel", 750);
        Client c5 = new Client(5, "Gigel", 1250);

        Validator<Book> bookV = new BookValidator<Book>();
        Validator<Client> clientV = new ClientValidator<Client>();
        Validator<Inventory> inventoryV = new InventoryValidator<Inventory>();
        boolean go = true;
        while (go) {
            System.out.println("Repository: \n");
            System.out.println("1. In Memory \n");
            System.out.println("2. XML \n ");
            System.out.println("3. Data base \n ");
            System.out.println("4. File Repo \n");
            System.out.println("5. Yaml Repo \n");
            System.out.println("Option: ");
            Scanner s = new Scanner(System.in);

            Integer k = Integer.parseInt(s.nextLine());


            switch (k) {
                case 0: {
                    go = false;
                    break;
                }
                case 1: {
                    Repo memoBook = new InMemoRepo(bookV);
                    Repo memoClient = new InMemoRepo(clientV);
                    Repo memoInventory = new InMemoRepo(inventoryV);
                    BookController memoBookCtrl = new BookController(memoBook);
                    ClientController memoClientCtrl = new ClientController(memoClient);
                    InventoryController memoInventoryCtrl = new InventoryController(memoBook, memoClient, memoInventory);
                    try {
                        memoBook.save(b1);
                        memoBook.save(b2);
                        memoBook.save(b3);
                        memoBook.save(b4);
                        memoClient.save(c1);
                        memoClient.save(c2);
                        memoClient.save(c3);
                        memoClient.save(c4);
                        memoClient.save(c5);
                        runMenu(memoBookCtrl, memoClientCtrl, memoInventoryCtrl);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                    break;
                }
                case 2: {
                    try {
                        Repo xmlBook = new BookXmlRepository(bookV, "./Data/Books.xml");
                        Repo xmlClient = new ClientXmlRepository(clientV, "./Data/Clients.xml");
                        Repo xmlInventory = new InventoryXmlRepository(inventoryV, "./Data/Inventory.xml");
                        BookController xmlBookCtrl = new BookController(xmlBook);
                        ClientController xmlClientCtrl = new ClientController(xmlClient);
                        InventoryController xmlInventoryCtrl = new InventoryController(xmlBook, xmlClient, xmlInventory);
                        runMenu(xmlBookCtrl, xmlClientCtrl, xmlInventoryCtrl);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    break;
                }
                case 3: {
                    Repo dbBook = new JDBCRepoBook(bookV);
                    Repo dbClient = new JDBCRepoClient(clientV);
                    Repo dbInventory = new JDBCRepoInventory(inventoryV);
                    BookController xmlBookCtrl = new BookController(dbBook);
                    ClientController xmlClientCtrl = new ClientController(dbClient);
                    InventoryController xmlInventoryCtrl = new InventoryController(dbBook, dbClient, dbInventory);
                    runMenu(xmlBookCtrl, xmlClientCtrl, xmlInventoryCtrl);
                    break;
                }
                case 4: {
                    Repo memoBook = null;
                    Repo memoClient = null;
                    Repo memoInventory = null;
                    try {
                        memoBook = new FileBook(bookV, "./Data/boo.txt");
                        memoClient = new FileClient(clientV, "./Data/client.txt");
                        memoInventory = new FileInventory(inventoryV, "./Data/inventory.txt");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    BookController memoBookCtrl = new BookController(memoBook);
                    ClientController memoClientCtrl = new ClientController(memoClient);
                    InventoryController memoInventoryCtrl = new InventoryController(memoBook, memoClient, memoInventory);
                    runMenu(memoBookCtrl, memoClientCtrl, memoInventoryCtrl);
                    break;
                }

                case 5: {
                    Repo yamlBook = new YAMLRepoBook(bookV,"./Data/book.yml");
                    Repo dbClient = new YAMLRepoClient(clientV,"./Data/client.yml");
                    Repo dbInventory = new YAMLRepoInventory(inventoryV,"./Data/inv.yml");
                    BookController xmlBookCtrl = new BookController(yamlBook);
                    ClientController xmlClientCtrl = new ClientController(dbClient);
                    InventoryController xmlInventoryCtrl = new InventoryController(yamlBook, dbClient, dbInventory);
                    runMenu(xmlBookCtrl, xmlClientCtrl, xmlInventoryCtrl);
                    break;
                }
            }


        }
    }
}

