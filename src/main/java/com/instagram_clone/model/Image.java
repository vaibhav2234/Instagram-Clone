package com.instagram_clone.model;

import java.sql.Blob;

import com.instagram_clone.Playloads.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@Table(name = "Image")
@NoArgsConstructor
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long imageid;
	
	private String imagetype;
	
	private String imagename;
	
	@Lob
	private Blob imagecontent;
	

	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Post post;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Story story;

	public Image(String imagetype, String imagename, Blob imagecontent) {
		super();
		this.imagetype = imagetype;
		this.imagename = imagename;
		this.imagecontent = imagecontent;
	} 
	
	
}
