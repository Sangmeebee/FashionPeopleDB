package kr.ac.hansung.cse.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class ImageScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3646768082545212598L;
	
	@Id
	@GeneratedValue
	@Column(name = "score_id")
	private String id;
	
	@Column(name = "score")	
	private double score;

	@ManyToOne
	@JoinColumn(name = "image_id")
	private FeedImage feedImage;

	public ImageScore(double score, FeedImage feedImage) {
		this.score = score;
		this.feedImage = feedImage;
	}
	
}
