package be.jslm.liquidpanter;

import org.springframework.stereotype.Service;

@Service
public class EventService {

    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Long createNewEvent(EventDto eventDto) {

        Event event = new Event();
        event.setLocation(eventDto.getLocation());
        event.setTitle(eventDto.getTitle());
        event.setWhen(event.getWhen());

        event = eventRepository.save(event);

        return event.getId();
    }

    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }
}
