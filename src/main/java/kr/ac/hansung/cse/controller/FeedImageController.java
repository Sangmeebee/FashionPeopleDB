package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
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

    @GetMapping("/")
    public ResponseEntity<List<FeedImage>> getAllImages() {
        List<FeedImage> images = new ArrayList<>();
        try {
            feedImageRepository.findAll().forEach(images::add);

            if (images.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(images, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<FeedImage>> getAllPersonImages(@PathVariable("id") String id) {
        List<FeedImage> images = new ArrayList<>();
        try {
            feedImageRepository.findAll().forEach(images::add);
            List<FeedImage> _images = new ArrayList<FeedImage>();
            for (FeedImage image : images) {
                if (image.getUser().getId().equals(id)) {
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

    @PostMapping("/{id}")
    public ResponseEntity<FeedImage> saveFeedImage(@PathVariable("id") String id, @RequestBody FeedImage image) {
    	Optional<FUser> userData = fUserrepository.findById(id);
    	FUser user = userData.get();
    	FeedImage mImage = new FeedImage(image.getImageName(), image.getTimeStamp(), image.getStyle(), image.getTop(), image.getPants(), image.getShoes(), image.getRank(), image.isBattleNow(), user);
    	try {
			FeedImage _image = feedImageRepository.save(mImage);
			return new ResponseEntity<>(_image, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
    }
}
