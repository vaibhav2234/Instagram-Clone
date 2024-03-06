package com.instagram_clone.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Role {
	
	@Id
	private long roleid;
	
	private String rolename;
	
	@ManyToMany(mappedBy = "roles",cascade = { CascadeType.PERSIST,CascadeType.MERGE},fetch =FetchType.EAGER )
	//@JsonIgnore
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class ,property = "users")
	Set<User>users =new HashSet<>();
	

}
