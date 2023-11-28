package dev.ioalex.quote;

import dev.ioalex.quote.controller.QuoteController;
import dev.ioalex.quote.dto.PaginationDTO;
import dev.ioalex.quote.dto.QuoteDTO;
import dev.ioalex.quote.exception.QuoteServiceException;
import dev.ioalex.quote.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
public class QuoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    @Test
    public void findTheTwo() throws Exception {

        List<QuoteDTO> mockQuotes = Arrays.asList(
                new QuoteDTO(1, "TestAuthor1", "QuestQuote1."),
                new QuoteDTO(2, "Kepler", "QuestQuote2.")
        );
        when(quoteService.findAll(any(PaginationDTO.class))).thenReturn(mockQuotes);

        mockMvc.perform(get("/quotes?page=0&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].author", containsInAnyOrder("TestAuthor1", "Kepler")))
                .andExpect(jsonPath("$[*].text", containsInAnyOrder("QuestQuote1.", "QuestQuote2.")));

        verify(quoteService, times(1)).findAll(argThat(argument -> {
            PaginationDTO clapDTO = (PaginationDTO) argument;
            return clapDTO.getPage() == 0 && clapDTO.getPageSize() == 10;
        }));
    }

    @Test
    public void findAll_ServiceError_ReturnsInternalServerError() throws Exception {

        when(quoteService.findAll(any(PaginationDTO.class))).thenThrow(new QuoteServiceException("Service error"));

        mockMvc.perform(get("/quotes?page=0&pageSize=10"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(quoteService, times(1)).findAll(any(PaginationDTO.class));
    }

}