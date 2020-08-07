package com.nklymok.mindspace.service;

import java.util.List;

public interface CrudService<T, K> {

    T findById(K id);

    List<T> findAll();

    T save(T t);

    T update(T t);

    void delete(T t);

    void deleteById(K id);

}
