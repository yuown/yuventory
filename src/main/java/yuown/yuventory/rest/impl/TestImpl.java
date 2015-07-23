package yuown.yuventory.rest.impl;

import yuown.yuventory.rest.intf.Test;

public class TestImpl implements Test {

	public String test(String name) {
		return "Hello " + name;
	}

}
