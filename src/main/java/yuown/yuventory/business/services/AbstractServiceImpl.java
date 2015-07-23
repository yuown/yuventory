package yuown.yuventory.business.services;

import org.springframework.transaction.annotation.Transactional;

import yuown.yuventory.entity.BaseEntity;
import yuown.yuventory.jpa.repository.BaseRepository;
import yuown.yuventory.jpa.services.AbstractRepositoryService;
import yuown.yuventory.model.Model;
import yuown.yuventory.transformer.DTOTransformer;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractServiceImpl<ID extends Serializable, DTO extends Model, E extends BaseEntity<ID>, RS extends AbstractRepositoryService<? extends BaseRepository<E, ID>, E, ID>, TR extends DTOTransformer<DTO, E>> {

    public DTO save(DTO resource) {
        return transformer().transformTo(repoService().save(transformer().transformFrom(resource)));
    }

    public DTO getById(ID id) {
        return transformer().transformTo(repoService().findOne(id));
    }

    public void removeById(ID id) {
        repoService().delete(id);
    }

    public List<DTO> getAll() {
        return transformer().transformTo(repoService().findAll());
    }

    protected abstract RS repoService();

    protected abstract TR transformer();
}
