package yuown.yunventory.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yunventory.entity.Supplier;
import yuown.yunventory.repository.services.AbstractRepositoryService;

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
    public List<Supplier> findByName(String supplierName) {
        return repository().findByName(supplierName);
    }

    public List<Supplier> findAllByDescOrder() {
        return repository().findAll();
    }
}
