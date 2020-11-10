package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.SaveImage;

public interface SaveImageRepository extends CrudRepository<SaveImage, Integer> {
	List<SaveImage> findByUserId(String userId);
}
