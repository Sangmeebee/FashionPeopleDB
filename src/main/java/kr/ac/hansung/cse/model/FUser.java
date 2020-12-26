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
import javax.persistence.Table;

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
    
    @Column(name = "follower_num")
    private int followerNum;
    
    @Column(name = "following_num")
    private int followingNum;
    
    @Column(name = "evaluate_now")
    private boolean evaluateNow;

    public FUser(String id, String name, String introduce, String gender, String profileImage, boolean evaluateNow) {
        this.id = id;
        this.name = name;
        this.introduce = introduce;
        this.gender = gender;
        this.profileImage = profileImage;
        this.followerNum = 0;
        this.followingNum = 0;
        this.evaluateNow = evaluateNow;
    }

}