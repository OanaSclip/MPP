package server.repository;

import common.Base;

import java.util.List;


public interface Repository<ID,T extends Base> {
    void add(T entity);
    void remove(ID id);
    void update(T entity);
    List<T> findAll();


}
