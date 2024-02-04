package dev.ioalex.quote.repository;

import dev.ioalex.quote.entity.Quote;
import dev.ioalex.quote.exception.QuoteNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long>  {

    @Query("""
        SELECT quote FROM Quote quote
        WHERE quote.text LIKE :text
        ORDER BY quote.author
    """)
    List<Quote> findAllByTextLikeOrderByAuthor(@Param("text") String text, Pageable pageable);

    @Query("""
        select quote from Quote quote order by quote.author
    """)
    List<Quote> findAllByOrderByAuthor(Pageable pageable) throws QuoteNotFoundException;

}
