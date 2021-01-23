package kr.ac.hansung.cse.repo;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.ManRankImage;

public interface ManRankImageRepository extends CrudRepository<ManRankImage, Integer> {
	List<ManRankImage> findByRankTimeStamp(String rankTimeStamp);
}
