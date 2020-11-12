package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

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
public class FeedImageComment implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8112668373916158367L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@Column(name="content")
	private String content;
	
	@Column(name = "current_date_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime currentDateTime;
	
	@Column(name="image_id")
	private String imageId;
	
	@JsonIgnoreProperties({"comments"})
	@ManyToOne
	@JoinColumn(name = "user_id")
	private FUser user;

	public FeedImageComment(String content, LocalDateTime currentDateTime, FUser user) {
		this.content = content;
		this.currentDateTime = currentDateTime;
		this.user = user;
	}
}