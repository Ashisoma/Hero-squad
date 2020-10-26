package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeroTest {

    Hero first = new Hero("Flash",23,"speed","zoom",1);
    
    @Test
    public void setAge() {
        assertEquals(23, first.getAge());
    }


    @Test
    public void getHeroesName() {
        assertEquals("Flash", first.getHeroesName());
    }

    @Test
    public void getSuperPower() {
        assertEquals("speed", first.getSuperPower());
    }

    @Test
    public void getWeakness() {
        assertEquals("zoom", first.getWeakness());
    }

    @Test
    public void squad() {
        assertEquals(null, first.Squad());
    }
}// touch