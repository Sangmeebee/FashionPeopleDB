package kr.ac.hansung.cse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<SaveImage>> getSaveImages(@PathVariable("userId") String userId) {
		Optional<FUser> userData = fUserRepository.findById(userId);
		FUser user = userData.get();
		List<SaveImage> images = user.getSaveImages();
		if (images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
	}
    
	@PutMapping("/{userId}/{imageName}")
	public ResponseEntity<FUser> updateSaveImage(@PathVariable("userId") String userId, @PathVariable("imageName") String imageName) {
		Optional<FUser> fUserData = fUserRepository.findById(userId);
		FUser user = fUserData.get();
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		FeedImage image = feedImageData.get();
		SaveImage saveImage = new SaveImage(image);
		List<SaveImage> saveImages = user.getSaveImages();
		saveImages.add(saveImage);
		user.setSaveImages(saveImages);
		return new ResponseEntity<>(fUserRepository.save(user), HttpStatus.OK);
	}
}