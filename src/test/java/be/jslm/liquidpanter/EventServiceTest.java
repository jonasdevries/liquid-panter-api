package be.jslm.liquidpanter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Captor
    private ArgumentCaptor<Event> argumentCaptor;

    @Test
    public void testMapping() {

        // cut
        EventService eventService = new EventService(eventRepository);

        EventDto eventDto = new EventDto();
        eventDto.setWhen("DAN");
        eventDto.setTitle("Titleke");
        eventDto.setLocation("DaaR");

        Event event = new Event();
        event.setId(1L);
        event.setWhen("DAN");
        event.setTitle("Titelek");
        event.setLocation("DaaR");

//        when(eventRepository.save(argumentCaptor.capture())).thenReturn(event);
        when(eventRepository.save(argumentCaptor.capture())).thenReturn(event);

        eventService.createNewEvent(eventDto);

        assertThat(argumentCaptor.getValue().getId(), is(1L));
        assertThat(argumentCaptor.getValue().getWhen(), is("Dan"));
        assertThat(argumentCaptor.getValue().getTitle(), is("Titleke"));
        assertThat(argumentCaptor.getValue().getLocation(), is("DaaR"));

    }
}
