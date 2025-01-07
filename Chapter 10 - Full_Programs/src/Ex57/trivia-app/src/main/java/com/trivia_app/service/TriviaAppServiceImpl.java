package com.trivia_app.service;

import com.trivia_app.entity.TriviaElement;
import com.trivia_app.model.TriviaElementDTO;
import com.trivia_app.repository.TriviaElementRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TriviaAppServiceImpl implements TriviaAppService {

    public final TriviaElementRepository triviaElementRepository;

    public TriviaAppServiceImpl(TriviaElementRepository triviaElementRepository) {
        this.triviaElementRepository = triviaElementRepository;
    }

    @Override
    public List<TriviaElementDTO> getAllTriviaElements() {
        return triviaElementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TriviaElementDTO> getTriviaElementById(Long id) {
        return triviaElementRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public TriviaElementDTO saveOrUpdateTriviaElement(TriviaElement triviaElement) {
        triviaElementRepository.saveAndFlush(triviaElement);
        return convertToDTO(triviaElement);
    }

    @Override
    public void deleteTriviaElement(Long id) {
        triviaElementRepository.deleteById(id);
        triviaElementRepository.flush();
    }

    public TriviaElementDTO convertToDTO(TriviaElement triviaElement) {
        return new TriviaElementDTO(triviaElement.getId(), triviaElement.getQuestion(), triviaElement.getRightAnswer(), triviaElement.getDistractors(), triviaElement.getLevel());
    }

    @Override
    public TriviaElement convertToEntity(TriviaElementDTO triviaElementDTO) {
        TriviaElement triviaElement = new TriviaElement();
        triviaElement.setQuestion(triviaElementDTO.question());
        triviaElement.setRightAnswer(triviaElementDTO.rightAnswer());
        triviaElement.setDistractors(triviaElementDTO.distractors());
        triviaElement.setLevel(triviaElementDTO.level());
        return triviaElement;
    }
}
