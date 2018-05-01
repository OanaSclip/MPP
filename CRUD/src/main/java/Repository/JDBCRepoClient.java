package Repository;

import Domain.Client;
import Exceptions.ValidatorException;
import Validator.Validator;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JDBCRepoClient implements Repo<Integer, Client> {
    private static final String URL = "jdbc:postgresql://localhost:5432/BookStore";
    private static final String user = "postgres";
    private static final String password = "root";
    private Validator<Client> validator;

    public JDBCRepoClient(Validator<Client> validator) {
        this.validator = validator;
    }


    @Override
    public Optional<Client> findOne(Integer id) throws ValidatorException {
        Iterable<Client> clients = findAll();

        Stream<Client> targetStream = StreamSupport.stream(clients.spliterator(), false);
        return targetStream.filter(c -> c.getIdEntity().equals(id)).findFirst();

    }

    @Override
    public Iterable<Client> findAll() throws ValidatorException {
        Map<Integer, Client> clients = new HashMap<>();
        String sql = "select * from client";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int money = rs.getInt("spentmoney");
                Client c = new Client(id, name, money);
                clients.put(id, c);
            }
            return clients.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());

        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
    }

    @Override
    public Optional save(Client client) throws ValidatorException, IOException {
        validator.validate(client);
        String sql = "insert into client (id,name, spentmoney) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, client.getIdEntity());
            statement.setString(2, client.getName());
            statement.setInt(3, client.getSpentMoney());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
        return null;
    }

    @Override
    public Optional update(Client client) throws ValidatorException, IOException {
        validator.validate(client);
        String sql = "UPDATE client set name=?, spentmoney=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, client.getName());
            statement.setInt(2, client.getSpentMoney());
            statement.setLong(3, client.getIdEntity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
        return null;
    }


    @Override
    public Optional<Client> delete(Integer id) throws IOException {
        String sql = "DELETE from client where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IOException("cannont connect to the database!\n");

        }

        return null;
    }

}

