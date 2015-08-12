package yuown.yuventory.business.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Category;
import yuown.yuventory.entity.Item;
import yuown.yuventory.entity.Item_;
import yuown.yuventory.entity.StockType;
import yuown.yuventory.entity.Supplier;
import yuown.yuventory.jpa.services.ItemsRepositoryService;
import yuown.yuventory.model.ConfigurationModel;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.model.ReportRequestModel;
import yuown.yuventory.transformer.ItemTransformer;

@Service
public class ItemService extends AbstractServiceImpl<Integer, ItemModel, Item, ItemsRepositoryService, ItemTransformer> {

	private static final String ITEM_PAGE_SIZE = "item_pagesize";

	@Autowired
	private ItemsRepositoryService itemsRepositoryService;

	@Autowired
	private ItemTransformer itemTransformer;

	@Autowired
	private ConfigurationService configurationService;

	@Override
	protected ItemsRepositoryService repoService() {
		return itemsRepositoryService;
	}

	@PostConstruct
	public void init() {
		setPageSizeToSystem();
	}

	@Override
	public ItemTransformer transformer() {
		return itemTransformer;
	}

	public PageImpl<Item> getAll(Integer page, Integer size) {
		if (page == null || page < 0) {
			page = 0;
		}
		Integer fromSystem = Integer.parseInt(System.getProperty(ITEM_PAGE_SIZE));
		if (size == null || (size < 0 || size > fromSystem)) {
			size = fromSystem;
		}
		return repoService().findAll(new PageRequest(page, size));
	}

	public Integer getPageSize() {
		ConfigurationModel pageSize = configurationService.getByName(ITEM_PAGE_SIZE);
		Integer size = 10;
		if (pageSize != null) {
			size = pageSize.getValue();
		}
		return size;
	}

	public void setPageSize(Integer size) {
		ConfigurationModel pageSize = configurationService.getByName(ITEM_PAGE_SIZE);
		if (pageSize == null) {
			pageSize = new ConfigurationModel();
			pageSize.setName(ITEM_PAGE_SIZE);
		}
		if (size == null || size <= 0) {
			size = 10;
		}
		pageSize.setValue(size);
		configurationService.save(pageSize);
		setPageSizeToSystem(pageSize);
	}

	public void setPageSizeToSystem(ConfigurationModel pageSize) {
		try {
			System.setProperty(ITEM_PAGE_SIZE, Integer.toString(pageSize.getValue()));
		} catch (Exception e) {
			System.setProperty(ITEM_PAGE_SIZE, "10");
			ConfigurationModel newPageSize = new ConfigurationModel();
			newPageSize.setName(ITEM_PAGE_SIZE);
			newPageSize.setValue(10);
			configurationService.save(newPageSize);
		}
	}

	public void setPageSizeToSystem() {
		ConfigurationModel pageSize = configurationService.getByName(ITEM_PAGE_SIZE);
		setPageSizeToSystem(pageSize);
	}

	public ItemModel sell(ItemModel model) {
		model.setSold(true);
		model.setLendDate(new Date().getTime());
		return super.save(model);
	}

	public ItemModel getBack(ItemModel model) {
		model.setLendTo(0);
		return super.save(model);
	}

	public ItemModel lend(ItemModel model) {
		model.setSold(false);
		model.setLendDate(new Date().getTime());
		return super.save(model);
	}

	public List<ItemModel> generateReport(final ReportRequestModel model) {
		return transformer().transformTo(itemsRepositoryService.findAll(new Specification<Item>() {

			@Override
			public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();

				if (StringUtils.isNotBlank(model.getItemName())) {
					predicates.add(cb.like(root.get(Item_.name), "%" + model.getItemName().toUpperCase() + "%"));
				}

				if (StringUtils.isNotBlank(model.getItemType())) {
					predicates.add(cb.equal(root.get(Item_.itemType), model.getItemType()));
				}

				if (model.getCategory() > 0) {
					Category category = new Category();
					category.setId(model.getCategory());
					predicates.add(cb.equal(root.get(Item_.category), category));
				}

				if (model.getStockType() > 0) {
					StockType stockType = new StockType();
					stockType.setId(model.getStockType());
					predicates.add(cb.equal(root.get(Item_.stockType), stockType));
				}

				if (model.getSupplier() > 0) {
					Supplier supplier = new Supplier();
					supplier.setId(model.getSupplier());
					predicates.add(cb.equal(root.get(Item_.supplier), supplier));
				}

				if (model.getLent() > 0) {
					Supplier lent = new Supplier();
					lent.setId(model.getLent());
					predicates.add(cb.equal(root.get(Item_.lendTo), lent));
				}

				if (null != model.getPurchaseStartDate() && null != model.getPurchaseEndDate()) {
					predicates.add(cb.greaterThanOrEqualTo(root.get(Item_.createDate), getBeginTimeStampForDate(model.getPurchaseStartDate())));
					predicates.add(cb.lessThanOrEqualTo(root.get(Item_.createDate), getEndTimeStampForDate(model.getPurchaseEndDate())));
				}

				if (null != model.getSellStartDate() && null != model.getSellEndDate()) {
					predicates.add(cb.greaterThanOrEqualTo(root.get(Item_.lendDate), getBeginTimeStampForDate(model.getSellStartDate())));
					predicates.add(cb.lessThanOrEqualTo(root.get(Item_.lendDate), getEndTimeStampForDate(model.getSellEndDate())));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		}));
	}

	protected Long getBeginTimeStampForDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	protected Long getEndTimeStampForDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
}
