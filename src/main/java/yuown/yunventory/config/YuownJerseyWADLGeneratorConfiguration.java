package yuown.yunventory.config;

import java.util.ArrayList;
import java.util.List;

import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;

public class YuownJerseyWADLGeneratorConfiguration extends WadlGeneratorConfig {

	@Override
	public List<WadlGeneratorDescription> configure() {
		List<WadlGeneratorDescription> list = new ArrayList<WadlGeneratorDescription>();
		return list;
	}
}