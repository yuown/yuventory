package yuown.yuventory.jpa.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import yuown.yuventory.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

	public List<E> findAllByOrderByIdDesc();

	public PageImpl<E> findAllByOrderByIdDesc(Pageable pageRequest);
}
