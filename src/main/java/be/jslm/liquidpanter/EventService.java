package be.jslm.liquidpanter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Event getEventById(long id) {

        Optional<Event> requestedEvent = eventRepository.findById(id);

        if(requestedEvent.isEmpty()){
            throw new EventNotFoundException(String.format("Event with id: '%s' not found", id));
        }

        return requestedEvent.get();
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    @Transactional
    public Event updateEvent(long id, EventDto eventDto) {

        Event eventToUpdate =
          eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException(String.format("Event with id: '%s' not found", id)));

        eventToUpdate.setTitle(eventDto.getTitle());
        eventToUpdate.setWhen(eventToUpdate.getWhen());
        eventToUpdate.setLocation(eventDto.getLocation());
        return eventRepository.save(eventToUpdate);
    }

    public void deleteEventById(Long id) {
        eventRepository.deleteById(id);
    }
}
