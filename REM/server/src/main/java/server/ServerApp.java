package server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ServerApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("server.config");



        System.out.println("bye - server");
    }
}
