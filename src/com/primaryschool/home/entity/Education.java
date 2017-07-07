package com.primaryschool.home.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
* @ClassName: Education
* @Description: TODO  阳光德育实体类
* @author Mingshan
* @date 2017年3月26日 下午3:37:48
*
 */

@Entity
@Table(name="ps_education")
public class Education implements Serializable{

	
	private static final long serialVersionUID = -1361964530926611478L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="item_title")
	private String itemTitle;
	
	@Column(name="item_content")
	private String itemContent;
	
	@Column(name="type_id")
	private int  typeId;
	
	@Column(name="add_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  
	private Date addTime;
	
	@Column(name="view_count")
	private int viewCount;
	
	@Column(name="is_image")
	private int isImage;
	
	@Column(name="is_publish")
	private int isPublish;
	
	@Column(name="author")
	private String author;
	
	@Column(name="image_path")
	private String imagePath;
	
	//临时属性
    @Transient
    private String itemTypeFlag;
    
    @Transient
	private String itemTypeName;
	
	public Education(){}
    
    public Education(int id,String itemTitle,Date addTime){
    	super();
    	this.id = id;
		this.itemTitle = itemTitle;
		this.addTime = addTime;
    }
		
    public Education(int id , String itemTitle,Date addTime,int viewCount){
    	super();
    	this.id = id;
		this.itemTitle = itemTitle;
		this.addTime = addTime;
		this.viewCount =viewCount;
    }
    
    public Education(int id, String itemTitle, String itemContent, Date addTime, int viewCount, 
			  String itemTypeName,String itemTypeFlag,String author) {
		super();
		this.id = id;
		this.itemTitle = itemTitle;
		this.itemContent = itemContent;
		this.addTime = addTime;
		this.viewCount = viewCount;
		this.itemTypeName = itemTypeName;
		this.itemTypeFlag = itemTypeFlag;
		this.author=author;
	}
    
    //图片轮播
	public Education(int id, String itemTitle,String itemTypeFlag,String imagePath) {
		super();
		this.id = id;
		this.itemTitle = itemTitle;
		this.itemTypeFlag = itemTypeFlag;
		this.imagePath=imagePath;
	}  
    
	

		public Education(int id,String itemTitle,Date addTime,int isPublish,String author){
	    	super();
	    	this.id = id;
			this.itemTitle = itemTitle;
			this.addTime = addTime;
			this.isPublish=isPublish;
			this.author=author;
	    }
			

	    
	    public Education(int id, String itemTitle, String itemContent, Date addTime, int viewCount, String itemTypeName,
				String itemTypeFlag) {
			super();
			this.id = id;
			this.itemTitle = itemTitle;
			this.itemContent = itemContent;
			this.addTime = addTime;
			this.viewCount = viewCount;
			this.itemTypeFlag = itemTypeFlag;
			this.itemTypeName = itemTypeName;
		}


		public Education(int id,String author, String itemTitle, String itemContent, Date addTime, int viewCount, 
				  String itemTypeName,String itemTypeFlag,int isPublish) {
			super();
			this.id = id;
			this.author=author;
			this.itemTitle = itemTitle;
			this.itemContent = itemContent;
			this.addTime = addTime;
			this.viewCount = viewCount;
			this.itemTypeName = itemTypeName;
			this.itemTypeFlag = itemTypeFlag;
			this.isPublish=isPublish;
			
		}
	    
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getIsImage() {
		return isImage;
	}

	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}

	public int getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(int isPublish) {
		this.isPublish = isPublish;
	}

	public String getItemTypeFlag() {
		return itemTypeFlag;
	}

	public void setItemTypeFlag(String itemTypeFlag) {
		this.itemTypeFlag = itemTypeFlag;
	}

	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
