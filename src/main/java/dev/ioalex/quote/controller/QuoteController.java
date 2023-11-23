package dev.ioalex.quote.controller;


import dev.ioalex.quote.dto.PaginationDTO;
import dev.ioalex.quote.dto.QuoteDTO;
import dev.ioalex.quote.repository.QuoteRepository;
import dev.ioalex.quote.service.QuoteService;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import dev.ioalex.quote.entity.Quote;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService service;

    public QuoteController(QuoteService service) {
        this.service = service;
    }

    // GET http://localhost:8080/quotes?page=2&pageSize=10
    @GetMapping
    public List<QuoteDTO> findAll(@RequestParam int page, @RequestParam int pageSize) {
        return service.findAll(new PaginationDTO(page, pageSize));
    }

    // GET http://localhost:8080/quotes/1111
    @GetMapping("/{id}")
    public QuoteDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    // POST http://localhost:8080/quotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public QuoteDTO create(@RequestBody QuoteDTO quoteDTO) {
        return service.create(quoteDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public QuoteDTO update(@PathVariable long id, @RequestBody QuoteDTO quoteDTO) {
        return service.update(id, quoteDTO);
    }

//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    public void delete(@RequestBody Quote quote) {
//        repository.delete(quote.getId());
//    }

}
