package com.primaryschool.home.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
* @ClassName: HeadMaster
* @Description: TODO 校长 实体类
* @author Mingshan
* @date 2017年4月1日 上午9:31:37
*
 */


@Entity
@Table(name="ps_headmaster")
public class HeadMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="headmaster_name")
	private String name;
	

	@Column(name="headmaster_work")
	private String work;

	@Column(name="headmaster_email")
	private String email;
	
	@Column(name="headmaster_avatar")
	private String avatar;
    
	@Column(name="post_id")
	private String postId;

	@Transient
	private String postName;
	
	public HeadMaster(){}
	public HeadMaster(int id,String name,String work,String email,String avatar,String postName){
		super();
		this.id=id;
		this.name=name;
		this.work=work;
		this.email=email;
		this.avatar=avatar;
		this.postName=postName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}


	
}