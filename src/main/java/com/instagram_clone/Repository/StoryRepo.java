package com.instagram_clone.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram_clone.model.Story;

public interface StoryRepo extends JpaRepository<Story, Long> {

}
