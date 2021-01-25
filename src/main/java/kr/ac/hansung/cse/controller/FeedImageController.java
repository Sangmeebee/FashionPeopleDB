package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.Brand;
import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.FeedImageEvaluation;
import kr.ac.hansung.cse.model.Following;
import kr.ac.hansung.cse.model.Style;
import kr.ac.hansung.cse.repo.BrandRepository;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageEvaluationRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;
import kr.ac.hansung.cse.repo.FollowingRepository;
import kr.ac.hansung.cse.repo.SaveImageRepository;
import kr.ac.hansung.cse.repo.StyleRepository;

@RestController
@RequestMapping("/feedImage")
public class FeedImageController {
	@Autowired
	FUserRepository fUserrepository;

	@Autowired
	FeedImageRepository feedImageRepository;

	@Autowired
	SaveImageRepository saveImageRepository;

	@Autowired
	FollowingRepository followingRepository;

	@Autowired
	BrandRepository brandRepository;
	@Autowired
	StyleRepository styleRepository;

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
		Optional<List<FeedImage>> _images = feedImageRepository.findByUser(user);
		if (_images.isPresent()) {
			List<FeedImage> images = _images.get();
			images.sort(new Comparator<FeedImage>() {
				@Override
				public int compare(FeedImage o1, FeedImage o2) {
					return o2.getTimeStamp().compareTo(o1.getTimeStamp());
				}
			});
			return new ResponseEntity<>(images, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
			_images.sort(new Comparator<FeedImage>() {

				@Override
				public int compare(FeedImage o1, FeedImage o2) {
					return o1.getTimeStamp().compareTo(o2.getTimeStamp());
				}

			});
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
				FUser _user = fUserrepository.findById(followingList.get(i).getFollowingId()).get();
				List<FeedImage> images = new ArrayList<>();
				Optional<List<FeedImage>> _images = feedImageRepository.findByUser(_user);
				if (_images.isPresent()) {
					images = _images.get();
				}
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

	@GetMapping("/search/style/score/{query}")
	public ResponseEntity<List<FeedImage>> getSearchScoreStyleImages(@PathVariable("query") String query) {

		Optional<List<FeedImage>> feedImages = feedImageRepository.findByStyle(query);
		List<FeedImage> _feedImages = new ArrayList<>();
		if (feedImages.isPresent()) {
			_feedImages = feedImages.get();
		}
		_feedImages.sort(new Comparator<FeedImage>() {

			@Override
			public int compare(FeedImage o1, FeedImage o2) {
				Float f2 = (float) o2.getResultRating();
				Float f1 = (float) o1.getResultRating();
				return f2.compareTo(f1);
			}

		});

		return new ResponseEntity<List<FeedImage>>(_feedImages, HttpStatus.OK);
	}

	@GetMapping("/search/style/recent/{query}")
	public ResponseEntity<List<FeedImage>> getSearchRecentStyleImages(@PathVariable("query") String query) {

		Optional<List<FeedImage>> feedImages = feedImageRepository.findByStyle(query);
		List<FeedImage> _feedImages = new ArrayList<>();
		if (feedImages.isPresent()) {
			_feedImages = feedImages.get();
		}
		_feedImages.sort(new Comparator<FeedImage>() {

			@Override
			public int compare(FeedImage o1, FeedImage o2) {
				return o2.getTimeStamp().compareTo(o1.getTimeStamp());
			}

		});

		return new ResponseEntity<List<FeedImage>>(_feedImages, HttpStatus.OK);
	}

	@GetMapping("/search/brand/score/{query}")
	public ResponseEntity<List<FeedImage>> getSearchScoreBrandImages(@PathVariable("query") String query) {

		List<FeedImage> _feedImages = new ArrayList<>();
		List<FeedImage> feedImages = new ArrayList<>();
		feedImageRepository.findAll().forEach(_feedImages::add);
		;

		for (FeedImage feedImage : _feedImages) {
			Set<String> brandSet = new HashSet<String>();
			String top = feedImage.getTop();
			String pants = feedImage.getPants();
			String shoes = feedImage.getShoes();
			if (!top.isEmpty()) {
				brandSet.add(top);
			}
			if (!pants.isEmpty()) {
				brandSet.add(pants);
			}
			if (!shoes.isEmpty()) {
				brandSet.add(shoes);
			}

			for (String brand : brandSet) {
				if (query.equals(brand)) {
					feedImages.add(feedImage);
				}
			}
		}
		feedImages.sort(new Comparator<FeedImage>() {

			@Override
			public int compare(FeedImage o1, FeedImage o2) {
				Float f2 = (float) o2.getResultRating();
				Float f1 = (float) o1.getResultRating();
				return f2.compareTo(f1);
			}

		});

		return new ResponseEntity<List<FeedImage>>(feedImages, HttpStatus.OK);

	}

	@GetMapping("/search/brand/recent/{query}")
	public ResponseEntity<List<FeedImage>> getSearchRecentBrandImages(@PathVariable("query") String query) {

		List<FeedImage> _feedImages = new ArrayList<>();
		List<FeedImage> feedImages = new ArrayList<>();
		feedImageRepository.findAll().forEach(_feedImages::add);
		;

		for (FeedImage feedImage : _feedImages) {
			Set<String> brandSet = new HashSet<String>();
			String top = feedImage.getTop();
			String pants = feedImage.getPants();
			String shoes = feedImage.getShoes();
			if (!top.isEmpty()) {
				brandSet.add(top);
			}
			if (!pants.isEmpty()) {
				brandSet.add(pants);
			}
			if (!shoes.isEmpty()) {
				brandSet.add(shoes);
			}

			for (String brand : brandSet) {
				if (query.equals(brand)) {
					feedImages.add(feedImage);
				}
			}
		}
		feedImages.sort(new Comparator<FeedImage>() {

			@Override
			public int compare(FeedImage o1, FeedImage o2) {
				return o2.getTimeStamp().compareTo(o1.getTimeStamp());
			}

		});

		return new ResponseEntity<List<FeedImage>>(feedImages, HttpStatus.OK);

	}
	
	@Transactional
	@DeleteMapping("/{imageName}")
	public ResponseEntity<HttpStatus> deleteFeedImage(@PathVariable("imageName") String imageName) {
		try {

			Optional<FeedImage> _feedImage = feedImageRepository.findById(imageName);
			if (_feedImage.isPresent()) {
				FeedImage feedImage = _feedImage.get();
				saveImageRepository.deleteByImage(feedImage);
				Set<String> brandSet = new HashSet<String>();
				String style = feedImage.getStyle();
				String topBrand = feedImage.getTop();
				String pantsBrand = feedImage.getPants();
				String shoesBrand = feedImage.getShoes();
				if (!topBrand.isEmpty()) {
					brandSet.add(topBrand);
				}
				if (!pantsBrand.isEmpty()) {
					brandSet.add(pantsBrand);
				}
				if (!shoesBrand.isEmpty()) {
					brandSet.add(shoesBrand);
				}
				if (!style.isEmpty()) {
					Style st = styleRepository.findById(style).get();
					st.setPostNum(st.getPostNum() - 1);
					styleRepository.save(st);
				}
				if (!brandSet.isEmpty()) {
					for (String brand : brandSet) {
						Brand br = brandRepository.findById(brand).get();
						br.setPostNum(br.getPostNum() - 1);
						brandRepository.save(br);
					}
				}
				feedImageRepository.deleteById(imageName);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
