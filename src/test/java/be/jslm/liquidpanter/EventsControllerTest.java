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


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    public void getAllEventsShouldReturnTwoEvents() throws Exception {

        when(eventService.getAllEvents())
          .thenReturn(
            List.of(
              createEvent(1L, "Title 1", "Zondag", "Abdij"),
              createEvent(2L, "Title 2", "Maandag", "Het Foch")
            )
          );

        this.mockMvc
          .perform(get("/api/events"))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$", hasSize((2))))
          .andExpect(jsonPath("$[0].title", is("Title 1")))
          .andExpect(jsonPath("$[0].when", is("Zondag")))
          .andExpect(jsonPath("$[0].location", is("Abdij")));
    }

    @Test
    public void getEventWithIdOneShouldReturnAnEvent() throws Exception {

        when(eventService.getEventById(1L))
          .thenReturn(
            createEvent(1L, "Title 1","Zondag", "Abdij"));

        this.mockMvc
          .perform(get("/api/events/1"))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"))
          .andExpect(jsonPath("$.title", is("Title 1")))
          .andExpect(jsonPath("$.when", is("Zondag")))
          .andExpect(jsonPath("$.location", is("Abdij")));

    }

    @Test
    public void getEventWithUnknownIdShouldReturn404() throws Exception {

        when(eventService.getEventById(42L)).thenThrow(new EventNotFoundException("Event with id '42' not found"));

        this.mockMvc
          .perform(get("/api/events/42"))
          .andExpect(status().isNotFound());
    }


    private Event createEvent(long id, String title, String when, String location) {
        Event event = new Event();
        event.setId(id);
        event.setTitle(title);
        event.setWhen(when);
        event.setLocation(location);
        return event;
    }

}
