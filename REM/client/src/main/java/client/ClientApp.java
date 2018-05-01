package client;

import common.Book;
import common.ServiceInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("client.config");

        ServiceInterface bookService = (ServiceInterface) context.getBean("serviceClient");

            UI console=new UI(bookService);
//        bookService.remove(10);
//        List<Book> books=bookService.findAll();
//        for(Book b: books)
//        {
//            System.out.println(b);
//        }
        console.run();
        System.out.println("bye - client");
    }
}
