package be.jslm.liquidpanter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventsController {

    private EventService eventService;

    public EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewEvent(@Valid @RequestBody EventDto eventDto, UriComponentsBuilder uriComponentsBuilder) {

        Long id = eventService.createNewEvent(eventDto);

        UriComponents uriComponents = uriComponentsBuilder.path("/api/events/{id}").buildAndExpand(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public Iterable<Event> getEvents() {

        return eventService.getEvents();

    }
}