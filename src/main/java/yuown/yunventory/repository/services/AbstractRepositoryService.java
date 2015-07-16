package yuown.yunventory.repository.services;

import org.springframework.transaction.annotation.Transactional;

import yuown.yunventory.entity.BaseEntity;
import yuown.yunventory.repository.BaseRepository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractRepositoryService<R extends BaseRepository<E, ID>, E extends BaseEntity<ID>, ID extends Serializable> implements RepositoryService<E, ID> {

    @Transactional(readOnly = true)
    public E findById(ID id) {
        return repository().findOne(id);
    }

    @Transactional(readOnly = true)
    public List<E> findAll() {
        return repository().findAll();
    }

    @Transactional(readOnly = true)
    public Map<ID, E> findAllAsMap() {
        return findAllAsMap(repository().findAll());
    }

    public Map<ID, E> findAllAsMap(Iterable<? extends E> entities) {
        Map<ID, E> rowsMap = new HashMap<ID, E>();
        if (entities != null) {
            for (E entity : entities) {
                rowsMap.put(entity.getId(), entity);
            }
        }
        return rowsMap;
    }

    public E saveOrUpdate(E entity) {
        return repository().save(entity);
    }

    @SuppressWarnings("unchecked")
    public List<E> saveOrUpdateAll(Iterable<? extends E> entities) {
        return (List<E>) repository().save(entities);
    }

    public void remove(E entity) {
        entity.setEnabled(false);
        repository().save(entity);
    }

    public void removeAll(Iterable<E> entities) {
        repository().deleteInBatch(entities);
    }

    public abstract R repository();
}
