package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import kr.ac.hansung.cse.model.FeedImageLike;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;

@RestController
@RequestMapping("/feedImage")
public class FeedImageController {
	@Autowired
	FUserRepository fUserrepository;
	@Autowired
	FeedImageRepository feedImageRepository;

	@GetMapping("/{id}")
	public ResponseEntity<List<FeedImage>> getAllImages(@PathVariable("id") String id) {
		List<FeedImage> images = new ArrayList<>();
		try {
			feedImageRepository.findAll().forEach(images::add);
			List<FeedImage> _images = new ArrayList<FeedImage>();
			for(FeedImage image : images) {
				if(image.getUserId().equals(id)) {
					_images.add(image);
				}
			}

			if (_images.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(_images, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<FUser> updateFeedImage(@PathVariable("id") String id, @RequestBody FeedImage image) {
		Optional<FUser> userData = fUserrepository.findById(id);
		
		if (userData.isPresent()) {
			FUser _user = userData.get();
			List<FeedImage> images = _user.getImages();
			boolean isChange = false;
			for(FeedImage _image : images) {
				if(_image.getImageName().equals(image.getImageName())) {
					_image.setImageName(image.getImageName());
					_image.setTimeStamp(image.getTimeStamp());
					_image.setStyle(image.getStyle());
					_image.setTop(image.getTop());
					_image.setPants(image.getPants());
					_image.setShoes(image.getShoes());
					_image.setRank(image.getRank());
					_image.setBattleNow(image.isBattleNow());
					feedImageRepository.save(_image);
					isChange = true;
				}
			}
			if(!isChange) {
				FeedImage mImage = new FeedImage(image.getImageName(), image.getTimeStamp(), image.getStyle(), image.getTop(), image.getPants(), image.getShoes(), image.getRank(), image.isBattleNow());
				_user.getImages().add(mImage);
			}
			
			return new ResponseEntity<>(fUserrepository.save(_user), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
