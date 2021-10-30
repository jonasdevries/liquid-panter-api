package be.jslm.liquidpanter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventsController.class)
class EventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Captor
    private ArgumentCaptor<EventDto> argumentCaptor;

    @Test
    public void postANewEventShouldCreateANewEventInDatabase() throws Exception {

        EventDto eventDto = new EventDto();
        eventDto.setLocation("Abdij");
        eventDto.setTitle("V op zondag");
        eventDto.setWhen("Nu zondag");

        when(eventService.createNewEvent(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/api/events")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(eventDto)))
          .andExpect(status().isCreated())
          .andExpect(header().exists("Location"))
          .andExpect(header().string("Location", "http://localhost/api/events/1"));

        assertThat(argumentCaptor.getValue().getLocation(), is("Abdij"));
        assertThat(argumentCaptor.getValue().getTitle(), is("V op zondag"));
        assertThat(argumentCaptor.getValue().getWhen(), is("Nu zondag"));
    }

}
