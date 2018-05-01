package Repository;

import Domain.Base;
import Exceptions.ValidatorException;

import java.io.IOException;
import java.util.Optional;

public interface Repo<ID, T extends Base<ID>> {
    Optional<T> findOne(ID id) throws ValidatorException;

    Iterable<T> findAll() throws ValidatorException;

    Optional<T> save(T entity) throws ValidatorException, IOException, Exception;

    Optional<T> delete(ID id) throws IOException, ValidatorException, Exception;

    Optional<T> update(T entity) throws ValidatorException, IOException, Exception;



    // void savee(T c) throws ValidatorException;
}
