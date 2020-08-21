package kr.ac.hansung.cse.repo;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.ImageScore;

public interface ImageScoreRepository extends CrudRepository<ImageScore, String> {
}