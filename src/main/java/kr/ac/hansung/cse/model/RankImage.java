package kr.ac.hansung.cse.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class RankImage implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4743160317787874113L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
    @JsonIgnoreProperties({"rankImage"})
	@OneToOne
	@JoinColumn(name = "image_name")
	private FeedImage image;
	
    @Column(name = "rank")
    private int rank;
    
    @Column(name = "rankTimeStamp")
    private String rankTimeStamp;
    
    public RankImage(FeedImage image, int rank, String rankTimeStamp) {
        this.image = image;
        this.rank = rank;
        this.rankTimeStamp = rankTimeStamp;
    }  
}
