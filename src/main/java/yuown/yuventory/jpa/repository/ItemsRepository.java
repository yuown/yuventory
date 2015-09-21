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
	
	@Query("select sum(weight) from Item item where item.lendTo is null and item.sold = ? and item.itemType = ?")
	Double findAllWeightByItemType(Boolean sold, String type);

	@Query("select distinct name from Item")
	Set<String> findAllItemNames();

	public PageImpl<Item> findAllByNameLike(String name, Pageable pageRequest);
	
	@Query("select sum(weight) from Item item where item.lendTo is null and item.sold = ? and item.itemType = ? and item.supplier = ?")
	Double sumByWeightByItemTypeSupplier(Boolean sold, String type, Supplier supplier);

	@Query("select item.name, count(item.name) from Item item where item.lendTo is null and item.sold = ? group by item.name having count(item.name) <= ? order by count(item.name)")
	public List<Map<String, Integer>> findItemsCount(Boolean sold, Long itemNotifyCount);

	public List<Item> findAllByLendToNotNullAndSoldOrderByLendToDesc(Boolean sold);

	List<Item> findAllByLendToNotNullAndLendDateBetweenAndSoldOrderByLendToDesc(Long start, Long end, boolean b);
}
