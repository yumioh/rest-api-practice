package com.example.demo.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class EventTest {

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

    @Test
    @Parameters//(method = "parameterForTestFree")
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        //Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        //when
        event.update();
        //then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] parametersForTestFree() {
        return new Object[] {
                new Object[] {0,0,true},
                new Object[] {100,0,false},
                new Object[] {0,100,false},
                new Object[] {100,200,false},
        };
    }

    @Test
    @Parameters
    public void testOffline(String location, boolean isOffline){
        //Given
        Event event = Event.builder()
                .location(location)
                .build();
        //when
        event.update();
        //then
        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    private Object[] parametersForTestOffline() {
        return new Object[] {
                new Object[] {"강남",true},
                new Object[] {null,false},
                new Object[] {"      ",false}
        };
    }
}