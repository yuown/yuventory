package yuown.yunventory.holders;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum StockType {

    StockIn("Stock In", "in"),
    DemoBack("Demo Back", "in"),
    StockOut("Stock Out", "out"),
    Demo("Demo", "out");

    private String name;
    private String type;

    private static Map<String, StockType> map = new HashMap<String, StockType>();

    static {
        for (StockType stockType : EnumSet.allOf(StockType.class)) {
            map.put(stockType.name, stockType);
        }
    }

    StockType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}