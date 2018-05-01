package Validator;

import Domain.Client;
import Exceptions.ValidatorException;

public class ClientValidator<T> implements Validator<T> {
    @Override
    public void validate(T entity) throws ValidatorException {
        Client c = (Client) entity;
        if (!Character.isUpperCase(c.getName().codePointAt(0)))
            throw new ValidatorException("Name must start with an upper case");
        if (c.getIdEntity() < 0)
            throw new ValidatorException("id must be greater than 0");
    }
}
