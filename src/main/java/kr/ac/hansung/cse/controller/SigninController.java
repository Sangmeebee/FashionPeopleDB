package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
import kr.ac.hansung.cse.model.FeedImageEvaluation;
import kr.ac.hansung.cse.model.Follower;
import kr.ac.hansung.cse.model.Following;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageEvaluationRepository;
import kr.ac.hansung.cse.repo.FollowerRepository;
import kr.ac.hansung.cse.repo.FollowingRepository;

@RestController
@RequestMapping("/users")
public class SigninController {
	
	@Autowired
	FUserRepository fUserrepository;
	@Autowired
	FollowingRepository followingRepository;
	@Autowired
	FollowerRepository followerRepository;
	@Autowired
	FeedImageEvaluationRepository feedImageEvaluationRepository;

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

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String id) {
		try {
			followingRepository.deleteByFollowingId(id);
			followerRepository.deleteByFollowerId(id);
			List<FeedImageEvaluation> evaluations = feedImageEvaluationRepository.findByEvaluationPersonId(id);
			for(FeedImageEvaluation evaluation : evaluations) {
				evaluation.setEvaluationPersonId("empty_user");
				feedImageEvaluationRepository.save(evaluation);
			}
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
			_user.setIntroduce(user.getIntroduce());
			_user.setGender(user.getGender());
			_user.setProfileImage(user.getProfileImage());
			return new ResponseEntity<>(fUserrepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<FUser> postUser(@RequestBody FUser user) {
		try {
			FUser _user = fUserrepository.save(new FUser(user.getId(), user.getName(), user.getIntroduce(), user.getGender(), user.getProfileImage(), user.isEvaluateNow()));
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}


}