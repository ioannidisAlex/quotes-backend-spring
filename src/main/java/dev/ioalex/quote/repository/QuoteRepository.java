package dev.ioalex.quote.repository;

import dev.ioalex.quote.entity.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {
    /*
    Create a quote.
    Update a quote with a specific ID.
    Get a quote with a specific ID.
    Delete a quote with a specific ID.
    Get a random quote.
    Get all quotes.
    Get quotes that contain specific text (e.g. "discover").
    */
    @Query("""
        SELECT quote FROM Quote quote
        WHERE quote.text LIKE :text
        ORDER BY quote.author
    """)
    List<Quote> findAllByTextLikeOrderByAuthor(@Param("text") String text, Pageable pageable);

    @Query("""
        select quote from Quote quote order by quote.author
    """)
    List<Quote> findAllByOrderByAuthor(Pageable pageable);

    @Query("""
        SELECT q FROM Quote q
        WHERE q.id = :id
    """)
    Quote findById(@Param("id") long id);


    @Query("""
        DELETE FROM Quote q
        WHERE q.id = :id
    """)
    void deleteQuoteById(@Param("id") long id);

    Quote save(@Validated Quote entity);

}
