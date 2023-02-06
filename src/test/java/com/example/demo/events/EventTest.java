package com.example.demo.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder(){
     Event event = Event.builder()
             .name("Inflean Spring")
             .description("Description")
             .build();
     assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {

        //given
        String name = "Event";
        String des = "spring";

        //when
        Event event = new Event();
        event.setName(name);
        event.setDescription(des);

        //then
        assertThat(event.getName()).isEqualTo(name); //실제 예상하는 값이 동일하다
        assertThat(event.getDescription()).isEqualTo(des);
        //assert는 개발/테스트 단계에서 파라미터가 제대로 넘어 왔는지 /NULL이 들어오면 작동 안됨
    }

}