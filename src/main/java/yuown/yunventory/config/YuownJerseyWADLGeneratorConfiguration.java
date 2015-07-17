package yuown.yunventory.config;

import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorJAXBGrammarGenerator;

import java.util.List;

public class YuownJerseyWADLGeneratorConfiguration extends WadlGeneratorConfig {

    @Override
    public List<WadlGeneratorDescription> configure() {
        return generator(WadlGeneratorJAXBGrammarGenerator.class).descriptions();
    }
}