package kr.ac.hansung.cse.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class SaveImage implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8911329601937935180L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
    @JsonIgnoreProperties({"saveImage"})
	@OneToOne
	@JoinColumn(name = "image_name")
	private FeedImage image;

    @JsonIgnoreProperties({"saveImages"})
	@ManyToOne
	@JoinColumn(name = "user_id")
    private FUser user;

    public SaveImage(FUser user, FeedImage image) {
    	this.user = user;
        this.image = image;
    }
}