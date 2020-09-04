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
public class RankImageLike implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6656652619251867696L;

	
	@Id
	@GeneratedValue
	@Column(name = "like_id")
	private int id;
	
	@Column(name = "like_person")	
	private String likePerson;
	
	@Column(name="image_id")
	private int imageId;

	public RankImageLike(String likePerson) {
		this.likePerson = likePerson;
	}
}
