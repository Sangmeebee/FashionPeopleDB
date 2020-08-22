package kr.ac.hansung.cse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class FeedImageComment implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1241243705787344572L;

	@Id
	@GeneratedValue
	@Column(name= "comment_id")
	private int id;

	@Column(name= "comment")
	private String comment;
	
	@Column(name = "time_stamp")
	private String timeStamp;
	
	@Column(name="user_id")
	private String userId;
	
	public FeedImageComment(String comment, String timeStamp) {
		this.comment = comment;
		this.timeStamp = timeStamp;
	}
	
}