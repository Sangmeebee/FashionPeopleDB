package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.Follower;
import kr.ac.hansung.cse.model.Following;

public interface FollowingRepository extends CrudRepository<Following, Integer> {
	List<Following> findByUser(FUser user);
	void deleteByFollowingId(String followingId);
}
