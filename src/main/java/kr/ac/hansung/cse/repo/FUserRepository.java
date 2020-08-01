package kr.ac.hansung.cse.repo;

import org.springframework.data.repository.CrudRepository;

import kr.ac.hansung.cse.model.FUser;

public interface FUserRepository extends CrudRepository<FUser, String> {
	void deleteById(String id);
}