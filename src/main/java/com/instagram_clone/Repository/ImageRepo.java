package com.instagram_clone.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.instagram_clone.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long>{
	
	Optional<Image>findByImageid(long imageid);
	
	@Query("select i from Image i where i.imagename=:imagename")
	Optional<Image>findByImagename(@Param("imagename")String imagename);
	
	

}
