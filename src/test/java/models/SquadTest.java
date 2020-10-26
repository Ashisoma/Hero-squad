package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class SquadTest {

    Squad mySquad = new Squad("Axel","Peace",12);
//    @Test
//    public void getId() {
//        assertEquals(12, mySquad.getId());
//    }

    @Test
    public void getSquadName() {
        assertEquals("Axel", mySquad.getSquadName());
    }


    @Test
    public void getCause() {
        assertEquals("Peace",mySquad.getSquadCause());;
    }

    @Test
    public void setCause() {
        assertEquals("Peace",mySquad.getSquadCause());
    }
}