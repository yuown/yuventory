package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Configuration;
import yuown.yuventory.model.ConfigurationModel;

@Component
public class ConfigurationTransformer extends AbstractDTOTransformer<ConfigurationModel, Configuration> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public Configuration transformFrom(ConfigurationModel source) {
		Configuration dest = null;
		if (source != null) {
			try {
				dest = new Configuration();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public ConfigurationModel transformTo(Configuration source) {
		ConfigurationModel dest = null;
		if (source != null) {
			try {
				dest = new ConfigurationModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
