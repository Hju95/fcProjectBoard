package com.fastcampus.fcprojectboard.repository;

import com.fastcampus.fcprojectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}