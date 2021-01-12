package kr.ac.hansung.cse.repo;

import org.springframework.data.repository.CrudRepository;
import kr.ac.hansung.cse.model.Brand;
import kr.ac.hansung.cse.model.Style;


public interface StyleRepository extends CrudRepository<Style, String> {

}
