package yuown.yuventory.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Configuration.class)
public class Configuration_ {

	public static volatile SingularAttribute<Configuration, Integer> id;
	public static volatile SingularAttribute<Configuration, String> name;
}
