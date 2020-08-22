package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kr.ac.hansung.cse.model.FeedImageComment;

public interface FeedImageCommentRepository extends CrudRepository<FeedImageComment, Integer> {
	
	List<FeedImageComment> findByUserId(String id);
}