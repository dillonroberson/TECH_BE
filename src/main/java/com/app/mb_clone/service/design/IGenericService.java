package com.app.mb_clone.service.design;

import java.util.List;

public interface IGenericService<E> {
    List<E> findAll();

    E findById(Long id);

    E save(E e);

    void remove(Long id);

}
