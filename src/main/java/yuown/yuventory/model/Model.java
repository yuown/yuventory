package yuown.yuventory.model;

import java.io.Serializable;
import java.util.Date;

public class Model implements Serializable {

    protected Integer id;

    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
