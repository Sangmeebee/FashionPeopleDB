package kr.ac.hansung.cse.model;

import java.util.List;

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
public class Follower {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name="is_following")
	private boolean isFollowing;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private FUser user;
	
	@ManyToOne
	@JoinColumn(name = "follower_id")
	private FUser follower;

	public Follower(FUser user, FUser follower, boolean isFollowing) {
		this.user = user;
		this.follower = follower;
		this.isFollowing = isFollowing;
	}
}
