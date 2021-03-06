package kr.ac.hansung.cse.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageEvaluation;

public interface FeedImageEvaluationRepository extends CrudRepository<FeedImageEvaluation, Integer> {
	List<FeedImageEvaluation> findByImage(FeedImage image);
	Optional<List<FeedImageEvaluation>> findByEvaluationPersonId(String evaluationPersonId);
}