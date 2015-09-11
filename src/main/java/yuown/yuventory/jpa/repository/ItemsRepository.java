package yuown.yuventory.jpa.repository;

import java.util.Set;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Item;

@Repository
public interface ItemsRepository extends BaseRepository<Item, Integer> {

	@Query("select sum(weight) from Item where itemType = ?")
	Double findAllWeightByItemType(String type);

	@Query("select distinct name from Item")
	Set<String> findAllItemNames();

	public PageImpl<Item> findAllByName(String name, Pageable pageRequest);
}
