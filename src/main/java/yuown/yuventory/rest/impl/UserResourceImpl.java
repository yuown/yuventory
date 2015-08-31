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

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.TEXT_PLAIN_VALUE })
	@ResponseBody
	public ResponseEntity<String> save(@RequestBody UserModel model) {
		UserModel user = userService.findByUsername(model.getUsername());
		HttpHeaders headers = new HttpHeaders();
		if (null != user) {
			userService.updateUser(model);
			return new ResponseEntity<String>("User with username " + model.getUsername() + " Updated Successfully", HttpStatus.OK);
		} else {
			try {
				userService.createUser(model);
				return new ResponseEntity<String>("User with username " + model.getUsername() + " Created Successfully", HttpStatus.OK);
			} catch (Exception e) {
				headers.add("errorMessage", "User with username " + model.getUsername() + " cannot be Created");
				return new ResponseEntity<String>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public UserModel getById(@PathVariable("id") int id) {
		return userService.getById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = { MediaType.TEXT_PLAIN_VALUE }, value = "/{id}")
	public ResponseEntity<String> removeById(@PathVariable("id") int id) {
		UserModel user = userService.getById(id);
		HttpHeaders headers = new HttpHeaders();
		if (null == user) {
			headers.add("errorMessage", "User with ID " + id + " Not Found");
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		} else {
			try {
				userService.removeUser(user);
				return new ResponseEntity<String>("User with ID " + id + " Deleted Successfully", HttpStatus.OK);
			} catch (Exception e) {
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

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.TEXT_PLAIN_VALUE }, value = "/enable")
	@ResponseBody
	public ResponseEntity<String> enable(@RequestBody UserModel model) {
		UserModel user = userService.getById(model.getId());
		if (null == user) {
			return new ResponseEntity<String>("Invalid User, Not Found in the System", HttpStatus.NOT_FOUND);
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

	@RequestMapping(method = RequestMethod.GET, value = "/groups/authorities", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<String> getAllAuthorities() {
		return userService.getAllAuthorities();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/groups")
	@ResponseBody
	public List<String> getGroups() {
		return userService.getGroups();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/groups")
	public void createGroup(@RequestBody String groupName) {
		userService.createGroup(groupName);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/groups/{groupName}")
	public void deleteGroup(@PathVariable("groupName") String groupName) {
		userService.deleteGroup(groupName);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, value = "/groups/auth/{groupName}")
	@ResponseBody
	public List<String> findGroupAuthorities(@PathVariable("groupName") String groupName) {
		return userService.findGroupAuthorities(groupName);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/groups/auth/{groupName}")
	public void addGroupAuthority(@PathVariable("groupName") String groupName, @RequestBody List<String> authorities) {
		userService.addOrRemoveGroupAuthority(groupName, authorities);
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE }, value = "/groups/user/{groupName}")
	public List<String> addUserToGroup(@PathVariable("groupName") String groupName) {
		return userService.findUsersIngroup(groupName);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/groups/user/{groupName}")
	public void addUserToGroup(@RequestBody String username, @PathVariable("groupName") String groupName) {
		userService.addUserToGroup(username, groupName);
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE }, value = "/groups/user/{groupName}")
	public void removeUserFromGroup(@RequestBody String username, @PathVariable("groupName") String groupName) {
		userService.removeUserFromGroup(username, groupName);
	}
}
