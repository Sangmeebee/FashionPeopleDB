package kr.ac.hansung.cse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import kr.ac.hansung.cse.repo.FeedImageRepository;

@RestController
@RequestMapping("/feedImage/evaluation")
public class FeedImageEvaluationController {

	@Autowired
	FeedImageRepository feedImageRepository;
	
	@PutMapping("/{imageName}")
	public ResponseEntity<FeedImage> updateImageScore(@PathVariable("imageName") String imageName, @RequestBody FeedImageEvaluation evaluation) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		if (feedImageData.isPresent()) {
			FeedImage image = feedImageData.get();
			FeedImageEvaluation _evaluation = new FeedImageEvaluation(evaluation.getEvaluationPersonId(), evaluation.getScore());
			List<FeedImageEvaluation> evaluations = image.getEvaluations();
			evaluations.add(_evaluation);
			image.setEvaluations(evaluations);
			
			return new ResponseEntity<>(feedImageRepository.save(image), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
