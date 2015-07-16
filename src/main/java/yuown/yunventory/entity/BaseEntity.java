package yuown.yunventory.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AttributeOverrides(value = 
    {
        @AttributeOverride(name = "createdTS", column = @Column(name = "CREATED_TS")),
        @AttributeOverride(name = "updatedTS", column = @Column(name = "UPDATED_TS")),
        @AttributeOverride(name = "createdBy", column = @Column(name = "CREATED_BY")),
        @AttributeOverride(name = "updatedBy", column = @Column(name = "UPDATED_BY")),
        @AttributeOverride(name = "enabled", column = @Column(name = "ENABLED"))
    })
public abstract class BaseEntity<ID extends Serializable> {

    public static final String yyyy_MM_dd_HH_mm_ss_S = "yyyy-MM-dd HH:mm:ss.S";

    protected ID id;
    private Date createdTS = new DateTime().toDate();
    private Timestamp updatedTS = new Timestamp(createdTS.getTime());
    private boolean enabled;
    private String createdBy;
    private String updatedBy;
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public abstract ID getId();

    public abstract void setId(ID id);

    public Date getCreatedTS() {
        return createdTS;
    }

    public void setCreatedTS(Date createdTS) {
        this.createdTS = createdTS;
    }

    public Timestamp getUpdatedTS() {
        return updatedTS;
    }

    public void setUpdatedTS(Timestamp updatedTS) {
        this.updatedTS = updatedTS;
    }

}
