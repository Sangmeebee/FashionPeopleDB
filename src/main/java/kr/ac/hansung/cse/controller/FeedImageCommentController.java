package kr.ac.hansung.cse.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageComment;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageCommentRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;

@RestController
@RequestMapping("/feedImage/comment")
public class FeedImageCommentController {

	@Autowired
	FUserRepository fUserRepository;
	@Autowired
	FeedImageRepository feedImageRepository;
	@Autowired
	FeedImageCommentRepository feedImageCommentRepository;

	@PutMapping("/{userId}/{imageName}")
	public ResponseEntity<FeedImageComment> updateImageComment(@PathVariable("userId") String userId,
			@PathVariable("imageName") String imageName, @RequestBody FeedImageComment comment) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		FeedImage image = feedImageData.get();
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		LocalDateTime currentDateTime = LocalDateTime.now();
		FeedImageComment _comment = new FeedImageComment(comment.getContent(), currentDateTime, user, image);
		return new ResponseEntity<>(feedImageCommentRepository.save(_comment), HttpStatus.OK);

	}

	@GetMapping("/{imageName}")
	public ResponseEntity<List<FeedImageComment>> getImageComment(@PathVariable("imageName") String imageName) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		FeedImage image = feedImageData.get();
		List<FeedImageComment> commentData = feedImageCommentRepository.findByImage(image);

		if (commentData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(commentData, HttpStatus.OK);
	}

}
