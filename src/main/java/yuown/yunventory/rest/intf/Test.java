package yuown.yunventory.rest.intf;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public interface Test {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test(String name);

}
