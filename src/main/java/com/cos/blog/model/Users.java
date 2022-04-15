package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
		name="USER_SEQ_GENERATOR"
		, sequenceName = "USER_SEQ"
		, initialValue = 1
		, allocationSize = 1
		)

@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
	@Id // PRIMARY KEY
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GENERATOR")
	private int id;
	
	@Column(nullable=false, length=100,unique=true)
	private String userid;
	
	@Column(nullable=false, length=100)
	private String username;
	
	@Column(nullable=false, length=100)
	private String password;
	
	@Column(nullable=true, length=30)
	private String phone;
	
	@Column(nullable=false, length=50)
	private String email;
	
	@Column(nullable=true, length=20)
	private int zipcode;
	
	@Column(nullable=true, length=50)
	private String address1;
	
	@Column(nullable=true, length=50)
	private String address2;
	
	@Enumerated(EnumType.STRING)
	private RoleType roles;
	
	// 카카오 등 다른 계정 사용자인지 구분
	private String oauth;
	
	@CreationTimestamp
	private Timestamp createDate;
}
