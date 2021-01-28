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

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "introduce")
    private String introduce;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "height")
    private Integer height;
    
    @Column(name = "weight")
    private Integer weight;

    @Column(name = "profile_image")
    private String profileImage;

    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Follower> followers = new ArrayList<Follower>();
  
    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Following> followings = new ArrayList<Following>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FeedImage> images = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FeedImageComment> comments = new ArrayList<FeedImageComment>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SaveImage> saveImages = new ArrayList<SaveImage>();

    public FUser(String id, String name, String introduce, String gender, Integer height, Integer weight, String profileImage) {
        this.id = id;
        this.name = name;
        this.introduce = introduce;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.profileImage = profileImage;
    }

}