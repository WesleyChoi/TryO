package exceptions;

public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException(String errorMessage) {
        System.out.println(errorMessage);
    }
}
