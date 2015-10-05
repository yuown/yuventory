package yuown.yuventory.jpa.repository;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Customer;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Integer> {

}
