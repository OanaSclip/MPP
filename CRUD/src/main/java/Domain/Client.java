package Domain;

public class Client extends Base<Integer> implements Comparable<Client> {
    //private fields
    private String name;
    private Integer spentMoney;

    //constructors
    public Client(Integer id, String n, Integer spent) {
        super(id);
        name = n;
        spentMoney = spent;
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

    //overriding string method
    @Override
    public String toString() {
        return "Client: " + super.toString() + ", name-> " + name + ", spent money-> " + spentMoney + "\n";
    }

    @Override
    public String toStr() {
        return
                super.toStr() +","+
                        name+","+
                        spentMoney +

                        '\n';
    }
    @Override
    public int compareTo(Client o) {

        return this.name.compareTo(o.getName());
    }

}
