package yuown.yuventory.model;

public class StockTypeModel extends Model {

    private String name;
    
    public StockTypeModel() {
	}
    
    public StockTypeModel(String name) {
    	this.name = name;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
