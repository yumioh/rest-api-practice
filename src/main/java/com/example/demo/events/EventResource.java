package com.example.demo.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
       //  아래 코딩과 동일
        //  add(new Link("http://localhost:8080/api/events/"+event.getId()));
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }
}
