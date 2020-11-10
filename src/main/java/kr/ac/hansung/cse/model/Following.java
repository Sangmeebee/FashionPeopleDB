package kr.ac.hansung.cse.model;

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
public class Following {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "following_person_id")	
	private String followingPersonId;
	
	@Column(name="user_id")
	private String userId;

	public Following(String followingPersonId) {
		this.followingPersonId = followingPersonId;
	}
}
