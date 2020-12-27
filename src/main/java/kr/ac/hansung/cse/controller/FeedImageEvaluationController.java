package kr.ac.hansung.cse.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageEvaluation;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageEvaluationRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;

@RestController
@RequestMapping("/feedImage/evaluation")
public class FeedImageEvaluationController {

	@Autowired
	FUserRepository fUserRepository;
	@Autowired
	FeedImageRepository feedImageRepository;
	@Autowired
	FeedImageEvaluationRepository feedImageEvaluationRepository;

	@PutMapping("/{imageName}")
	public ResponseEntity<FeedImageEvaluation> updateImageEvaluation(@PathVariable("imageName") String imageName,
			@RequestBody FeedImageEvaluation evaluation) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		FeedImage image = feedImageData.get();
		if (image.getEvaluations().size() == 3) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		} else {
			FeedImageEvaluation _evaluation = new FeedImageEvaluation(evaluation.getEvaluationPersonId(),
					evaluation.getScore(), image);
			List<FeedImageEvaluation> evaluations = image.getEvaluations();
			evaluations.add(_evaluation);
			if (evaluations.size() == 3) {
				Optional<FUser> fUserData = fUserRepository.findById(image.getUser().getId());
				FUser user = fUserData.get();
				user.setEvaluateNow(false);
				fUserRepository.save(user);
				image.setEvaluateNow(false);
				image.setResultTimeStamp(LocalDateTime.now());
				float sum = 0;
				for (FeedImageEvaluation evalu : image.getEvaluations()) {
					sum += evalu.getScore();
				}
				image.setResultRating(sum / image.getEvaluations().size());
			}
			feedImageEvaluationRepository.save(_evaluation);
			return new ResponseEntity<>(_evaluation, HttpStatus.CREATED);
		}
	}

	@GetMapping("/{imageName}")
	public ResponseEntity<List<FeedImageEvaluation>> getImageEvaluation(@PathVariable("imageName") String imageName) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		FeedImage image = feedImageData.get();
		List<FeedImageEvaluation> evaluationData = feedImageEvaluationRepository.findByImage(image);

		return new ResponseEntity<>(evaluationData, HttpStatus.OK);
	}

}
