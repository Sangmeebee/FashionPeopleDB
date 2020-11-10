package kr.ac.hansung.cse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.Follower;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FollowerRepository;

@RestController
@RequestMapping("/follower")
public class FollowerController {

	@Autowired
	FUserRepository fUserRepository;
	@Autowired
	FollowerRepository followerRepository;

	@PutMapping("/{userId}")
	public ResponseEntity<FUser> updateFollower(@PathVariable("userId") String userId,
			@RequestBody Follower follower) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		if (fUserData.isPresent()) {
			FUser user = fUserData.get();
			Follower _follower = new Follower(follower.getFollowerPersonId());
			List<Follower> followers = user.getFollowers();
			followers.add(_follower);
			user.setFollowers(followers);

			return new ResponseEntity<>(fUserRepository.save(user), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{userId}")
		public ResponseEntity<List<Follower>> getFollower(@PathVariable("userId") String userId) {
			List<Follower> followerData = followerRepository.findByUserId(userId);
		
			if (followerData.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(followerData, HttpStatus.OK);
		}
}