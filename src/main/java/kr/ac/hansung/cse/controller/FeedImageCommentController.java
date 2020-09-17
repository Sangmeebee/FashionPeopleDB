package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	FeedImageCommentRepository feedImageCommentRepository;
	@Autowired
	FeedImageRepository feedImageRepository;	
	
	

	@GetMapping
	public ResponseEntity<List<FeedImageComment>> getAllFeedImageComments() {
		List<FeedImageComment> feedImageComments = new ArrayList<>();
		try {
			feedImageCommentRepository.findAll().forEach(feedImageComments::add);

			if (feedImageComments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(feedImageComments, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<List<FeedImageComment>> findByUserId(@PathVariable("id") String id) {
		try {
			List<FeedImageComment> feedImageComments = feedImageCommentRepository.findByUserId(id);

			if (feedImageComments.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(feedImageComments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	/**
	@PutMapping("/{id}/{imageName}")
	public ResponseEntity<FeedImage> updateFeedImageComment(@PathVariable("id") String id, @PathVariable("imageName") String imageName, @RequestBody FeedImageComment comment) {
		List<FeedImage> feedImageData = feedImageRepository.findByImageName(imageName);
		if(!feedImageData.isEmpty()) {
			FeedImage image= new FeedImage();
			FeedImageComment _comment = new FeedImageComment(comment.getComment(), comment.getTimeStamp());
			for(FeedImage _image: feedImageData) {
				if(_image.getUserId().equals(id)) {
					image = _image;
					image.getComments().add(_comment);
				}
			}
			return new ResponseEntity<>(feedImageRepository.save(image), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	**/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteFeedImageComment(@PathVariable("id") int id) {
		try {
			feedImageCommentRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllFeedImageComments() {
		try {
			feedImageCommentRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
}