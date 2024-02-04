package dev.ioalex.quote.service;

import dev.ioalex.quote.dto.PaginationDTO;
import dev.ioalex.quote.dto.QuoteDTO;
import dev.ioalex.quote.entity.Quote;
import dev.ioalex.quote.exception.QuoteNotFoundException;
import dev.ioalex.quote.exception.QuoteServiceException;
import dev.ioalex.quote.repository.QuoteRepository;
import org.springframework.data.domain.PageRequest;
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
        try {
            return repository.findAllByOrderByAuthor(PageRequest.of(paginationDTO.getPage(), paginationDTO.getPageSize()))
                    .stream().map(entity -> new QuoteDTO(entity.getId(), entity.getAuthor(), entity.getText()))
                    .collect(Collectors.toList());
            LOGGER.info("Successfully retrieved {} quotes for page {} and pageSize {}", responseQuotesDTO.size(), paginationDTO.getPage(), paginationDTO.getPageSize());
            return  responseQuotesDTO;
        } catch (QuoteNotFoundException e) {
            throw new QuoteServiceException("An error occurred in QuoteService", e);
        }
    }

    public QuoteDTO getRandomQuote() {
        if (repository.count() > 0) {
            Random random = new Random();
            Optional<Quote> quoteOpt;
            do {
                long Id = random.nextInt((int) repository.count()) + 1;
                quoteOpt = repository.findById(Id);
            } while (quoteOpt.isEmpty());
            return new QuoteDTO(quoteOpt.get().getId(), quoteOpt.get().getAuthor(), quoteOpt.get().getText());
        } else {
            LOGGER.warn("Empty database");
            return null;
        }
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
        repository.deleteById(id);
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
