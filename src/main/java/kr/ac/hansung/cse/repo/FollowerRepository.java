package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
	List<Follower> findByUser(FUser user);
	void deleteByFollowerId(String followerId);
}
