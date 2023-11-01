/*
 *
 *  Copyright 2017 the original author or authors.
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
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springfox.petstore.Responses;
import springfox.petstore.model.Pet;
import springfox.petstore.model.Pets;
import springfox.petstore.repository.MapBackedRepository;

/**
 * Operations about pets
 */
@Controller
@RequestMapping(value = "/api/pet", produces = { APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE })
public class PetController {

	private PetRepository petData = new PetRepository();

	/**
	 * Find pet by ID
	 * 
	 * @param petId ID of pet that needs to be fetched,range[1,5]
	 * @return Returns a pet when ID < 10. ID > 10 or non-integers will simulate API
	 *         error conditions
	 */
	@RequestMapping(value = "/{petId}", method = GET)
	public ResponseEntity<Pet> getPetById(@PathVariable("petId") String petId) throws NotFoundException {
		Pet pet = petData.get(Long.valueOf(petId));
		if (null != pet) {
			return Responses.ok(pet);
		} else {
			throw new NotFoundException(404, "Pet not found");
		}
	}

	/**
	 * Add a new pet to the store
	 * 
	 * @param pet Pet object that needs to be added to the store
	 * @return
	 */
	@RequestMapping(method = POST)
	public ResponseEntity<String> addPet(@RequestBody Pet pet) {
		petData.add(pet);
		return Responses.ok("SUCCESS");
	}

	/**
	 * Update an existing pet
	 * 
	 * @param pet Pet object that needs to be added to the store
	 * @return
	 */
	@RequestMapping(method = PUT)
	public ResponseEntity<String> updatePet(@RequestBody Pet pet) {
		petData.add(pet);
		return Responses.ok("SUCCESS");
	}

	/**
	 * Finds Pets by status
	 * 
	 * @param status Status values that need to be considered for filter,Multiple
	 *               status values can be provided with comma-separated strings
	 * @return
	 */
	@RequestMapping(value = "/findByStatus", method = GET)
	public ResponseEntity<List<Pet>> findPetsByStatus(@RequestParam(name = "status", required = true) String status) {
		return Responses.ok(petData.findPetByStatus(status));
	}

	/**
	 * Finds Pets by tags
	 * 
	 * @param tags
	 * @return
	 */
	@RequestMapping(value = "/findByTags", method = GET)
	@Deprecated
	public ResponseEntity<List<Pet>> findPetsByTags(@RequestParam("tags") String tags) {
		return Responses.ok(petData.findPetByTags(tags));
	}

	/**
	 * Finds Pets (hidden)
	 * 
	 * @param tags Tags to filter by
	 * @return
	 */
	@RequestMapping(value = "/findPetsHidden", method = GET)
	public ResponseEntity<List<Pet>> findPetsHidden(@RequestParam(name = "tags", required = true) String tags) {
		return Responses.ok(petData.findPetByTags(tags));
	}

	static class PetRepository extends MapBackedRepository<Long, Pet> {
		public List<Pet> findPetByStatus(String status) {
			return where(Pets.statusIs(status));
		}

		public List<Pet> findPetByTags(String tags) {
			return where(Pets.tagsContain(tags));
		}
	}
}
