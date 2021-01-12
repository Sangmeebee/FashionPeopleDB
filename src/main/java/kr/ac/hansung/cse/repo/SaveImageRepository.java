package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.SaveImage;

public interface SaveImageRepository extends CrudRepository<SaveImage, Integer> {
	List<SaveImage> findByUser(FUser user);
	void deleteByImage(FeedImage image);
}
