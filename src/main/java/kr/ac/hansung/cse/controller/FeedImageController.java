package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageEvaluation;
import kr.ac.hansung.cse.model.Following;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;
import kr.ac.hansung.cse.repo.FollowingRepository;

@RestController
@RequestMapping("/feedImage")
public class FeedImageController {
	@Autowired
	FUserRepository fUserrepository;

	@Autowired
	FeedImageRepository feedImageRepository;

	@Autowired
	FollowingRepository followingRepository;

	@GetMapping
	public ResponseEntity<List<FeedImage>> getAllImages() {
		List<FeedImage> images = new ArrayList<>();
		try {
			feedImageRepository.findAll().forEach(images::add);
			return new ResponseEntity<>(images, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<FeedImage>> getUserImages(@PathVariable("id") String id) {
		Optional<FUser> userData = fUserrepository.findById(id);
		FUser user = userData.get();
		List<FeedImage> images = feedImageRepository.findByUser(user);
		if (images.get(images.size() - 1).isEvaluateNow() == true) {
			images.remove(images.size() - 1);
		}

		return new ResponseEntity<>(images, HttpStatus.OK);
	}

	@GetMapping("/imageName/{imageName}")
	public ResponseEntity<FeedImage> getFeedImageByName(@PathVariable("imageName") String imageName) {
		Optional<FeedImage> feedImageData = feedImageRepository.findById(imageName);
		if (feedImageData.isPresent()) {
			FeedImage feedImage = feedImageData.get();
			return new ResponseEntity<>(feedImage, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("/evaluated/{userId}")
	public ResponseEntity<FeedImage> getEvaluatedFeedImage(@PathVariable("userId") String userId) {
		Optional<FUser> userData = fUserrepository.findById(userId);
		FUser user = userData.get();
		List<FeedImage> feedImageData = feedImageRepository.findByUser(user);
		FeedImage feedImage = feedImageData.get(feedImageData.size()-1);
		return new ResponseEntity<>(feedImage, HttpStatus.OK);

	}

	@GetMapping("/other/{id}")
	public ResponseEntity<List<FeedImage>> getOtherImages(@PathVariable("id") String id) {
		List<FeedImage> images = new ArrayList<>();
		try {
			feedImageRepository.findAll().forEach(images::add);
			List<FeedImage> _images = new ArrayList<>();
			for (FeedImage image : images) {
				boolean isAlreadyEvaluation = false;
				List<FeedImageEvaluation> evaluations = image.getEvaluations();
				if (!evaluations.isEmpty()) {
					for (FeedImageEvaluation evaluation : image.getEvaluations()) {
						if (evaluation.getEvaluationPersonId().equals(id)) {
							isAlreadyEvaluation = true;
						}
					}
				}
				if (image.isEvaluateNow() && !image.getUser().getId().equals(id) && !isAlreadyEvaluation) {
					_images.add(image);
				}
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
		FeedImage mImage = new FeedImage(image.getImageName(), image.getStyle(), image.getTop(), image.getPants(),
				image.getShoes(), image.getRank(), image.isEvaluateNow(), image.getResultRating(), user);
		try {
			user.setEvaluateNow(true);
			fUserrepository.save(user);
			FeedImage _image = feedImageRepository.save(mImage);
			return new ResponseEntity<>(_image, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/following/{userId}")
	public ResponseEntity<List<FeedImage>> getFollowingFeedImages(@PathVariable("userId") String userId) {
		Optional<FUser> fuserOptional = fUserrepository.findById(userId);
		System.out.println(fuserOptional.isPresent());
		if (fuserOptional.isPresent()) {
			FUser fuser = fuserOptional.get();
			List<Following> followingList = fuser.getFollowings();

			List<FeedImage> feedImages = new LinkedList<>();

			for (int i = 0; i < followingList.size(); i++) {
				List<FeedImage> images = feedImageRepository.findByUser(followingList.get(i).getFollowing());
				feedImages.addAll(images);
			}
			feedImages.sort(new Comparator<FeedImage>() {

				@Override
				public int compare(FeedImage o1, FeedImage o2) {
					return o2.getTimeStamp().compareTo(o1.getTimeStamp());
				}

			});
			return new ResponseEntity<List<FeedImage>>(feedImages, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}
}
