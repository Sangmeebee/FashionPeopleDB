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
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class FeedImage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4225217987814035781L;

    @Id
    @Column(name = "image_name")
    private String imageName;

    @Column(name = "timeStamp")
    private LocalDateTime timeStamp;

    @Column(name = "style")
    private String style;

    @Column(name = "top")
    private String top;

    @Column(name = "pants")
    private String pants;

    @Column(name = "shoes")
    private String shoes;

    @Column(name = "rank")
    private int rank;

    @Column(name = "evaluate_now")
    private boolean evaluateNow;
    
    @Column(name = "result_rating")
    private float resultRating;
    
    @Column(name = "resultTimeStamp")
    private LocalDateTime resultTimeStamp;

    @JsonIgnoreProperties({"image"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<FeedImageEvaluation> evaluations = new ArrayList<FeedImageEvaluation>();
    
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<FeedImageComment> comments = new ArrayList<FeedImageComment>();
    
    @JsonIgnoreProperties({"image"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL)
    private RankImage rankImage;
        
    @JsonIgnoreProperties({"images"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private FUser user;

    public FeedImage(String imageName, String style, String top, String pants, String shoes, int rank, boolean evaluateNow, float resultRating, FUser user) {
        this.imageName = imageName;
        this.style = style;
        this.top = top;
        this.pants = pants;
        this.shoes = shoes;
        this.rank = rank;
        this.evaluateNow = evaluateNow;
        this.resultRating = resultRating;
        this.timeStamp = LocalDateTime.now();
        this.resultTimeStamp = null;
        this.user = user;      
    }

}