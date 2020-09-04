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

import kr.ac.hansung.cse.model.RankImage;
import kr.ac.hansung.cse.model.RankImageLike;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.RankImageRepository;

@RestController
@RequestMapping("/rankImage/like")
public class RanImageLikeController {
	
	@Autowired
	FUserRepository fUserrepository;
	@Autowired
	RankImageRepository rankImageRepository;
	
	@PutMapping("/{imageId}")
	public ResponseEntity<RankImage> updateImageScore(@PathVariable("imageId") int imageId, @RequestBody RankImageLike like) {
		Optional<RankImage> rankImageData = rankImageRepository.findById(like.getImageId());
		if (rankImageData.isPresent()) {
			RankImage image = rankImageData.get();
			if(!image.getLikes().contains(like)) {
				image.getLikes().add(like);
			}
			return new ResponseEntity<>(rankImageRepository.save(image), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
