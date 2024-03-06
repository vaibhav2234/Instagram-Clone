package com.instagram_clone.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram_clone.ExceptionHandler.RoleException;
import com.instagram_clone.ExceptionHandler.UserException;
import com.instagram_clone.Playloads.ResponseUserDto;
import com.instagram_clone.Repository.RoleRepo;
import com.instagram_clone.Repository.UserRepo;
import com.instagram_clone.model.Role;
import com.instagram_clone.model.User;
import com.instagram_clone.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	

	@Override
	public Role update(long userid, long roleid) throws RoleException, UserException {
		
	ResponseUserDto user = userServiceImpl.getUserById(userid);
		
		Role existingRole = roleRepo.findByRoleid(roleid).orElseThrow(()->new RoleException("Role is existed with role id :"+roleid));
		
		
		roleRepo.saveAndFlush(existingRole);
		user.getRoles().add(existingRole);
	    User user1 = userServiceImpl.responseUserDtoToUser(user);
		userRepo.saveAndFlush(user1);
		return existingRole;
	}

	@Override
	public Role create(Role role) throws RoleException {
		
		Optional<Role> rolename = roleRepo.findByRolename(role.getRolename());
		if(rolename.isPresent())
		{
		   throw new RoleException("Role is already existed");
		}
		
		Role savedRole = roleRepo.save(role);
		
		
		return savedRole;
	}

	@Override
	public List<User> getUsersByRole(long roleid) throws RoleException {
		List<User> allUsersByRole = roleRepo.getAllUsersByRole(roleid);
		if(allUsersByRole.size()==0)
		{
			throw new RoleException("No User is register on respective role");
		}
		return allUsersByRole;
	}

	@Override
	public String deleteRole(long roleid) throws RoleException {
		Optional<Role> role = roleRepo.findByRoleid(roleid);
		if(role.isEmpty())
		{
			throw new RoleException("Role is not existed with roleid: "+roleid);
		}
		Role role2 = role.get();	
		roleRepo.delete(role2);
		return "Role is Deleted successFully with roleid :"+roleid;
		}

	@Override
	public Role UpdateRole(Role role, long roleid) throws RoleException {
		Role existingRole = roleRepo.findByRoleid(roleid).orElseThrow(()->new RoleException("Role not exited with id :" +roleid));
		
		if(role.getRolename()!=null)
		{
			existingRole.setRolename(role.getRolename());
		}
		
		Role savedRole = roleRepo.saveAndFlush(existingRole);
		return savedRole;
	}

}
