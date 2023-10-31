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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static springfox.petstore.Responses.ok;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import springfox.petstore.model.Order;
import springfox.petstore.model.Pet;
import springfox.petstore.repository.MapBackedRepository;

/**
 * Operations about store
 */
@Controller
@RequestMapping(value = "/api/store", produces = APPLICATION_JSON_VALUE)
public class PetStoreResource {
	private static StoreData storeData = new StoreData();

	private static class StoreData extends MapBackedRepository<Long, Order> {
	}

	/**
	 * Find purchase order by ID
	 * 
	 * @param orderId ID of pet that needs to be fetched,range[1,5]
	 * @return
	 * @throws NotFoundException
	 */
	@RequestMapping(value = "/order/{orderId}", method = GET)
	public ResponseEntity<Order> getOrderById(@PathVariable(name = "orderId", required = true) String orderId)
			throws NotFoundException {
		Order order = storeData.get(Long.valueOf(orderId));
		if (null != order) {
			return ok(order);
		} else {
			throw new NotFoundException(404, "Order not found");
		}
	}

	/**
	 * Place an order for a pet
	 * 
	 * @param order order placed for purchasing the pet
	 * @return
	 */
	@RequestMapping(value = "/order", method = POST)
	public ResponseEntity<String> placeOrder(Order order) {
		storeData.add(order);
		return ok("");
	}

	/**
	 * Delete purchase order by ID
	 * 
	 * @param orderId ID of the order that needs to be deleted,For valid response
	 *                try integer IDs with value < 1000. Anything above 1000 or
	 *                non-integers will generate API errors
	 * @return
	 */
	@RequestMapping(value = "/order/{orderId}", method = DELETE)
	public ResponseEntity<String> deleteOrder(@PathVariable("orderId") String orderId) {
		storeData.delete(Long.valueOf(orderId));
		return ok("");
	}

	/**
	 * 1234567
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET, produces = "application/json", params = "x=TX")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Pet> getPetInTx() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 12345678
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET, produces = "application/json", params = "x=CA")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Pet> getPetInCA() {
		throw new UnsupportedOperationException();
	}
}
