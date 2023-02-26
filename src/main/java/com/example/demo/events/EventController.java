package com.example.demo.events;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

    private Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator){
        this.eventRepository= eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;

    }

    @PostMapping("/events")
   public ResponseEntity createEvent(@RequestBody @Valid EventDto eventdto, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        eventValidator.validate(eventdto,errors);
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        //logger.info("~~~~~~"+ eventdto.getLocation());
        Event event = modelMapper.map(eventdto, Event.class);
        event.update();
        Event newEvent = this.eventRepository.save(event);

        ControllerLinkBuilder selfLinkBuilder= linkTo(EventController.class).slash(newEvent.getId());
        URI createURI = selfLinkBuilder.toUri();
        //®URI createUri = URI.create("https://dummy");
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withSelfRel());
        eventResource.add(selfLinkBuilder.withRel("update-events"));
        return ResponseEntity.created(createURI).body(eventResource);
   }
}
