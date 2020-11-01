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
	private static final long serialVersionUID = -8926614496461558623L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name = "evaluation_person_id")	
	private String evaluationPersonId;
	
	@Column(name="score")
	private float score;
	
	@Column(name="image_id")
	private String imageId;

	public FeedImageEvaluation(String evaluationPersonId, float score) {
		this.evaluationPersonId = evaluationPersonId;
		this.score = score;
	}
	
}
