package Validator;

import Domain.Inventory;
import Exceptions.ValidatorException;

public class InventoryValidator<T> implements Validator<T> {
    @Override
    public void validate(T entity) throws ValidatorException {
        Inventory inv = (Inventory) entity;
        if (inv.getIdEntity() < 0)
            throw new ValidatorException("id must be greater than 0");

    }
}
