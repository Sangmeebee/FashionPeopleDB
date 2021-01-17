package kr.ac.hansung.cse.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.Brand;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.Style;
import kr.ac.hansung.cse.repo.BrandRepository;
import kr.ac.hansung.cse.repo.StyleRepository;

@RestController
public class BrandController {

	@Autowired
	BrandRepository brandRepository;
	@Autowired
	StyleRepository styleRepository;

	@GetMapping("/feedImage/style/{style}")
	public ResponseEntity<List<Style>> getStyle(@PathVariable("style") String style) {
		List<Style> styles = new ArrayList<>();
		List<Style> result = new ArrayList<>();
		styleRepository.findAll().forEach(styles::add);
		for (Style st : styles) {
			if (st.getStyleName().contains((CharSequence) style)) {
				result.add(st);
			}
		}
		
		result.sort(new Comparator<Style>() {

			@Override
			public int compare(Style o1, Style o2) {
				return o1.getStyleName().compareTo(o2.getStyleName());
			}
		});

		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/feedImage/popularStyle")
	public ResponseEntity<List<Style>> getPopularStyle() {
		List<Style> styles = new ArrayList<>();
		List<Style> result = new ArrayList<>();
		styleRepository.findAll().forEach(styles::add);
		
		styles.sort(new Comparator<Style>() {
			@Override
			public int compare(Style o1, Style o2) {
				Integer num1 = o2.getPostNum();
				Integer num2 = o1.getPostNum();
				return num1.compareTo(num2);
			}
		});
		
		for(int i=0 ; i<10 ; i++) {
			result.add(styles.get(i));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/feedImage/style/{style}")
	public ResponseEntity<Style> putStyle(@PathVariable("style") String style) {
		try {
			Optional<Style> _style = styleRepository.findById(style);
			if (_style.isPresent()) {
				Style st = _style.get();
				st.setPostNum(st.getPostNum() + 1);
				return new ResponseEntity<>(styleRepository.save(st), HttpStatus.CREATED);

			} else {
				Style st = new Style(style, 1);
				return new ResponseEntity<>(styleRepository.save(st), HttpStatus.CREATED);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/feedImage/brand/{brand}")
	public ResponseEntity<List<Brand>> getBrand(@PathVariable("brand") String brand) {
		List<Brand> brands = new ArrayList<>();
		List<Brand> result = new ArrayList<>();
		brandRepository.findAll().forEach(brands::add);
		for (Brand br : brands) {
			if (br.getBrandName().contains((CharSequence) brand)) {
				result.add(br);
			}
		}
		
		result.sort(new Comparator<Brand>() {

			@Override
			public int compare(Brand o1, Brand o2) {
				Integer num1 = o2.getPostNum();
				Integer num2 = o1.getPostNum();
				return num1.compareTo(num2);
			}
		});

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/feedImage/popularBrand")
	public ResponseEntity<List<Brand>> getPopularBrand() {
		List<Brand> brands = new ArrayList<>();
		List<Brand> result = new ArrayList<>();
		brandRepository.findAll().forEach(brands::add);
		
		brands.sort(new Comparator<Brand>() {
			@Override
			public int compare(Brand o1, Brand o2) {
				Integer num1 = o2.getPostNum();
				Integer num2 = o1.getPostNum();
				return num1.compareTo(num2);
			}
		});
		
		for(int i=0 ; i<10 ; i++) {
			result.add(brands.get(i));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/feedImage/brand/{brand}")
	public ResponseEntity<Brand> putBrand(@PathVariable("brand") String brand) {
		try {
			Optional<Brand> _brand = brandRepository.findById(brand);
			if (_brand.isPresent()) {
				Brand br = _brand.get();
				br.setPostNum(br.getPostNum() + 1);
				return new ResponseEntity<>(brandRepository.save(br), HttpStatus.CREATED);

			} else {
				Brand br = new Brand(brand, 1);
				return new ResponseEntity<>(brandRepository.save(br), HttpStatus.CREATED);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
}