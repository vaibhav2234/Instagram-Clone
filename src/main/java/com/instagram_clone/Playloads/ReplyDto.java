package com.instagram_clone.Playloads;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

@Embeddable
@Data
public class ReplyDto {
	@Id
	private long replyid;
	private String commentContent;
	private long parentCommentid;
	private long userid;
	

}
