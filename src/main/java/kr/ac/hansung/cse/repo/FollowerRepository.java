package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
	List<Follower> findByUserId(String userId);
}
