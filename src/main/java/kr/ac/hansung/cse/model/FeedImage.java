package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

public class FeedImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225217987814035781L;

	@Id
	@GeneratedValue
	@Column(name = "image_id")
	private int id;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "timeStamp")
	private String timeStamp;

	@Column(name="user_id")
	private String userId;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="image_id")
	private List<FeedImageLike> likes = new ArrayList<FeedImageLike>();
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name ="user_id")
	private List<FeedImageComment> comments = new ArrayList<FeedImageComment>();
	
	public FeedImage(String imageName, String timeStamp) {
		this.imageName = imageName;
		this.timeStamp = timeStamp;
	}



}