package yuown.yuventory.jpa.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Item;
import yuown.yuventory.entity.Supplier;

@Repository
public interface ItemsRepository extends BaseRepository<Item, Integer> {
	
	@Query("select sum(weight) from Item where itemType = ?")
	Double findAllWeightByItemType(String type);

	@Query("select distinct name from Item")
	Set<String> findAllItemNames();

	public PageImpl<Item> findAllByNameLike(String name, Pageable pageRequest);
	
	@Query("select sum(weight) from Item where itemType = ? and supplier = ?")
	Double sumByWeightByItemTypeSupplier(String type, Supplier supplier);

	@Query("select item.name, count(item.name) from Item item where item.lendTo is null and item.sold = ? group by item.name having count(item.name) <= ? order by count(item.name)")
	public List<Map<String, Integer>> findItemsCount(Boolean sold, Long itemNotifyCount);
}
