package com.instagram_clone.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.collection.spi.PersistentSet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Playloads.UserDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.metadata.ValidateUnwrappedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Users")
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "requestedUsers")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String fullname;

	@NotNull(message = "Enter the Username")
	private String username;

	@NotNull(message = "Enter the email ")
	@Email
	@Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$", message = "Please enter Valid Email !!")
	private String email;

	@NotNull(message = "Enter the mobile number")
	private String mobile;

	private String password;

	private Date date;

	private String website;

	private String gender;

	private String bio;
	private String account;

	@OneToOne(cascade = CascadeType.ALL  ,fetch = FetchType.EAGER)
	private Image image;

	@Embedded
	@ElementCollection
	//@Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
	private Set<UserDto> followers = new HashSet<>();

	@Embedded
	@ElementCollection
	//@Cascade(value= {org.hibernate.annotations.CascadeType.ALL})
	private Set<UserDto> followings = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns =
	@JoinColumn(name = "story_id", referencedColumnName = "storyId"))
	@JsonManagedReference
	private List<Story> sotories = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	             inverseJoinColumns= @JoinColumn(name = "post_id", referencedColumnName = "postId"))
	private List<Post> savedPost = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = 
	@JoinColumn(name = "post_id", referencedColumnName = "postId"))
	//@JsonManagedReference
	private List<Post> posts = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(

			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleid")
	)
	//@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "roles")
	@JsonIgnore
	private Set<Role> roles = new HashSet<>();
	

	@ManyToMany(cascade= CascadeType.ALL,fetch =FetchType.LAZY)
	@JoinTable(
			name="user_requests",
			joinColumns = @JoinColumn(name = "userid" ,referencedColumnName ="id"),
			inverseJoinColumns = @JoinColumn(name="reuquestid" ,referencedColumnName = "requestid")
			
	)
	//@JsonIgnore
	@JsonManagedReference
	private Set<Requests> requestedUsers = new HashSet<>();

	
}
