package yuown.yuventory.rest.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import yuown.yuventory.business.services.UserService;
import yuown.yuventory.model.UserModel;

@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserResourceImpl {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserModel save(@RequestBody UserModel model) {
		return userService.save(model);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public UserModel getById(@PathVariable("id") int id) {
		return userService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<String> removeById(@PathVariable("id") int id) {
		UserModel user = userService.getById(id);
		if (null == user) {
			return new ResponseEntity<String>("User with ID " + id + " Not Found", HttpStatus.NOT_FOUND);
		} else {
			try {
				userService.removeById(id);
				return new ResponseEntity<String>("User with ID " + id + " Deleted Successfully", HttpStatus.OK);
			} catch (Exception e) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("errorMessage", "User with ID " + id + " cannot be Deleted");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<UserModel> getAll() {
		return userService.getAll();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public UserModel login(@RequestBody UserModel user) {
		user.setPassword(null);
		return user;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/enable")
	@ResponseBody
	public ResponseEntity<String> enable(@RequestBody UserModel model) {
		UserModel user = userService.getById(model.getId());
		if (null == user) {
			return new ResponseEntity<String>("Inavlid User, Not Found in the System", HttpStatus.NOT_FOUND);
		} else {
			try {
				userService.enable(model);
				return new ResponseEntity<String>("User with ID " + user.getId() + " Updated Successfully", HttpStatus.OK);
			} catch (Exception e) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("errorMessage", "User with ID " + user.getId() + " cannot be Updated");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
}
