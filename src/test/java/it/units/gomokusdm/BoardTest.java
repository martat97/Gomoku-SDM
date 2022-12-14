package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardTest {

    @Test
    public void testBoardInitWithDefaultBoardSizeOf19x19() {
        Board board = new Board();
        int numOfNonEmptyStones = (int) board.getBoard()
                .values()
                .stream()
                .filter(stone -> !stone.equals(Stone.EMPTY))
                .count();
        Assertions.assertEquals(0, numOfNonEmptyStones);
    }

    @Test
     public void testDefaultBoardSize() {
        Board board = new Board();
        int defaultGomokuBoardSize = 19;
        Assertions.assertEquals(defaultGomokuBoardSize, board.getBoardDimension());
    }

    @ParameterizedTest
    @ValueSource(ints = {5,9,15})
    public void testBoardCreationWithDifferentBoardSize(int boardSize) {
        Board board = new Board(boardSize);
        Assertions.assertEquals(boardSize, board.getBoardDimension());
    }

    private static Stream<Arguments> generateSomeValidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(10,10)),
                Arguments.of(new Coordinates(0,0)),
                Arguments.of(new Coordinates(0,0)),
                Arguments.of(new Coordinates(8,1)),
                Arguments.of(new Coordinates(0,15)),
                Arguments.of(new Coordinates(18,17)));
    }

    @ParameterizedTest
    @MethodSource("generateSomeValidCoordinates")
    public void testIfCoordinatesAreValidOn19x19Board(Coordinates coordinates){
        Board board = new Board();
        Assertions.assertTrue(board.areValidCoordinates(coordinates));
    }

    private static Stream<Arguments> generateSomeInvalidCoordinates() {
        return Stream.of(
                Arguments.of(new Coordinates(-2,3)),
                Arguments.of(new Coordinates(28,21)),
                Arguments.of(new Coordinates(41,0)),
                Arguments.of(new Coordinates(20,4)));
    }

    @ParameterizedTest
    @MethodSource("generateSomeInvalidCoordinates")
    public void testIfCoordinatesAreInvalidOn19x19Board(Coordinates coordinates){
        Board board = new Board();
        Assertions.assertFalse(board.areValidCoordinates(coordinates));
    }

    private static Board occupyAllTheBoard(){
        Board board = new Board();
        IntStream.range(0,19).forEach(row ->
                IntStream.range(0, 19)
                        .forEach(col -> {
                            if (col % 2 == 0) {
                                board.setCell(Stone.BLACK, new Coordinates(row, col));
                            } else {
                                board.setCell(Stone.WHITE, new Coordinates(row, col));
                            }
                        }));
        return board;
    }

    @Test
    public void testOccupyAllBoard(){
        Board board = occupyAllTheBoard();
        int numberOfEmptyCell = (int) board.getBoard()
                .values()
                .stream()
                .filter(stone -> stone.equals(Stone.EMPTY))
                .count();
        Assertions.assertEquals(0, numberOfEmptyCell);
    }

    /**
    @Test
    void testBoardPrintBegin() {
        Board board = new Board();
        String result = board.toString();
        String expected_result = "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n" +
                "|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|0|\n";

        Assertions.assertEquals(expected_result, result);

    }
     **/
    @Test
    public void testBoardToStringAtTheBeginning(){
        Board board = new Board();
        String result = board.toString();
        int numberOfStonesInAFullBoard = (int) Math.pow(board.getBoardDimension(), 2);
        String expected_result = "*".repeat(numberOfStonesInAFullBoard);
        Assertions.assertEquals(expected_result, result);
    }


    @Test
    public void testGetStoneInTheFirstCellOfBoardAfterTheStartOfTheGame() {
        Board board = new Board();
        Coordinates coordinates = new Coordinates(0, 0);
        Assertions.assertEquals(Stone.EMPTY, board.getStoneAt(coordinates));
    }

}
