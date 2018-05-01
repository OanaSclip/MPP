package client;


import common.Book;
import common.Client;
import common.ServiceInterface;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class UI {

    private ServiceInterface service;


    public UI(ServiceInterface s) {
        service = s;
    }

    public void printMenu() {
        System.out.println("Options: ");
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Update book");
        System.out.println("4. List all books");
        System.out.println("5. Add client");
        System.out.println("6. Remove client");
        System.out.println("7. Update client");
        System.out.println("8. List all clients");
        System.out.println("0. Exit");
        System.out.println("Option: ");
    }

    public void run() {
        boolean choice = true;
        int option;
        while (choice == true) {
            printMenu();
            Scanner c = new Scanner(System.in);
            option = c.nextInt();

            c.nextLine();
            switch (option) {
                case 1: {
                    add();
                    break;
                }
                case 2: {
                    remove();
                    break;
                }
                case 3: {
                    update();
                }
                case 4: {
                    findAll();
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
                    findAllClients();
                    break;
                }
                case 0: {
                    choice = false;
                }
            }
        }
    }

    private void add() {
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
            // String string = b.getIdEntity().toString() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getPrice().toString() + "," + b.getGenre();
            this.service.add(b);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void remove() {
        Integer id;
        System.out.println("ID to remove: ");
        Scanner a = new Scanner(System.in);
        id = Integer.valueOf(a.next());
        //a.nextLine();
        this.service.remove(id);
    }

    private void update() {
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
            //String string = b.getIdEntity().toString() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getPrice().toString() + "," + b.getGenre();
            this.service.update(b);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Book> findAll() {
        List var10000 = this.service.findAll();
        PrintStream var10001 = System.out;
        System.out.getClass();
        var10000.forEach(var10001::println);
        return var10000;
    }

    private void addClient() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID Client ");
        String id = s.nextLine();
        System.out.println("Name: ");
        String t = s.nextLine();
        System.out.println("Money spent: ");
        Integer p = s.nextInt();
        Client c = new Client(Integer.parseInt(id), t, p);
        try {
            // String string = b.getIdEntity().toString() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getPrice().toString() + "," + b.getGenre();
            this.service.addClient(c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void removeClient() {
        Integer id;
        System.out.println("ID to remove: ");
        Scanner a = new Scanner(System.in);
        id = Integer.valueOf(a.next());
        this.service.removeClient(id);
    }

    private void updateClient() {
        Scanner s = new Scanner(System.in);
        System.out.println("ID to update: ");
        String id = s.nextLine();
        System.out.println("New name: ");
        String t = s.nextLine();
        System.out.println("New money spent: ");
        String a = s.nextLine();
        Client c = new Client(Integer.parseInt(id), t,Integer.parseInt(a));
        try {
            //String string = b.getIdEntity().toString() + "," + b.getTitle() + "," + b.getAuthor() + "," + b.getPrice().toString() + "," + b.getGenre();
            this.service.updateClient(c);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Client> findAllClients() {
        List var10000 = this.service.findAllClients();
        PrintStream var10001 = System.out;
        System.out.getClass();
        var10000.forEach(var10001::println);
        return var10000;
    }
}
