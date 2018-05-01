package server.repository;


import common.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class ClientJdbcRepo implements Repository<Integer, Client> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public ClientJdbcRepo() {
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM client";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Client.class));
    }



    @Override
    public void update(Client book) {
        String sql = "update client set name=?, spentmoney=? where id=?";
        jdbcTemplate.update(sql, book.getName(), book.getSpentMoney(),  book.getId());
    }

    @Override
    public void remove(Integer id) {
        String sql = "delete from client where id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public void add(Client book) {
        String sql = "insert into Client (id,name,spentmoney) values (?,?,?)";
        System.out.println("din save client service impl");
        jdbcTemplate.update(sql, book.getId(), book.getName(), book.getSpentMoney());

    }

}
