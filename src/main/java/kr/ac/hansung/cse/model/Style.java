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
public class Style implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6643711609244200347L;

	@Id
	@Column(name = "style_name")
	private String styleName;

	@Column(name = "post_num")
	private int postNum;

	public Style(String styleName, int postNum) {
		this.styleName = styleName;
		this.postNum = postNum;
	}

}
