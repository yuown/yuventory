package yuown.yuventory.jpa.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import yuown.yuventory.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<E, ID> {

	public List<E> findAllByOrderByIdDesc();

}