package yuown.yunventory.business.services;

import org.springframework.transaction.annotation.Transactional;

import yuown.yunventory.entity.BaseEntity;
import yuown.yunventory.model.Model;
import yuown.yunventory.repository.BaseRepository;
import yuown.yunventory.repository.services.AbstractRepositoryService;
import yuown.yunventory.transformer.DTOTransformer;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractServiceImpl<ID extends Serializable, DTO extends Model, E extends BaseEntity<ID>, RS extends AbstractRepositoryService<? extends BaseRepository<E, ID>, E, ID>, TR extends DTOTransformer<DTO, E>> {

    public DTO saveOrUpdate(DTO resource) {
        return transformer().transformTo(repoService().saveOrUpdate(transformer().transformFrom(resource)));
    }

    public DTO getById(ID id) {
        return transformer().transformTo(repoService().findById(id));
    }

    public List<DTO> getByName(String name) {
        return transformer().transformTo(repoService().findByName(name));
    }

    public void removeById(ID id) {
        repoService().remove(repoService().findById(id));
    }
    
    public List<DTO> getAll() {
        return transformer().transformTo(repoService().findAllByDescOrder());
    }

    protected abstract RS repoService();

    protected abstract TR transformer();
}
