package kr.ac.hansung.cse.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private FUser user;
	
	@OneToOne
	@JoinColumn(name = "following_id")
	private FUser following;


	public Following(FUser user, FUser following) {
		this.user = user;
		this.following = following;
	}
}
