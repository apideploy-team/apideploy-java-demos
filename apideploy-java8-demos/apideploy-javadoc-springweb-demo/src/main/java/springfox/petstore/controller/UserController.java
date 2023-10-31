/*
 *
 *  Copyright 2015 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.petstore.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.petstore.Responses;
import springfox.petstore.model.User;
import springfox.petstore.repository.UserRepository;

/**
 * Operations about user
 */
@Controller
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private UserRepository userRepository = new UserRepository();

	/**
	 * Create user
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method = POST)
	@ResponseBody
	public ResponseEntity<User> createUser(@RequestBody User user) {

		userRepository.add(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

//	/**
//	 * Creates list of users with given input array
//	 * 
//	 * @param users List of user object
//	 */
//	@RequestMapping(value = "/createWithArray", method = POST)
//	@ResponseBody
//	public ResponseEntity<User> createUsersWithArrayInput(User[] users) {
//		for (User user : users) {
//			userRepository.add(user);
//		}
//		return Responses.ok();
//	}

	/**
	 * Creates list of users with given input array
	 * 
	 * @param users List of user object
	 */
	@RequestMapping(value = "/createWithList", method = POST)
	@ResponseBody
	public ResponseEntity<String> createUsersWithListInput(@RequestBody List<User> users) {
		for (User user : users) {
			userRepository.add(user);
		}
		return Responses.ok();
	}

	/**
	 * Updated user
	 * 
	 * @param username name that need to be deleted
	 * @param user     Updated user object
	 */
	@RequestMapping(value = "/{username}", method = PUT)
	@ResponseBody
	public ResponseEntity<String> updateUser(@PathVariable("username") String username, User user) {
		if (userRepository.get(username) != null) {
			userRepository.add(user);
			return Responses.ok();
		}
		return Responses.notFound();
	}

	/**
	 * Delete user
	 * 
	 * @param username The name that needs to be deleted
	 */
	@RequestMapping(value = "/{username}", method = DELETE)
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
		if (userRepository.exists(username)) {
			userRepository.delete(username);
			return Responses.ok();
		}
		return Responses.notFound();

	}

	/**
	 * Get user by user name
	 * 
	 * @param username The name that needs to be fetched. Use user1 for testing.
	 * @return
	 */
	@RequestMapping(value = "/{username}", method = GET)
	public ResponseEntity<User> getUserByName(@PathVariable("username") String username) {
		User user = userRepository.get(username);
		if (null != user) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			throw new NotFoundException(404, "User not found");
		}
	}

	/**
	 * Logs user into the system
	 * 
	 * @param username The user name for login
	 * @param password The password for login in clear text
	 * @return
	 */
	@RequestMapping(value = "/login", method = GET)
	public ResponseEntity<String> loginUser(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		return new ResponseEntity<>("logged in user session:" + System.currentTimeMillis(), HttpStatus.OK);
	}

	/**
	 * Logs out current logged in user session
	 */
	@RequestMapping(value = "/logout", method = GET)
	public ResponseEntity<String> logoutUser() {
		return Responses.ok();
	}
}
