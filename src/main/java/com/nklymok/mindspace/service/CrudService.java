package com.nklymok.mindspace.service;

import com.nklymok.mindspace.entity.Task;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, K> {

    T findById(K id);

    List<T> findAll();

    T update(T t);

    void delete(T t);

    void deleteById(K id);

    T save(T t);


}
