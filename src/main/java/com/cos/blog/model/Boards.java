package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.ForeignKey;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="boards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(
		name ="USER_SEQ_GENERATOR2"
		, sequenceName="USER_SEQ2"
		,initialValue=1
		, allocationSize=1
		)
public class Boards {
	@Id // 기본키
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GENERATOR2")
	private int id;
	
	@Column(nullable=false, length=50)
	private String category;
	
	@Column(nullable=false, length=100)
	private String title;
	
	private int count; // 조회수
	
	@Lob
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="userid", foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (userid) references users (id) ON DELETE SET NULL"))
	private Users users;

	@OneToMany (mappedBy="boards", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)	// board 삭제 시 댓글도 삭제함
	@JsonIgnoreProperties({"boards"})
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp
	private Timestamp createDate;
}
