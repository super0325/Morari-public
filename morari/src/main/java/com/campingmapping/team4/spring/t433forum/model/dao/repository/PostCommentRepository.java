package com.campingmapping.team4.spring.t433forum.model.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.campingmapping.team4.spring.t433forum.model.entity.Post;
import com.campingmapping.team4.spring.t433forum.model.entity.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

	// 依post查留言
	public List<PostComment> findByPost(Post post);
	
	// 依post查非隱藏留言
	public Page<PostComment> findByPostAndPostcommenthide(Post post, Integer postcommenthide, Pageable pageable);
//	public List<PostComment> findByPostAndPostcommenthide(Post post, Integer postcommenthide);
	
}
