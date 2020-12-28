package kr.ac.hansung.cse.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
	
	@Column(name="is_following")
	private boolean isFollowing;

    @JsonIgnoreProperties({"followers"})
	@ManyToOne
	@JoinColumn(name = "user_id")
	private FUser user;

	@Column(name = "follower_id")
	private String followerId;

	public Follower(FUser user, String followerId, boolean isFollowing) {
		this.user = user;
		this.followerId = followerId;
		this.isFollowing = isFollowing;
	}
}
