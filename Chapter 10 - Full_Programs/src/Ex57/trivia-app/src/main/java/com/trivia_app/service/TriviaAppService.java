package com.trivia_app.service;

import com.trivia_app.entity.TriviaElement;
import com.trivia_app.model.TriviaElementDTO;

import java.util.List;
import java.util.Optional;

public interface TriviaAppService {

    List<TriviaElementDTO> getAllTriviaElements();
    Optional<TriviaElementDTO> getTriviaElementById(Long id);
    TriviaElementDTO saveOrUpdateTriviaElement(Long id, TriviaElement TriviaElement);
    void deleteTriviaElement(Long id);
    TriviaElement convertToEntity(TriviaElementDTO TriviaElementDTO);

}
