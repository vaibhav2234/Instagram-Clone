package com.instagram_clone.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "users")
public class Requests {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long requestid;
	
	private long requestUserId;
	
	private long followUserId;
	
	private String requestStatus;

	
	@ManyToMany(mappedBy = "requestedUsers", cascade = {CascadeType.PERSIST,CascadeType.
			MERGE,CascadeType.REFRESH},fetch = FetchType.LAZY)
	
	@JsonBackReference
	private Set<User> users =new HashSet<>();


	public Requests(long requestUserId, long followUserId, String requestStatus) {
		super();
		this.requestUserId = requestUserId;
		this.followUserId = followUserId;
		this.requestStatus = requestStatus;
	}
	
	

}
