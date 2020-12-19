package exceptions;

public class PlayerDoesNotExistException extends Exception {
    public PlayerDoesNotExistException(String errorMessage) {
        System.out.println(errorMessage);
    }

}
