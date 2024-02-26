package game.engine.exceptions;
import java.lang.Exception;
public abstract class GameActionException extends Exception {
    public GameActionException(){
        super();
    }
    public  GameActionException(String message){
        super(message);
    }
}
