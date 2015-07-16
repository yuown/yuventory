package yuown.yunventory.rest.impl;

import yuown.yunventory.rest.intf.Test;

public class TestImpl implements Test {

	public String test(String name) {
		return "Hello " + name;
	}

}
