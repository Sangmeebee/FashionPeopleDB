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

import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageComment;
import kr.ac.hansung.cse.repo.FeedImageCommentRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;

@RestController
@RequestMapping("/feedImage/comment")
public class FeedImageCommentController {

	@Autowired
	FeedImageRepository feedImageRepository;
	@Autowired
	FeedImageCommentRepository feedImageCommentRepository;
	
	@PutMapping("/{imageName}")
	public ResponseEntity<FeedImage> updateImageComment(@PathVariable("imageName") String imageName, @RequestBody FeedImageComment comment) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		if (feedImageData.isPresent()) {
			FeedImage image = feedImageData.get();
			LocalDateTime currentDateTime = LocalDateTime.now();
			FeedImageComment _comment = new FeedImageComment(comment.getCommentPersonId(), comment.getContent(), currentDateTime);
			List<FeedImageComment> comments = image.getComments();
			comments.add(_comment);
			image.setComments(comments);
			
			return new ResponseEntity<>(feedImageRepository.save(image), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{imageName}")
	public ResponseEntity<List<FeedImageComment>> getImageComment(@PathVariable("imageName") String imageName) {
		List<FeedImageComment> commentData = feedImageCommentRepository.findByImageId(imageName);
	
		if (commentData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentData, HttpStatus.OK);
	}

}
