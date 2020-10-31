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
public class FeedImageEvaluation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9219899251905499498L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "evaluation_person_id")	
	private String evaluationPersonId;
	
	@Column(name="score")
	private float score;

	public FeedImageEvaluation(String evaluationPersonId, float score) {
		this.evaluationPersonId = evaluationPersonId;
		this.score = score;
	}
	
}
