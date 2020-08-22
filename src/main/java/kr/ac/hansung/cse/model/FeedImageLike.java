package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FeedImageLike implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9219899251905499498L;

	@Id
	@GeneratedValue
	@Column(name = "like_id")
	private int id;
	
	@Column(name = "like_person")	
	private String likePerson;
	
	@Column(name="image_id")
	private String imageId;

	public FeedImageLike(String likePerson) {
		this.likePerson = likePerson;
	}
	
}
