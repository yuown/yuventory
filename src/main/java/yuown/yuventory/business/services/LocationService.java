package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Location;
import yuown.yuventory.jpa.services.LocationRepositoryService;
import yuown.yuventory.model.LocationModel;
import yuown.yuventory.transformer.LocationTransformer;

@Service
public class LocationService extends AbstractServiceImpl<Integer, LocationModel, Location, LocationRepositoryService, LocationTransformer> {

	@Autowired
	private LocationRepositoryService locationRepositoryService;

	@Autowired
	private LocationTransformer locationTransformer;

	@Override
	protected LocationRepositoryService repoService() {
		return locationRepositoryService;
	}

	@Override
	protected LocationTransformer transformer() {
		return locationTransformer;
	}
}
