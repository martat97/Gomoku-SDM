package it.units.gomokusdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordinatesTest {

    @Test
    void getRowIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        Assertions.assertEquals(0, coordinate.getRowIndex());
    }

    @Test
    void getColIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        Assertions.assertEquals(0, coordinate.getColIndex());
    }

    @Test
    void setRowIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        coordinate.setRowIndex(1);
        Assertions.assertEquals(1, coordinate.getRowIndex());
    }

    @Test
    void setColIndexOfCoordinate(){
        Coordinates coordinate = new Coordinates(0,0);
        coordinate.setColIndex(1);
        Assertions.assertEquals(1, coordinate.getColIndex());
    }
}