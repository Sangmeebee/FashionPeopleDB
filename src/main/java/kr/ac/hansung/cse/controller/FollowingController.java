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
	@Autowired
	FollowerRepository followerRepository;

	@PutMapping("/{userId}/{followingId}")
	public ResponseEntity<Following> updateFollowing(@PathVariable("userId") String userId,
			@PathVariable("followingId") String followingId) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		Optional<FUser> followingData = fUserRepository.findById(followingId);
		FUser following = followingData.get();
		FUser user = fUserData.get();
		// 나의 following에 following 추가
		Following _following = new Following(user, following);
		user.setFollowingNum(user.getFollowerNum()+1);
		// following의 follower에 user 추가
		Follower _follower = new Follower(following, user);
		following.setFollowerNum(following.getFollowerNum()+1);
		followerRepository.save(_follower);
		return new ResponseEntity<>(followingRepository.save(_following), HttpStatus.OK);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Following>> getFollowing(@PathVariable("userId") String userId) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		List<Following> followingData = followingRepository.findByUser(user);
		return new ResponseEntity<>(followingData, HttpStatus.OK);
	}
}