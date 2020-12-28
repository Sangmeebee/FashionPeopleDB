package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
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

	@GetMapping("/{userId}")
	public ResponseEntity<List<FUser>> getFollower(@PathVariable("userId") String userId) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		List<Follower> followerData = user.getFollowers();
		List<FUser> users = new ArrayList<FUser>();
		for(Follower follower : followerData) {
			users.add(fUserRepository.findById(follower.getFollowerId()).get());
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}