package yuown.yunventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yunventory.entity.Supplier;
import yuown.yunventory.model.SupplierModel;
import yuown.yunventory.repository.SupplierRepositoryService;
import yuown.yunventory.transformer.SupplierTransformer;

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
