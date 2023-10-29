package com.fastcampus.fcprojectboard.repository;

import com.fastcampus.fcprojectboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {

}
