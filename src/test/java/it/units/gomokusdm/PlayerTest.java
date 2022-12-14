package it.units.gomokusdm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void testPlayerCreation(){
        Player player = new Player("xyz", Stone.BLACK);
        Assertions.assertInstanceOf(Player.class, player);
    }

    @Test
    public void testChangeUsernameOfPlayer(){
        Player player = new Player("xyz", Stone.BLACK);
        player.setUsername("qwerty");
        Assertions.assertEquals("qwerty", player.getUsername());
    }

    @Test
    public void test2PlayersCreationAreDifferent(){
        Player player1 = new Player("xyz", Stone.BLACK);
        Player player2 = new Player("xyz", Stone.WHITE);
        Assertions.assertNotEquals(player1, player2);
    }

    @Test
    public void testGetPlayerColour(){
        Player player = new Player("xyz", Stone.BLACK);
        Assertions.assertEquals(Stone.BLACK, player.getColour());
    }

    @Test
    public void testAddMoveOfStoneDoneByPlayer(){
        Player player = new Player("xyz", Stone.BLACK);
        player.addMove(new Coordinates(0,0));
        Assertions.assertEquals(1, player.getMovesList().size());
    }

    @Test
    public void testDefaultUserBlack(){
        // - vogliamo che il test fallisca se non inserisco il nome?
        // - oppure non fallisce, ma viene mantenuto di default lo user Player 1/2 (numero scelto in base al colore)
        Player player = new Player("", Stone.BLACK);
        Assertions.assertEquals("Player_1", player.getUsername());
    }

    @Test
    public void testDefaultUserWhite(){
        // - vogliamo che il test fallisca se non inserisco il nome?
        // - oppure non fallisce, ma viene mantenuto di default lo user Player 1/2 (numero scelto in base al colore)
        Player player = new Player("", Stone.WHITE);
        Assertions.assertEquals("Player_2", player.getUsername());
    }


}
