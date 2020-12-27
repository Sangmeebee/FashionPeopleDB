package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
    @JsonIgnoreProperties({"evaluations"})
	@ManyToOne
	@JoinColumn(name = "image_name")
	private FeedImage image;

	public FeedImageEvaluation(String evaluationPersonId, float score) {
		this.evaluationPersonId = evaluationPersonId;
		this.score = score;
	}
	
	public FeedImageEvaluation(String evaluationPersonId, float score, FeedImage image) {
		this.evaluationPersonId = evaluationPersonId;
		this.score = score;
		this.image = image;
	}
	
}
