package cz.cvut.fel.ear.planIt.services;

import cz.cvut.fel.ear.planIt.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


public abstract class BaseService<T> {

    protected abstract BaseDao<T> getDao();

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Transactional(readOnly = true)
    public T find(Integer id) {
        return getDao().find(id);
    }

    @Transactional
    public void persist(T instance) {
        Objects.requireNonNull(instance);
        getDao().persist(instance);
    }

    @Transactional
    public void update(T instance) {
        Objects.requireNonNull(instance);
        getDao().update(instance);
    }

}
