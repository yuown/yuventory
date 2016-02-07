package yuown.yuventory.jpa.repository;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.ItemArc;

@Repository
public interface ItemsArcRepository extends BaseRepository<ItemArc, Integer> {

	ItemArc findByItemId(int id);

}