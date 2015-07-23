package yuown.yuventory.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.business.services.SupplierService;
import yuown.yuventory.model.SupplierModel;
import yuown.yuventory.rest.intf.SupplierResource;

import java.util.List;

import javax.ws.rs.core.Response;

@Service
public class SupplierResourceImpl implements SupplierResource {

    @Autowired
    private SupplierService supplierService;

    public SupplierModel save(SupplierModel model) {
        return supplierService.save(model);
    }

    public SupplierModel getById(int id) {
        return supplierService.getById(id);
    }

    public Response removeById(int id) {
        SupplierModel supplier = supplierService.getById(id);
        if (null == supplier) {
            return Response.status(Response.Status.NOT_FOUND).entity("Supplier with ID " + id + " Not Found").build();
        } else {
            return Response.status(Response.Status.OK).entity("Supplier with ID " + id + " Deleted Successfully").build();
        }
    }

    public List<SupplierModel> getAll() {
        return supplierService.getAll();
    }
}
