package kr.ac.hansung.cse.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;

public interface FeedImageRepository extends CrudRepository<FeedImage, String> {
	List<FeedImage> findByUser(FUser user);
	Optional<FeedImage> findByImageName(String imageName);
}