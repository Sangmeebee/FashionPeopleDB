package kr.ac.hansung.cse.controller;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.hansung.cse.model.FUser;
import kr.ac.hansung.cse.model.FeedImage;
import kr.ac.hansung.cse.model.RankImage;
import kr.ac.hansung.cse.repo.FUserRepository;
import kr.ac.hansung.cse.repo.FeedImageRepository;
import kr.ac.hansung.cse.repo.RankImageRepository;
import kr.ac.hansung.cse.repo.SaveImageRepository;

@RestController
@RequestMapping("/rankImage")
public class RankImageController {

	@Autowired
	FeedImageRepository feedImageRepository;
	@Autowired
	RankImageRepository rankImageRepository;

	@GetMapping
	public ResponseEntity<Map<String, List<RankImage>>> getRankImages() {
		List<RankImage> images = new ArrayList<>();
		Map<String,List<RankImage>> map = new HashMap<>();
		rankImageRepository.findAll().forEach(images::add);
		Set<String> timeStamp = new HashSet<>();
		for(RankImage image : images) {
					timeStamp.add(image.getRankTimeStamp());
		}
		for(String ts : timeStamp) {
			List<RankImage> i = rankImageRepository.findByRankTimeStamp(ts);
			map.put(ts, i);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<List<RankImage>> updateRankImage(LocalDateTime nowTimeStamp) {
		List<FeedImage> images = new ArrayList<>();
		feedImageRepository.findAll().forEach(images::add);
		List<FeedImage> rankImages = new ArrayList<>();
		for (FeedImage image : images) {
			if (image.getResultTimeStamp() != null) {
				LocalDateTime timeStamp = image.getResultTimeStamp();
				LocalDateTime sevenDaysAgo = nowTimeStamp.minusDays(7).minusNanos(1);
				if (timeStamp.isAfter(sevenDaysAgo) && timeStamp.isBefore(nowTimeStamp)) {
					rankImages.add(image);
				}
			}
		}
		rankImages.sort(new Comparator<FeedImage>() {
			@Override
			public int compare(FeedImage o1, FeedImage o2) {
				Float rating1 = o1.getResultRating();
				Float rating2 = o2.getResultRating();
				if (rating1 == rating2)
					return 0;
				else if (rating2 > rating1)
					return 1;
				else
					return -1;
			}

		});

		for (int i = 0; i < rankImages.size(); i++) {
			if (i == 10) {
				break;
			}
			WeekFields weekFields = WeekFields.of(Locale.getDefault());
			String ts = Integer.toString(nowTimeStamp.getYear()) + "년 " + Integer.toString(nowTimeStamp.getMonthValue())
					+ "월 " + Integer.toString(nowTimeStamp.get(weekFields.weekOfMonth())) + "주차";
			RankImage rankImage = new RankImage(rankImages.get(i), i + 1, ts);
			rankImageRepository.save(rankImage);
		}
		List<RankImage> rImages = new ArrayList<>();
		rankImageRepository.findAll().forEach(rImages::add);
		return new ResponseEntity<>(rImages, HttpStatus.OK);
	}

	@Scheduled(cron = "0 0 0 * * 1")
	public void task1() {
		updateRankImage(LocalDateTime.now());

	}
}