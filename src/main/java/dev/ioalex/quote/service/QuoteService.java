package dev.ioalex.quote.service;

import dev.ioalex.quote.dto.PaginationDTO;
import dev.ioalex.quote.dto.QuoteDTO;
import dev.ioalex.quote.entity.Quote;
import dev.ioalex.quote.repository.QuoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository repository;

    public QuoteService(QuoteRepository repository) {
        this.repository = repository;
    }

    public List<QuoteDTO> findAll(PaginationDTO paginationDTO) {
        return repository.findAllByOrderByAuthor(PageRequest.of(paginationDTO.getPage(), paginationDTO.getPageSize()))
                .stream().map(entity -> new QuoteDTO(entity.getId(), entity.getAuthor(), entity.getText()))
                .collect(Collectors.toList());
    }

    public QuoteDTO getRandomQuote() {
        Random random = new Random();
        long Id = random.nextInt((int) repository.count()) + 1;
        Quote quote =  repository.findById(Id);
        return new QuoteDTO(quote.getId(), quote.getAuthor(), quote.getText());
    }

    public QuoteDTO findById(long id) {
        Quote quote = repository.findById(id);
        return new QuoteDTO(quote.getId(), quote.getAuthor(), quote.getText());
    }

    public List<QuoteDTO> findAllByTextLike(String text ,PaginationDTO paginationDTO) {
        return repository.findAllByTextLikeOrderByAuthor("%" + text + "%", PageRequest.of(paginationDTO.getPage(), paginationDTO.getPageSize()))
                .stream().map(entity -> new QuoteDTO(entity.getId(), entity.getAuthor(), entity.getText()))
                .collect(Collectors.toList());
    }

    public void delete(long id) {
        repository.deleteQuoteById(id);
    }

    public QuoteDTO update(long id, QuoteDTO quoteDTO) {
        Quote quote = repository.findById(id);
        return updateEntity(quote, quoteDTO);
    }

    public QuoteDTO create(QuoteDTO quoteDTO) {
        return updateEntity(new Quote(), quoteDTO);
    }

    private QuoteDTO updateEntity(Quote quote, QuoteDTO quoteDTO) {
        quote.setText(quoteDTO.getText());
        quote.setAuthor(quoteDTO.getAuthor());
        quote = repository.save(quote);
        quoteDTO.setId(quote.getId());
        return quoteDTO;
    }

}
