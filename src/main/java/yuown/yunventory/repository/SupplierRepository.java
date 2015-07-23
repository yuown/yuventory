package yuown.yunventory.repository;

import org.springframework.stereotype.Repository;

import yuown.yunventory.entity.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends BaseRepository<Supplier, Integer> {

//    List<Supplier> findAllOrderByIdDesc();

}
