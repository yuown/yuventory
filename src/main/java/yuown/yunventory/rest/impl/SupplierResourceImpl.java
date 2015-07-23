package yuown.yunventory.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import yuown.yunventory.business.services.SupplierService;
import yuown.yunventory.model.SupplierModel;
import yuown.yunventory.rest.intf.SupplierResource;

import java.util.List;

import javax.ws.rs.core.Response;

@Component
public class SupplierResourceImpl implements SupplierResource {

    @Autowired
    private SupplierService supplierService;

    public SupplierModel save(SupplierModel model) {
        return supplierService.saveOrUpdate(model);
    }

    public SupplierModel getById(int id) {
        return supplierService.getById(id);
    }

//    public List<SupplierModel> getByName(String name) {
//        return supplierService.getByName(name);
//    }

//    public List<SupplierModel> getAll() {
//        return supplierService.getAll();
//    }

    public Response removeById(int id) {
        SupplierModel supplier = supplierService.getById(id);
        if (null == supplier) {
            return Response.status(Response.Status.NOT_FOUND).entity("Supplier with ID " + id + " Not Found").build();
        } else {
            return Response.status(Response.Status.OK).entity("Supplier with ID " + id + " Deleted Successfully").build();
        }
    }
}
