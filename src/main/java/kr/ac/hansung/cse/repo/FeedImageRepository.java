package kr.ac.hansung.cse.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;

public interface FeedImageRepository extends CrudRepository<FeedImage, String> {
	List<FeedImage> findByUser(FUser user);
	Optional<List<FeedImage>> findByStyle(String style);
	Optional<List<FeedImage>> findByTop(String top);
	Optional<List<FeedImage>> findByPants(String pants);
	Optional<List<FeedImage>> findByShoes(String shoes);

}