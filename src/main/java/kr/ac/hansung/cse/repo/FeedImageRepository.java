package kr.ac.hansung.cse.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FeedImage;

public interface FeedImageRepository extends CrudRepository<FeedImage, String> {
}