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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class FUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7416085012069871833L;

    @Id
    @Column(name= "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "introduce")
    private String introduce;
    
    @Column(name = "gender")
    private String gender;

    @Column(name = "profile_image")
    private String profileImage;
    
    @Column(name = "evaluate_now")
    private boolean evaluateNow;
    
    @JsonIgnoreProperties({"user"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user")
    private List<Follower> followers = new ArrayList<Follower>();
  
    @JsonIgnoreProperties({"user"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user")
    private List<Following> followings = new ArrayList<Following>();
    
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user")
    private List<FeedImage> images = new ArrayList<>();
    
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user")
    private List<FeedImageComment> comments = new ArrayList<FeedImageComment>();
    
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "user")
    private List<SaveImage> saveImages = new ArrayList<SaveImage>();

    public FUser(String id, String name, String introduce, String gender, String profileImage, boolean evaluateNow) {
        this.id = id;
        this.name = name;
        this.introduce = introduce;
        this.gender = gender;
        this.profileImage = profileImage;
        this.evaluateNow = evaluateNow;
    }

}