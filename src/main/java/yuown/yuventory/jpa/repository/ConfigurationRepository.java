package yuown.yuventory.jpa.repository;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Configuration;

@Repository
public interface ConfigurationRepository extends BaseRepository<Configuration, Integer> {

	public Configuration findByName(String name);

}
