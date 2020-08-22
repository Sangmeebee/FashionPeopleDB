package kr.ac.hansung.cse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageLike;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;

@RestController
@RequestMapping("/feedImage/like")
public class FeedImageLikeController {
	@Autowired
	FUserRepository fUserrepository;
	@Autowired
	FeedImageRepository feedImageRepository;
	
	@PutMapping("/{id}/{imageName}")
	public ResponseEntity<FeedImage> updateImageScore(@PathVariable("id") String id, @PathVariable("imageName") String imageName, @RequestBody FeedImageLike like) {
		List<FeedImage> feedImageData = feedImageRepository.findByImageName(imageName);
		if (!feedImageData.isEmpty()) {
			FeedImage image = new FeedImage();
			FeedImageLike _like = new FeedImageLike(like.getLikePerson());
			for(FeedImage _image : feedImageData) {
				if(_image.getUserId().equals(id)) {
					image = _image;
					image.getLikes().add(_like);
				}
			}
			return new ResponseEntity<>(feedImageRepository.save(image), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
