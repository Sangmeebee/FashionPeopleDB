package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.RankImage;

public interface RankImageRepository extends CrudRepository<RankImage, Integer> {

	List<RankImage> findByImageName(String imageName);

}
