package Domain;

public abstract class Base<I> {
    protected I idEntity;

    public Base(I id) {
        idEntity = id;
    }

    public Base() {
    }

    public void setIdEntity(I idEntity) {
        this.idEntity = idEntity;
    }

    public I getIdEntity() {
        return idEntity;
    }

    public String toStr() {
        return idEntity.toString();
    }

    @Override
    public String toString() {
        return idEntity.toString()+" ";
    }

}
