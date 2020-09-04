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
import kr.ac.hansung.cse.model.RankImage;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.RankImageRepository;

@RestController
@RequestMapping("/rankImage")
public class RankImageController {

	@Autowired
	FUserRepository fUserrepository;
	
	@Autowired
	RankImageRepository rankImageRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<List<RankImage>> getAllImages(@PathVariable("id") String id) {
		List<RankImage> images = new ArrayList<>();
		try {
			rankImageRepository.findAll().forEach(images::add);
			List<RankImage> _images = new ArrayList<RankImage>();
			for(RankImage image : images) {
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
	public ResponseEntity<FUser> updateFeedImage(@PathVariable("id") String id, @RequestBody RankImage image) {
		Optional<FUser> userData = fUserrepository.findById(id);
		
		if (userData.isPresent()) {
			FUser _user = userData.get();
			_user.getRankImages().add(image);
			return new ResponseEntity<>(fUserrepository.save(_user), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
}
