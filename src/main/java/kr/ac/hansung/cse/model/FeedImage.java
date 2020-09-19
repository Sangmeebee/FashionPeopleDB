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

	@Column(name="style")
	private String style;
	
	@Column(name="top")
	private String top;
	
	@Column(name="pants")
	private String pants;
	
	@Column(name="shoes")
	private String shoes;
	
	@Column(name="rank")
	private int rank;
	
	@Column(name="battle_now")
	private boolean battleNow;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="image_id")
	private List<FeedImageLike> likes = new ArrayList<FeedImageLike>();
	
	/**
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name ="user_id")
	private List<FeedImageComment> comments = new ArrayList<FeedImageComment>();
	**/
	
	public FeedImage(String imageName, String timeStamp, String style, String top, String pants, String shoes, int rank, boolean battleNow) {
		this.imageName = imageName;
		this.timeStamp = timeStamp;
		this.style = style;
		this.top = top;
		this.pants = pants;
		this.shoes = shoes;
		this.rank = rank;
		this.battleNow = battleNow;
	}



}