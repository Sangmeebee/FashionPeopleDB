package kr.ac.hansung.cse.repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.ManRankImage;
import kr.ac.hansung.cse.model.WomanRankImage;

public interface WomanRankImageRepository extends CrudRepository<WomanRankImage, Integer> {
	List<WomanRankImage> findByRankTimeStamp(String rankTimeStamp);
}
