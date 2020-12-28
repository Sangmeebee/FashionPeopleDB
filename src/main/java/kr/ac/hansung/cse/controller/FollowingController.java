package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

	
	@GetMapping("/{userId}")
	public ResponseEntity<List<FUser>> getFollowing(@PathVariable("userId") String userId) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		List<Following> followingData = user.getFollowings();
		List<FUser> users = new ArrayList<FUser>();
		for(Following following : followingData) {
			users.add(fUserRepository.findById(following.getFollowingId()).get());
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/{customId}")
	public ResponseEntity<Boolean> getIsFollowing(@PathVariable("userId") String userId, @PathVariable("customId") String customId) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		boolean isFollowing = false;
		List<Following> followingData = user.getFollowings();
		for(Following following : followingData) {
			if(following.getFollowingId().equals(customId)) {
				isFollowing = true;
			}
		}
		return new ResponseEntity<>(isFollowing, HttpStatus.OK);
	}
	
	@GetMapping("/isFollowingsFollowing/{userId}/{customId}")
	public ResponseEntity<Map<String, Boolean>> getIsFollowingsFollowing(@PathVariable("userId") String userId, @PathVariable("customId") String customId) {
		FUser user = fUserRepository.findById(userId).get();
		FUser custom = fUserRepository.findById(customId).get();
		Map<String, Boolean> map = new HashMap<>();
		List<Following> customFollowings = custom.getFollowings();
		for(Following cFollowing : customFollowings) {
			map.put(cFollowing.getFollowingId(), false);
		}
		List<Following> userFollowings = user.getFollowings();
		Set<String> keys = map.keySet();
		for(Following uFollowing : userFollowings) {
			for (String fid : keys) {
				if(fid.equals(uFollowing.getFollowingId())) {
					map.put(fid, true);
				}
			}
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/isFollowingsFollower/{userId}/{customId}")
	public ResponseEntity<Map<String, Boolean>> getIsFollowingsFollower(@PathVariable("userId") String userId, @PathVariable("customId") String customId) {
		FUser user = fUserRepository.findById(userId).get();
		FUser custom = fUserRepository.findById(customId).get();
		Map<String, Boolean> map = new HashMap<>();
		List<Follower> customFollowers = custom.getFollowers();
		for(Follower cFollower : customFollowers) {
			map.put(cFollower.getFollowerId(), false);
		}
		List<Following> userFollowings = user.getFollowings();
		Set<String> keys = map.keySet();
		for(Following uFollowing : userFollowings) {
			for (String fid : keys) {
				if(fid.equals(uFollowing.getFollowingId())) {
					map.put(fid, true);
				}
			}
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PutMapping("/{userId}/{followingId}")
	public ResponseEntity<HttpStatus> updateFollowing(@PathVariable("userId") String userId,
			@PathVariable("followingId") String followingId) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		Optional<FUser> followingData = fUserRepository.findById(followingId);
		FUser following = followingData.get();
		FUser user = fUserData.get();
		// 나의 following에 following 추가
		Following _following = new Following(user, followingId);
		// following의 follower에 user 추가
		List<Follower> followers = followerRepository.findByUser(user);
		List<Follower> ffollowers = followerRepository.findByUser(following);
		boolean isFollowing = false;
		if (followers != null) {
			for(Follower f : followers) {
				if(f.getFollowerId().equals(following.getId())) {
					for (Follower ff: ffollowers) {
						if(ff.getFollowerId().equals(user.getId())) {
							ff.setFollowing(true);
							followerRepository.save(ff);
						}
						break;
					}
					f.setFollowing(true);
					isFollowing = true;
					followerRepository.save(f);
					break;
				}
			}
		}
		Follower _follower = new Follower(following, userId, isFollowing);
		followerRepository.save(_follower);
		followingRepository.save(_following);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@DeleteMapping("/{userId}/{followingId}")
	public ResponseEntity<HttpStatus> deletFollowing(@PathVariable("userId") String userId,
			@PathVariable("followingId") String followingId) {
		try {
			Optional<FUser> fUserData = fUserRepository.findById(userId);
			Optional<FUser> followingData = fUserRepository.findById(followingId);
			FUser following = followingData.get();
			FUser user = fUserData.get();
			// 나의 following에 following 제거 
			List<Following> _followings = followingRepository.findByUser(user);
			for(Following _following : _followings) {
				if(_following.getFollowingId().equals(followingId)) {
					followingRepository.delete(_following);
					fUserRepository.save(user);
					break;
				}
			}

			// following의 follower에 user 제거	
			List<Follower> followers = followerRepository.findByUser(user);
			List<Follower> ffollowers = followerRepository.findByUser(following);
			for(Follower f : followers) {
				if(f.getFollowerId().equals(followingId)) {
					f.setFollowing(false);
					followerRepository.save(f);
					break;
				}
			}
			
			for (Follower ff : ffollowers) {
				if(ff.getFollowerId().equals(userId)) {
					followerRepository.delete(ff);
					
					fUserRepository.save(following);
					break;
				}
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}
}