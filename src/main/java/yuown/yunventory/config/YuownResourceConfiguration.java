package yuown.yunventory.config;

import java.util.logging.Logger;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import yuown.yunventory.rest.impl.SupplierResourceImpl;
import yuown.yunventory.rest.impl.TestImpl;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Component
public class YuownResourceConfiguration extends ResourceConfig {

	public YuownResourceConfiguration() {
		register(JsonMappingExceptionMapper.class);
		register(JsonParseExceptionMapper.class);
		register(JacksonJsonProvider.class);

		register(TestImpl.class);
		register(SupplierResourceImpl.class);

		registerInstances(new LoggingFilter(Logger.getLogger(YuownResourceConfiguration.class.getName()), true));
	}

}
