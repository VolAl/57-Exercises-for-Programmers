package com.trivia_app.controller;

import com.trivia_app.entity.TriviaElement;
import com.trivia_app.model.TriviaElementDTO;
import com.trivia_app.service.TriviaAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trivia-app")
public class TriviaAppController {

    private final TriviaAppService triviaAppService;

    public TriviaAppController(TriviaAppService triviaAppService) {
        this.triviaAppService = triviaAppService;
    }

    @GetMapping
    public List<TriviaElementDTO> getAllTriviaElements() {
        return triviaAppService.getAllTriviaElements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriviaElementDTO> getTriviaElementById(@PathVariable Long id) {
        Optional<TriviaElementDTO> triviaElementDTO = triviaAppService.getTriviaElementById(id);
        return triviaElementDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TriviaElementDTO createTriviaElement(@RequestBody TriviaElement triviaElement) {
        return triviaAppService.saveOrUpdateTriviaElement(triviaElement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TriviaElementDTO> updateTriviaElement(@RequestBody TriviaElement triviaElement) {
        try {
            TriviaElementDTO updatedTriviaElement = triviaAppService.saveOrUpdateTriviaElement(triviaElement);
            return ResponseEntity.ok(updatedTriviaElement);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTriviaElement(@PathVariable Long id) {
        triviaAppService.deleteTriviaElement(id);
        return ResponseEntity.noContent().build();
    }
}
