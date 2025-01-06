package com.trivia_app.repository;

import com.trivia_app.entity.TriviaElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaElementRepository extends JpaRepository<TriviaElement, Long> {}
