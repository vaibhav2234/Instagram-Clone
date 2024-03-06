package com.instagram_clone.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;

public interface RoleRepo extends JpaRepository<Role, Long>{
	
	Optional<Role> findByRoleid(long roleid);
	
	Optional<Role> findByRolename(String rolename);
	
	@Query("select r.users from Role r where r.roleid= :roleid")
	List<User>getAllUsersByRole(long roleid);

}
