package yuown.yuventory.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Supplier;
import yuown.yuventory.jpa.repository.SupplierRepository;

import java.util.List;

@Service("supplierRepositoryService")
public class SupplierRepositoryService extends AbstractRepositoryService<SupplierRepository, Supplier, Integer> {

    @Autowired
    private SupplierRepository repository;

    @Override
    public SupplierRepository repository() {
        return repository;
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public List<Supplier> findAll() {
        return super.findAll();
    }

    @Override
    public Supplier findOne(Integer id) {
        return super.findOne(id);
    }

    @Override
    public Supplier save(Supplier entity) {
        return super.save(entity);
    }
}
