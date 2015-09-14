package yuown.yuventory.jpa.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Supplier;

@Repository
public interface SupplierRepository extends BaseRepository<Supplier, Integer> {

	List<Supplier> findAllByOrderByNameDesc();

}
