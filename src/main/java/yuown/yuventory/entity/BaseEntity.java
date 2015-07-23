package yuown.yuventory.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> {

    public static final String yyyy_MM_dd_HH_mm_ss_S = "yyyy-MM-dd HH:mm:ss.S";

    protected ID id;

    public abstract ID getId();

    public abstract void setId(ID id);

}
