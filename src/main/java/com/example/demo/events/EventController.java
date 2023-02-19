package com.example.demo.events;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

    //@Autowired
    private final EventRepository eventRepository;

    //@Autowired
    private final ModelMapper modelMapper;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper){
        this.eventRepository= eventRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping
   public ResponseEntity createEvent(@RequestBody EventDto eventdto){
        Event event = modelMapper.map(eventdto, Event.class);
        Event newEvent = this.eventRepository.save(event);
        URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        //®URI createUri = URI.create("https://dummy");
        return ResponseEntity.created(createUri).body(event);
   }
}
