package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RankImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5306071520320797858L;
	
	@Id
	@GeneratedValue
	@Column(name = "image_id")
	private int id;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "time_stamp")
	private String timeStamp;
	
	@Column(name = "competition_date")
	private String competitionDate;
	
	@Column(name="user_id")
	private String userId;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="image_id")
	private List<RankImageLike> likes = new ArrayList<RankImageLike>();

	
	public RankImage(String imageName, String timeStamp, String competitionDate) {
		this.imageName = imageName;
		this.timeStamp = timeStamp;
		this.competitionDate = competitionDate;
	}

	
}
