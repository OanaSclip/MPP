package Domain;


public class Inventory extends Base<Integer> {
    //fields
    private Integer idClient;
    private Integer idBook;


    //constructor
    public Inventory(Integer idInventory, Integer idClient, Integer idBook) {
        super(idInventory);

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

    //overriding string method from Object superclass
    @java.lang.Override
    public java.lang.String toString() {
        return "Inventory: " +
                super.toString() +
                ", idClient-> " + idClient +
                ", idBook-> " + idBook +
                '\n';
    }
    @Override
    public String toStr() {
        return
                super.toStr() +","+
                        idClient+","+
                        idBook +

                        '\n';
    }
}
