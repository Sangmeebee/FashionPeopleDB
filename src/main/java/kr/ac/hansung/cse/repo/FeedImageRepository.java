package kr.ac.hansung.cse.repo;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FeedImage;

public interface FeedImageRepository extends CrudRepository<FeedImage, Integer> {
	Optional<FeedImage> findByImageName(String imageName);
	void deleteByImageName(String imageName);
}