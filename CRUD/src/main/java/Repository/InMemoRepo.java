package Repository;


import Domain.Base;
import Exceptions.ValidatorException;
import Validator.Validator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoRepo<ID, T extends Base<ID>> implements Repo<ID, T> {
    protected Map<ID, T> entities;
    protected Validator<T> validator;

    public InMemoRepo(){}
    public InMemoRepo(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }



    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return entities.entrySet().stream().filter(a->a.getKey()==id).map(Map.Entry::getValue).findFirst();
        //return entities.entrySet().stream().filter(a -> a.getKey().equals(id)).map(Map.Entry::getValue).findFirst();

    }

    @Override
    public Iterable<T> findAll() {
        return entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
    }

    @Override


    public Optional<T> save(T entity) throws ValidatorException, IOException, Exception {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getIdEntity(), entity));
    }


    @Override


    public Optional<T> delete(ID id) throws ValidatorException,IOException, Exception {

        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override

    public Optional<T> update(T entity) throws ValidatorException, IOException, Exception {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getIdEntity(), (k, v) -> entity));
    }
}