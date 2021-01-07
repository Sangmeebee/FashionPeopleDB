package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;

import kr.ac.hansung.cse.model.SaveImage;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;
import kr.ac.hansung.cse.repo.SaveImageRepository;

@RestController
@RequestMapping("/saveImage")
public class SaveImageController {
	@Autowired
	FUserRepository fUserRepository;
	@Autowired
	FeedImageRepository feedImageRepository;
	@Autowired
	SaveImageRepository saveImageRepository;

	@GetMapping("/{userId}")
	public ResponseEntity<List<FeedImage>> getSaveImages(@PathVariable("userId") String userId) {
		Optional<FUser> userData = fUserRepository.findById(userId);
		FUser user = userData.get();
		List<SaveImage> images = saveImageRepository.findByUser(user);
		images.sort(new Comparator<SaveImage>() {
			@Override
			public int compare(SaveImage o1, SaveImage o2) {
				return o1.getTimeStamp().compareTo(o2.getTimeStamp());
			}
		});

		List<FeedImage> feedImages = new ArrayList<FeedImage>();
		for (SaveImage image : images) {
			feedImages.add(image.getImage());
		}
		return new ResponseEntity<>(feedImages, HttpStatus.OK);
	}

	@PostMapping("/{userId}/{imageName}")
	public ResponseEntity<SaveImage> postSaveImage(@PathVariable("userId") String userId,
			@PathVariable("imageName") String imageName) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		FeedImage image = feedImageData.get();
		SaveImage saveImage = new SaveImage(user, image);
		return new ResponseEntity<>(saveImageRepository.save(saveImage), HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}/{imageName}")
	public ResponseEntity<HttpStatus> deleteSaveImage(@PathVariable("userId") String userId,
			@PathVariable("imageName") String imageName) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		List<SaveImage> saveImages = saveImageRepository.findByUser(user);
		for(SaveImage saveImage : saveImages) {
			if(saveImage.getImage().getImageName().equals(imageName)) {
				saveImageRepository.delete(saveImage);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				
			}
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}