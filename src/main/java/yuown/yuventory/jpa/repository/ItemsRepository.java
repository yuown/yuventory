package yuown.yuventory.jpa.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Item;
import yuown.yuventory.entity.Supplier;

@Repository
public interface ItemsRepository extends BaseRepository<Item, Integer> {
	
	@Query("select sum(weight) from Item item where item.lendTo is null and item.sold = :sold and item.itemType = :type")
	Double findAllWeightByItemType(@Param("sold") Boolean sold, @Param("type") String type);

	@Query("select distinct name from Item")
	Set<String> findAllItemNames();

	public PageImpl<Item> findAllByNameLike(String name, Pageable pageRequest);
	
	@Query("select sum(weight) from Item item where item.lendTo is null and item.sold = :sold and item.itemType = :type and item.supplier = :supplier")
	Double sumByWeightByItemTypeSupplier(@Param("sold") Boolean sold, @Param("type") String type, @Param("supplier") Supplier supplier);

	@Query("select item.name, count(item.name) from Item item where item.lendTo is null and item.sold = :sold group by item.name having count(item.name) <= :count order by count(item.name)")
	public List<Map<String, Integer>> findItemsCount(@Param("sold") Boolean sold, @Param("count") Long itemNotifyCount);

	public List<Item> findAllByLendToNotNullAndSoldOrderByLendToDesc(Boolean sold);

	List<Item> findAllByLendToNotNullAndLendDateBetweenAndSoldOrderByLendToDesc(Long start, Long end, boolean b);

	PageImpl<Item> findAllByValidated(boolean booleanValue, Pageable pageRequest);

	@Query("update Item item set validated = :type where (item.lendTo is null or item.lendTo = 0) and item.sold = 0")
	@Modifying
	public void saveAllAsValid(@Param("type") Boolean flag);
}
