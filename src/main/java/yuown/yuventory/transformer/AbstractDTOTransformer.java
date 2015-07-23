package yuown.yuventory.transformer;

import yuown.yuventory.entity.BaseEntity;
import yuown.yuventory.model.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDTOTransformer<From extends Model, To extends BaseEntity<?>> implements DTOTransformer<From, To> {

    public List<To> transformFrom(List<From> sources) {
        List<To> toList = new ArrayList<To>();
        if (sources != null && !sources.isEmpty()) {
            for (From source : sources) {
                To to = transformFrom(source);
                if (to != null) {
                    toList.add(to);
                }
            }

        }
        return toList;
    }

    public List<From> transformTo(List<To> sources) {
        List<From> fromList = new ArrayList<From>();
        if (sources != null && !sources.isEmpty()) {
            for (To source : sources) {
                From from = transformTo(source);
                if (from != null) {
                    fromList.add(from);
                }
            }
        }
        return fromList;
    }

    public abstract To transformFrom(From source);

    public abstract From transformTo(To source);

}
