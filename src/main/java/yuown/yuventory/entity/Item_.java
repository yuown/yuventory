package yuown.yuventory.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Item.class)
public class Item_ {

	public static volatile SingularAttribute<Item, Integer> id;
	public static volatile SingularAttribute<Item, String> name;
	public static volatile SingularAttribute<Item, String> itemType;
	public static volatile SingularAttribute<Item, Category> category;
	public static volatile SingularAttribute<Item, Long> createDate;
	public static volatile SingularAttribute<Item, Long> updateDate;
	public static volatile SingularAttribute<Item, Supplier> supplier;
	public static volatile SingularAttribute<Item, StockType> stockType;
	public static volatile SingularAttribute<Item, Supplier> lendTo;
	public static volatile SingularAttribute<Item, Long> lendDate;
	public static volatile SingularAttribute<Item, Boolean> sold;

}
