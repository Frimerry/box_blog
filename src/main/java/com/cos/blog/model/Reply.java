package com.cos.blog.model;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SequenceGenerator(
		name = "USER_SEQ_GENERATOR3"
		, sequenceName="USER_SEQ3"
		, initialValue=1
		, allocationSize=1)
public class Reply {
	@Id // 기본키
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GENERATOR3")
	private int id;
	
	@Column(nullable=false, length=200)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="boardsId")
	private Boards boards;
	
	@ManyToOne(optional = true)
	@JoinColumn(name="userid", foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (userid) references users (id) ON DELETE SET NULL"))
	private Users users;
	
	@CreationTimestamp
	private Timestamp createDate;

}
