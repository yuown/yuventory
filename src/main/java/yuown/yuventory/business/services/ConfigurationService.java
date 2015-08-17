package yuown.yuventory.business.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Configuration;
import yuown.yuventory.entity.Configuration_;
import yuown.yuventory.jpa.services.ConfigurationRepositoryService;
import yuown.yuventory.model.ConfigurationModel;
import yuown.yuventory.transformer.ConfigurationTransformer;

@Service
@Scope("singleton")
public class ConfigurationService extends AbstractServiceImpl<Integer, ConfigurationModel, Configuration, ConfigurationRepositoryService, ConfigurationTransformer> {

	@Autowired
	private ConfigurationRepositoryService configurationRepositoryService;

	@Autowired
	private ConfigurationTransformer configurationTransformer;

	@Value("${settings.to.init}")
	private String settingsToInit;

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

	@PostConstruct
	public void init() {
		saveSettingsToSystem();
	}

	@Override
	public ConfigurationModel save(ConfigurationModel resource) {
		ConfigurationModel saved = super.save(resource);
		saveSettingsToSystem(saved.getName(), saved);
		return saved;
	}

	public List<ConfigurationModel> getSettings(final String[] settingNames) {
		return transformer().transformTo(configurationRepositoryService.findAll(new Specification<Configuration>() {
			@Override
			public Predicate toPredicate(Root<Configuration> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				for (int i = 0; i < settingNames.length; i++) {
					predicates.add(cb.equal(root.get(Configuration_.name), settingNames[i].toUpperCase()));
				}
				return cb.or(predicates.toArray(new Predicate[0]));
			}
		}));
	}

	public void saveSettings(List<ConfigurationModel> settings) {
		List<String> names = new ArrayList<String>();
		Map<String, ConfigurationModel> mapped = new HashMap<String, ConfigurationModel>();
		for (ConfigurationModel configurationModel : settings) {
			names.add(configurationModel.getName());
			mapped.put(configurationModel.getName(), configurationModel);
		}
		List<ConfigurationModel> settingsFromDB = getSettings(names.toArray(new String[0]));
		for (Iterator<ConfigurationModel> iterator = settingsFromDB.iterator(); iterator.hasNext();) {
			ConfigurationModel configurationModel = iterator.next();
			ConfigurationModel toSave = mapped.get(configurationModel.getName());
			toSave.setValue(configurationModel.getValue());
			configurationRepositoryService.save(transformer().transformFrom(toSave));
			saveSettingsToSystem(configurationModel.getName(), configurationModel);
			mapped.remove(configurationModel.getName());
		}
		if (mapped.size() > 0) {
			for (Iterator<ConfigurationModel> iterator = settings.iterator(); iterator.hasNext();) {
				ConfigurationModel configurationModel = iterator.next();
				configurationRepositoryService.save(transformer().transformFrom(configurationModel));
				saveSettingsToSystem(configurationModel.getName(), configurationModel);
				mapped.remove(configurationModel.getName());
			}
		}
	}

	public void saveSettingsToSystem(String name, ConfigurationModel valueModel) {
		int value = 0;
		if (valueModel != null) {
			value = valueModel.getValue();
		}
		System.setProperty(name, Integer.toString(value));
	}

	public void saveSettingsToSystem() {
		String[] names = StringUtils.split(settingsToInit, ",");
		List<ConfigurationModel> settingsFromDB = getSettings(names);
		for (ConfigurationModel configurationModel : settingsFromDB) {
			saveSettingsToSystem(configurationModel.getName(), configurationModel);
		}
	}
}
