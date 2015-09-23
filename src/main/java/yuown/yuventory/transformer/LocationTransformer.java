package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Location;
import yuown.yuventory.model.LocationModel;

@Component
public class LocationTransformer extends AbstractDTOTransformer<LocationModel, Location> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public Location transformFrom(LocationModel source) {
		Location dest = null;
		if (source != null) {
			try {
				dest = new Location();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public LocationModel transformTo(Location source) {
		LocationModel dest = null;
		if (source != null) {
			try {
				dest = new LocationModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
