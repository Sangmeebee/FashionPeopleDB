package kr.ac.hansung.cse.repo;

import org.springframework.data.repository.CrudRepository;
import kr.ac.hansung.cse.model.Brand;


public interface BrandRepository extends CrudRepository<Brand, String> {

}
