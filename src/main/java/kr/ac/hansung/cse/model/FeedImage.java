package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class FeedImage implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5309265946788881683L;

	@Id
	@GeneratedValue
	@Column(name = "image_id")
	private String id;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "timeStamp")
	private String timeStamp;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private FUser fUser;

	@OneToMany(mappedBy="feedImage", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ImageScore> scores = new ArrayList<ImageScore>();
	
	public void addFeedImage(ImageScore score) {
		scores.add(score);
	}
	
	public FeedImage(String imageName, String timeStamp, FUser fUser) {
		this.imageName = imageName;
		this.timeStamp = timeStamp;
		this.fUser = fUser;
	}



}