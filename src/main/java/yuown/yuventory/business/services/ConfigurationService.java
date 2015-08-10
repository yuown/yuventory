package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Configuration;
import yuown.yuventory.jpa.services.ConfigurationRepositoryService;
import yuown.yuventory.model.ConfigurationModel;
import yuown.yuventory.transformer.ConfigurationTransformer;

@Service
public class ConfigurationService extends AbstractServiceImpl<Integer, ConfigurationModel, Configuration, ConfigurationRepositoryService, ConfigurationTransformer> {

	@Autowired
	private ConfigurationRepositoryService configurationRepositoryService;

	@Autowired
	private ConfigurationTransformer configurationTransformer;

	@Override
	protected ConfigurationRepositoryService repoService() {
		return configurationRepositoryService;
	}

	@Override
	protected ConfigurationTransformer transformer() {
		return configurationTransformer;
	}

	public ConfigurationModel getByName(String name) {
		return transformer().transformTo(repoService().findByName(name));
	}
}
