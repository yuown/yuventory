package yuown.yunventory.repository.services;

import yuown.yunventory.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface RepositoryService<E extends BaseEntity<ID>, ID extends Serializable> {

    E findById(ID id);
    
    List<E> findAll();

    Map<ID, E> findAllAsMap();

    Map<ID, E> findAllAsMap(Iterable<? extends E> entities);

    E saveOrUpdate(E entity);

    List<E> saveOrUpdateAll(Iterable<? extends E> entities);
    
    void remove(E entity);

    void removeAll(Iterable<E> entities);
}
