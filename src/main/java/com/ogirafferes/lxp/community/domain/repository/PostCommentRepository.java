package com.ogirafferes.lxp.community.domain.repository;

import com.ogirafferes.lxp.community.domain.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
