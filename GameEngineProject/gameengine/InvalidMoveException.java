package GameEngineProject.gameengine;

///Thrown when a player attempts an invalid move,such as selecting an out-of-bounds card.

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}