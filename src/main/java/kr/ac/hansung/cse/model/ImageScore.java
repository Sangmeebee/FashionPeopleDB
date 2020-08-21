package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImageScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1954943840103865394L;

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
