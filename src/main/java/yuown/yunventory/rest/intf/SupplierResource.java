package yuown.yunventory.rest.intf;

import yuown.yunventory.model.SupplierModel;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/suppliers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface SupplierResource {

    @POST
    public SupplierModel save(SupplierModel model);

    @GET
    public SupplierModel getById(@QueryParam("id") int id);

    @GET
    public List<SupplierModel> getByName(@QueryParam("name") String name);

    @GET
    public List<SupplierModel> getAll();

    @DELETE
    public Response removeById(@QueryParam("id") int id);

}
