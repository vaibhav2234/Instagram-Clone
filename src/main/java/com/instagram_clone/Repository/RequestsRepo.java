package com.instagram_clone.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instagram_clone.model.Requests;

@Repository

public interface RequestsRepo extends JpaRepository<Requests, Long>{
	
	Optional<Requests> findByRequestid(long requestid);
	
	
	@Query("SELECT r FROM Requests r WHERE r.requestUserId=:requestUserId")
	Optional<List<Requests>> getRequestByRequestUserId(@Param("requestUserId") long requestUserId);
	
	
	Optional<Requests>findByRequestUserIdAndFollowUserId(@Param("requestUserId") long requestUserId, @Param("followUserId") long followUserId);

	Optional<Requests>findByFollowUserIdAndRequestUserId( long followUserId, long requestUserId);
	

}
