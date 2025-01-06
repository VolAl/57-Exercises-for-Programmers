package com.trivia_app.model;

import java.util.List;

public record TriviaElementDTO(Long id, String question, String rightAnswer, List<String>distractors, Integer level) {}
