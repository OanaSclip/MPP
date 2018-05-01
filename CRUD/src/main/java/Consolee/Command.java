package Consolee;

public abstract class Command {
    protected Integer key;
    protected String desc;

    Command(Integer _key, String _desc) {
        this.key = _key;
        this.desc = _desc;
    }
    
    Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public abstract void exec();

    @Override
    public String toString() {
        return this.key + ". " + this.desc;
    }
}
