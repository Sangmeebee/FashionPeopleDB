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
import kr.ac.hansung.cse.model.Following;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FollowerRepository;
import kr.ac.hansung.cse.repo.FollowingRepository;

@RestController
@RequestMapping("/following")
public class FollowingController {

	@Autowired
	FUserRepository fUserRepository;
	@Autowired
	FollowingRepository followingRepository;

	@PutMapping("/{userId}")
	public ResponseEntity<FUser> updateFollowing(@PathVariable("userId") String userId,
			@RequestBody Following following) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		if (fUserData.isPresent()) {
			FUser user = fUserData.get();
			Following _following = new Following(following.getFollowingPersonId());
			List<Following> followings = user.getFollowings();
			followings.add(_following);
			user.setFollowings(followings);

			return new ResponseEntity<>(fUserRepository.save(user), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{userId}")
		public ResponseEntity<List<Following>> getFollowing(@PathVariable("userId") String userId) {
			List<Following> followingData = followingRepository.findByUserId(userId);
		
			if (followingData.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }
	        return new ResponseEntity<>(followingData, HttpStatus.OK);
		}
}