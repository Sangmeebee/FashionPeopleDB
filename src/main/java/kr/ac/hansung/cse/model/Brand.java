package kr.ac.hansung.cse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Brand implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -7986402738521364781L;

    @Id
    @Column(name= "brand_name")
    private String brandName;
    
    @Column(name= "post_num")
    private int postNum;
    
    public Brand(String brandName, int postNum) {
    	this.brandName = brandName;
    	this.postNum = postNum;
    }

}
