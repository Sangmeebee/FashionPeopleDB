package kr.ac.hansung.cse.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
    @JsonIgnoreProperties({"followings"})
	@ManyToOne
	@JoinColumn(name = "following_id")
	private FUser following;

	public Following(FUser following) {
		this.following = following;
	}
}
