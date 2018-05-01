package server.config;


import common.ServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import server.service.ServiceImpl;

@Configuration
@ComponentScan({"server.repository","server.service"})
public class ServerConfig {
    @Bean
    ServiceInterface serviceInterface() {
        ServiceInterface service = new ServiceImpl();
//        System.out.println("configurare service");
        return service;
    }

    @Bean
    RmiServiceExporter rmiServiceExporter(){
        RmiServiceExporter exporter=new RmiServiceExporter();
        exporter.setServiceName("ServiceInterface");
        exporter.setServiceInterface(ServiceInterface.class);
        exporter.setService(serviceInterface());
        return exporter;
    }
}
