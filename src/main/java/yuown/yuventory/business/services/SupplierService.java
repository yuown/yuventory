package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Supplier;
import yuown.yuventory.model.SupplierModel;
import yuown.yuventory.repository.SupplierRepositoryService;
import yuown.yuventory.transformer.SupplierTransformer;

@Service
public class SupplierService extends AbstractServiceImpl<Integer, SupplierModel, Supplier, SupplierRepositoryService, SupplierTransformer> {

    @Autowired
    private SupplierRepositoryService supplierRepositoryService;

    @Autowired
    private SupplierTransformer supplierTransformer;

    @Override
    protected SupplierRepositoryService repoService() {
        return supplierRepositoryService;
    }

    @Override
    protected SupplierTransformer transformer() {
        return supplierTransformer;
    }
}
