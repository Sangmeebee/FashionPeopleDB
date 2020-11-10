package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.Following;

public interface FollowingRepository extends CrudRepository<Following, Integer> {
	List<Following> findByUserId(String userId);

}
