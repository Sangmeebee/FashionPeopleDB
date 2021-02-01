package kr.ac.hansung.cse.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageComment;

public interface FeedImageCommentRepository extends CrudRepository<FeedImageComment, Integer> { 
	Optional<List<FeedImageComment>> findByImage(FeedImage image);
}
