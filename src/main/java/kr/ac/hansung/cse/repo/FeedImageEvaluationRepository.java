package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FeedImageEvaluation;

public interface FeedImageEvaluationRepository extends CrudRepository<FeedImageEvaluation, Integer> {
	List<FeedImageEvaluation> findByImageId(String imageId);
}