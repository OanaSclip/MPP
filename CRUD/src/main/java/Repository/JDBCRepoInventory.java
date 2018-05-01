package Repository;


import Domain.Inventory;
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

public class JDBCRepoInventory implements Repo<Integer, Inventory> {

    private static final String URL = "jdbc:postgresql://localhost:5432/BookStore";
    private static final String user = "postgres";
    private static final String password = "root";
    private Validator<Inventory> validator;

    public JDBCRepoInventory(Validator<Inventory> validator) {
        this.validator = validator;
    }

    /**
     * @param id
     * @return null if
     * @throws ValidatorException
     */
    @Override
    public Optional<Inventory> findOne(Integer id) throws ValidatorException {
        Iterable<Inventory> inv = findAll();

        Stream<Inventory> targetStream = StreamSupport.stream(inv.spliterator(), false);
        return targetStream.filter(c -> c.getIdEntity().equals(id)).findFirst();

    }

    @Override
    public Iterable<Inventory> findAll() throws ValidatorException {
        Map<java.lang.Integer, Inventory> clients = new HashMap<>();
        String sql = "select * from inventory";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idc = rs.getInt("idclient");
                int idb = rs.getInt("idbook");
                Inventory inv = new Inventory(id, idc, idb);
                clients.put(id, inv);
            }
            return clients.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());

        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
    }

    @Override
    public Optional save(Inventory inv) throws ValidatorException, IOException {
        validator.validate(inv);
        String sql = "insert into inventory (id,idclient, idbook) values (?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, inv.getIdEntity());
            statement.setInt(2, inv.getIdClient());
            statement.setInt(3, inv.getIdBook());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
        return null;
    }

    @Override
    public Optional update(Inventory inv) throws ValidatorException, IOException {
        validator.validate(inv);
        String sql = "UPDATE Inventory set idclient=?, idbook=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, inv.getIdClient());
            statement.setInt(2, inv.getIdBook());
            statement.setLong(3, inv.getIdEntity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");
        }
        return null;
    }


    @Override
    public Optional<Inventory> delete(Integer id) throws IOException, ValidatorException {
        String sql = "DELETE from Inventory where id=?";
        try (Connection connection = DriverManager.getConnection(URL, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ValidatorException("cannont connect to the database!\n");

        }

        return null;
    }
}

