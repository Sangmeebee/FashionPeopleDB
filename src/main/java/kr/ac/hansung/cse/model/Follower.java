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
public class Follower {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "follower_person_id")	
	private String followerPersonId;
	
	@Column(name="user_id")
	private String userId;

	public Follower(String followerPersonId) {
		this.followerPersonId = followerPersonId;
	}
}
