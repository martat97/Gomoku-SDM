package it.units.gomokusdm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class CLIControllerTest {

    @Test
    void test() {
        Board board = new Board();
        board.setCell(Stone.BLACK, new Coordinates(0,0));
        CLIController cli = CLIController.createInstance(System.out, System.in);
        cli.printBoard();
    }

    @Test
    void testCLICreation() {
        CLIController cli = CLIController.createInstance(System.out, System.in);
        Assertions.assertEquals(cli.getClass(), CLIController.class);
    }

    @Test
    void testGetCLI() {
        CLIController cli = CLIController.createInstance(System.out, System.in);
        Assertions.assertEquals(CLIController.getInstance(), cli);
    }

    @Test
    void testPlayersInitialization() throws IOException {
        String inputString = "Player One\nPlayer Two\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.initializeGameCLI();
        Assertions.assertEquals("Player One", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player Two", cli.getPlayer2().getUsername());
    }

    @Test
    void testPlayersInitializationWithoutUsername() throws IOException {
        String inputString = "\n\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        cli.initializeGameCLI();
        Assertions.assertEquals("Player_1", cli.getPlayer1().getUsername());
        Assertions.assertEquals("Player_2", cli.getPlayer2().getUsername());
    }

    @Test
    void testPlayersCoordinatesInput() throws IOException {
        String inputString = "1\n1\n"; //position (1,1)
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player= new Player("A", Stone.WHITE);
        Coordinates coordinates = cli.getCoordinatesByPlayerInput(player);
        Assertions.assertEquals(new Coordinates(1,1), coordinates);
    }

    @Test
    void testNonNumericPlayersCoordinatesInput() throws IOException {
        String inputString = "a\na\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player= new Player("A", Stone.WHITE);
        Coordinates coordinates = cli.getCoordinatesByPlayerInput(player);
        Assertions.assertNull(coordinates);
    }

    @Test
    void testCompleteGameSimulation() throws IOException {
        String inputString =
                "player one\n" +
                "player two\n" +
                "9\n10\n" +
                "9\n8\n" +
                "9\n11\n" +
                "9\n7\n" +
                "9\n12\n" +
                "9\n6\n" +
                "9\n13\n" +
                "9\n5\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        Player player2 = cli.getPlayer2();
        cli.initializeGameCLI();
        cli.startGameClI();
        Assertions.assertEquals(cli.getWinner(), player1);
    }

    @Test
    void testCompleteGameSimulationWithWrongPlayerInputs() throws IOException {
        String inputString =
                "player one\n" +
                "player two\n" +
                "9\n10\n" +
                "9\n8\n" +
                "9\n11\n" +
                "9\n7\n" +
                "9\n12\n" +
                "9\n6\n" +
                "9\n13\n" +
                "9\n*!&%x\n" +
                "9\n5\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        CLIController.closeInstance();
        CLIController cli = CLIController.createInstance(System.out, inputStream);
        Player player1 = cli.getPlayer1();
        Player player2 = cli.getPlayer2();
        cli.initializeGameCLI();
        cli.startGameClI();
        Assertions.assertEquals(cli.getWinner(), player1);
    }

}
