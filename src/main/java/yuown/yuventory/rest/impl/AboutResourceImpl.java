package yuown.yuventory.rest.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/about", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AboutResourceImpl {

	@Value("${about.developer}")
	private String developer;

	@Value("#{'${about.contacts}'.split(',')}")
	private List<String> contacts;

	@Value("${about.licenceTo}")
	private String licenceTo;

	@Value("${about.version}")
	private String version;

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseEntity<HashMap<String, List<String>>> me() {
		HashMap<String, List<String>> about = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add(developer);
		about.put("developer", list);

		list = new ArrayList<String>();
		list.addAll(contacts);
		about.put("contacts", list);

		list = new ArrayList<String>();
		list.add(licenceTo);
		about.put("licenceTo", list);

		list = new ArrayList<String>();
		list.add(version);
		about.put("version", list);

		return new ResponseEntity<HashMap<String, List<String>>>(about, HttpStatus.OK);
	}
}
