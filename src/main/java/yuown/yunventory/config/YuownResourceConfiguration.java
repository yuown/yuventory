package yuown.yunventory.config;

import com.fasterxml.jackson.jaxrs.base.JsonMappingExceptionMapper;
import com.fasterxml.jackson.jaxrs.base.JsonParseExceptionMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import yuown.yunventory.rest.impl.TestImpl;

import java.util.logging.Logger;

@Component
public class YuownResourceConfiguration extends ResourceConfig {

    public YuownResourceConfiguration() {
        register(JsonMappingExceptionMapper.class);
        register(JsonParseExceptionMapper.class);
        register(JacksonJsonProvider.class);

        register(TestImpl.class);

        registerInstances(new LoggingFilter(Logger.getLogger(YuownResourceConfiguration.class.getName()), true));
    }

}
