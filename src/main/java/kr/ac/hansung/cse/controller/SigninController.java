package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.repo.FUserRepository;

@RestController
@RequestMapping("/users")
public class SigninController {
	
	@Autowired
	FUserRepository fUserrepository;

	@GetMapping
	public ResponseEntity<List<FUser>> getAllUsers() {
		List<FUser> users = new ArrayList<>();
		try {
			fUserrepository.findAll().forEach(users::add);

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<FUser> getUserById(@PathVariable("id") String id) {
		Optional<FUser> customerData = fUserrepository.findById(id);

		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
		try {
			fUserrepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<FUser> updateUser(@PathVariable("id") String id, @RequestBody FUser user) {
		Optional<FUser> userData = fUserrepository.findById(id);

		if (userData.isPresent()) {
			FUser _user = userData.get();
			_user.setId(user.getId());
			_user.setName(user.getName());
			_user.setInstagramId(user.getInstagramId());
			_user.setProfileImage(user.getProfileImage());
			return new ResponseEntity<>(fUserrepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<FUser> postUser(@RequestBody FUser user) {
		try {
			FUser _user = fUserrepository.save(new FUser(user.getId(), user.getName(), user.getInstagramId(), user.getProfileImage(), user.isEvaluateNow()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}


}