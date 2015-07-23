package yuown.yuventory.jpa.services;

import org.springframework.transaction.annotation.Transactional;

import yuown.yuventory.entity.BaseEntity;
import yuown.yuventory.jpa.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractRepositoryService<R extends BaseRepository<E, ID>, E extends BaseEntity<ID>, ID extends Serializable> implements RepositoryService<E, ID> {

    public List<E> findAll() {
        return repository().findAllByOrderByIdDesc();
    }

    public void delete(ID id) {
        repository().delete(id);
    }

    public E save(E entity) {
        return repository().save(entity);
    }

    public E findOne(ID id) {
        return repository().findOne(id);
    }

    public abstract R repository();

}
