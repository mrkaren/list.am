package com.example.listam.repository;

import com.example.listam.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

//    @Query(value = "select * from comment where item_id = :itemId", nativeQuery = true)
//    List<Comment> getAllCommentsByItemId(@Param("itemId") int itemId);

    List<Comment> findAllByItem_Id(int itemId);

}
