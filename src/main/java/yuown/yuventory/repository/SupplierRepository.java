package yuown.yuventory.repository;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Supplier;

import java.util.List;

@Repository
public interface SupplierRepository extends BaseRepository<Supplier, Integer> {

    public <S extends Supplier> S save(S entity);

    public List<Supplier> findAll();

    public void delete(Integer id);

    public Supplier findOne(Integer id);

}
