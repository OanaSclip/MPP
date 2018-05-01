package common;

import java.io.Serializable;

public class Client extends Base implements Comparable<Client>, Serializable {
    //private fields
    private String name;
    private Integer spentMoney;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //constructors
    public Client(Integer id, String n, Integer spent) {
        this.id=id;
        name = n;
        spentMoney = spent;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", spentMoney=" + spentMoney +
                ", id=" + id +
                '}';
    }

    //getters
    public Integer getSpentMoney() {
        return spentMoney;
    }

    public String getName() {
        return name;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpentMoney(Integer spentMoney) {
        this.spentMoney = spentMoney;
    }

    @Override
    public int compareTo(Client o) {

        return this.name.compareTo(o.getName());
    }

}
