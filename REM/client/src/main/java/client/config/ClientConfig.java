package client.config;



import client.service.ServiceClient;
import common.ServiceInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
public class ClientConfig {
    @Bean
    ServiceInterface serviceClient(){
        return new ServiceClient();
    }

    @Bean
    RmiProxyFactoryBean rmiProxyFactoryBean(){
        RmiProxyFactoryBean proxy=new RmiProxyFactoryBean();
        proxy.setServiceInterface(ServiceInterface.class);
        proxy.setServiceUrl("rmi://localhost:1099/ServiceInterface");
        return proxy;
    }
}
