package yuown.yuventory.rest.intf;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.model.SupplierModel;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping(value = "/suppliers", produces = { MediaType.APPLICATION_JSON })
public interface SupplierResource {

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON })
    public SupplierModel save(SupplierModel model);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public SupplierModel getById(@PathVariable int id);

    @RequestMapping(method = RequestMethod.GET)
    public List<SupplierModel> getAll();

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Response removeById(@PathVariable int id);

}
