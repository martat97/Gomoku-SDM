package it.units.gomokusdm;


import java.util.ArrayList;
import java.util.List;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private Coordinates lastMoveCoordinates;
    private Player lastMovingPlayer;

    public Game(Board board, Player player1, Player player2) throws Exception {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        if (checkPlayerColours(player1, player2)) {
            makeMandatoryFirstMove(player1.getColour() == Colour.BLACK ? player1 : player2);
        } else {
            throw new Exception("invalid player colors");
        }
    }

    private static boolean checkPlayerColours(Player player1, Player player2) {
        return player1.getColour() != player2.getColour();
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Coordinates getLastMoveCoordinates() {
        return lastMoveCoordinates;
    }

    private void makeMandatoryFirstMove(Player player) {
        board.setCell(player.getColour(),
                new Coordinates(board.getBoardDimension() / 2, board.getBoardDimension() / 2));
    }

    public void makeMove(Player player, Coordinates coordinates) throws Exception {
        if (isFeasibleMove(coordinates) && isTurnOfPlayer(player)) {
            board.setCell(player.getColour(), coordinates);
            lastMoveCoordinates = new Coordinates(coordinates.getRowIndex(), coordinates.getColIndex());
            lastMovingPlayer = player;
        } else {
            throw new Exception("Invalid arguments.");
        }
    }

    private boolean isTurnOfPlayer(Player player) {
        return player != lastMovingPlayer;
    }

    // Implemento metodo super basico che verifica se ci sono nstones dello stesso colore,
    // basandosi su i,j per le direzioni
    // metodo poco leggibile anche se funziona, aperto al refactoring
    public boolean countStones(int colDirection, int rowDirection, int winningColour, int n) {
        int rowIndex = lastMoveCoordinates.getRowIndex();
        int colIndex = lastMoveCoordinates.getColIndex();
        int i = colDirection * (n - 1);
        int j = rowDirection * (n - 1);
        int i_increment = i / (n - 1);
        int j_increment = j / (n - 1);
        int counterStones = 0;
        if (n <= board.getBoardDimension() && n > 0) {
            while (i <= (n - 1) && i >= -(n - 1) && j <= (n - 1) && j >= -(n - 1)) {
                if (board.areValidCoordinates(new Coordinates(rowIndex + j, colIndex + i))) {
                    int stoneColourNumber = board.getStoneAt(new Coordinates(rowIndex + j, colIndex + i));
                    if (stoneColourNumber == winningColour) {
                        counterStones++;
                    } else {
                        if (counterStones != n) {
                            counterStones = 0;
                        } else {
                            i = (n - 1);
                            j = (n - 1); // stop while, there are already five consecutive stones
                        }
                    }
                }
                i = i - i_increment;
                j = j - j_increment;
            }
        }
        return (counterStones == n);
    }

    public boolean checkIfPlayerWins() {
        return getAllDirectionsResultOfConsecutiveNStones(5).contains(Boolean.TRUE);
    }

    private List<Boolean> getAllDirectionsResultOfConsecutiveNStones(int N) {
        List<Boolean> valuesOfAdjStonesInAllDirection = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            valuesOfAdjStonesInAllDirection.add(checkIfThereAreNConsecutiveStonesInDirection(direction, N));
        }
        return valuesOfAdjStonesInAllDirection;
    }

    private boolean checkIfThereAreNConsecutiveStonesInDirection(Direction direction, int N) {
        List<Boolean> valueOfAdjacentStones = new ArrayList<>();
        Coordinates currentCoordinate = lastMoveCoordinates;
        valueOfAdjacentStones.add(Boolean.TRUE);
        int valueToControl = N - 1;
        while (valueToControl > 0) {
            Coordinates coordinatesInDirection = currentCoordinate.getCoordinateMovedInDirectionWithStep(direction, 1);
            if (!board.areValidCoordinates(coordinatesInDirection) || !checkIfStonesAreEqual(currentCoordinate, coordinatesInDirection)) {
                valueOfAdjacentStones.add(Boolean.FALSE);
                break;
            }
            valueOfAdjacentStones.add(Boolean.TRUE);
            currentCoordinate = coordinatesInDirection;
            valueToControl--;
        }
        return valueOfAdjacentStones.size() == N && !valueOfAdjacentStones.contains(Boolean.FALSE);
    }

    private boolean checkIfStonesAreEqual(Coordinates firstCoordinate, Coordinates secondCoordinate) {
        return board.areStonesOfSameColourAt(firstCoordinate, secondCoordinate);
    }

    public boolean checkIfThereAreFiveConsecutiveStones(Colour colour) {
        boolean areThereFiveStones = false;
        int winningColour = 1;
        if (colour == Colour.WHITE) {
            winningColour = 2;
        }
        int[] directions = {-1, 0, 0, -1, -1, -1, 1, -1}; // -1 -> sx - +1 -> dx
        int len = 0;
        while (len < directions.length) {
            areThereFiveStones = countStones(directions[len], directions[len + 1], winningColour, 5);
            if (areThereFiveStones) {
                len = directions.length; // stop while if I've already found 5 stones in a direction
            }
            len = len + 2;
        }
        return (areThereFiveStones);

    }


    // controlla se esiste una stone adiacente intorno a quella che sto inserendo. se ne esiste almeno una,
    // posso inserire la stone
    public boolean isThereAnAdjacentStone(Coordinates coordinates) {
        ArrayList<Coordinates> adjacent_coordinates = coordinates.getAdjacentCoordinates();
        for (Coordinates el : adjacent_coordinates) {
            if (board.areValidCoordinates(coordinates) && !board.isEmptyCell(el)) {
                return true;
            }
        }
        return false;
    }

    // la cella dev'essere vuota, e intorno ce ne deve essere almeno una adiacente
    public boolean isFeasibleMove(Coordinates coordinates) {
        return board.isEmptyCell(coordinates) && isThereAnAdjacentStone(coordinates);
    }
}
