package common;


import java.io.Serializable;

public class Inventory extends Base  implements Serializable {
    //fields
    private Integer idClient;
    private Integer idBook;
    private Integer id;


    //constructor
    public Inventory(Integer idInventory, Integer idClient, Integer idBook) {
        this.id=idInventory;

        this.idClient = idClient;
        this.idBook = idBook;

    }


    //getters for fields
    public Integer getIdClient() {
        return idClient;
    }

    public Integer getIdBook() {
        return idBook;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "idClient=" + idClient +
                ", idBook=" + idBook +
                ", id=" + id +
                '}';
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

