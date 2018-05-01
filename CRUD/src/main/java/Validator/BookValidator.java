package Validator;

import Domain.Book;
import Exceptions.ValidatorException;

public class BookValidator<T> implements Validator<T> {
    @Override
    public void validate(T entity) throws ValidatorException {
        Book b = (Book) entity;

        if (b.getIdEntity() < 0)
            throw new ValidatorException("id must be greater than 0");

    }
}
