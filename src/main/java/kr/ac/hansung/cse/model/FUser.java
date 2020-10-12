package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7416085012069871833L;

	@Id
	@Column(name = "user_id")
	private String id;
	
	@Column(name = "name")
	private String name;

	@Column(name = "instagram_id")
	private String instagramId;
	
	@Column(name = "profile_image")
	private String profileImage;
	
	@OneToMany(mappedBy="user")
	@JsonManagedReference
	private List<FeedImage> images = new ArrayList<>();

	public FUser(String id, String name, String instagramId, String profileImage) {
		this.id = id;
		this.name = name;
		this.instagramId = instagramId;
		this.profileImage = profileImage;
	}

}