package Controller;

import Domain.Client;
import Exceptions.ValidatorException;
import Repository.Repo;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ClientController {
    private Repo<Integer, Client> repo;

    public ClientController(Repo<Integer, Client> repo) {
        this.repo = repo;
    }

    /**
     * Receives a Client and calls the save method from the repository with that param
     *
     * @param c
     * @throws ValidatorException
     */

    public void addClient(Client c) throws Exception {
        repo.save(c);
    }

    /**
     * Receives in Integer and calls the method delete from the repository with that param
     *
     * @param id
     * @throws ValidatorException
     */
    public void deleteClient(Integer id) throws Exception {

        repo.delete(id);
    }

    /**
     * Receives a Client and calls the update method from the repository with that param
     *
     * @param c
     * @throws ValidatorException
     */


    public void updateClient(Client c) throws Exception {

            repo.update(c);
        }

        /**
         * Returns a Set containing all the Clients currently in the java.Repository
         *
         * @return
         */
        public Set<Client> getAllClients () throws ValidatorException {
            Iterable<Client> clients = repo.findAll();
            return StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());

        }

    public List<Client> clientsFilterSorted() throws ValidatorException {
        Iterable<Client> clients = repo.findAll();
        return StreamSupport.stream(clients.spliterator(), false).filter(client -> client.getSpentMoney()>10).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }


    /**
         * Returns a Set of Clients sorted by name
         *
         * @return
         */
        public Set<Client> sortByName () throws ValidatorException {

            Iterable<Client> clients = repo.findAll();
            return StreamSupport.stream(clients.spliterator(), false).sorted().collect(Collectors.toSet());
        }
    }
