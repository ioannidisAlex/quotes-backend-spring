package dev.ioalex.quote;

import dev.ioalex.quote.entity.Quote;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteTest {

    @Test
    void create_new_quote() {

        Quote quote = new Quote();
        quote.setAuthor("Marie");
        quote.setText("Maths is the language of the universe");
        assertNotNull(quote);
        assertEquals("Maths is the language of the universe", quote.getText(), "Quote text is incorrect");
        assertEquals("Marie", quote.getAuthor(), "Author name is incorrect");

    }

    @Test
    void create_new_quote_with_null_text() {

        Quote quote = new Quote();
        quote.setAuthor("Who");
        assertThrows(IllegalArgumentException.class, () -> quote.setText(null));

    }
}
