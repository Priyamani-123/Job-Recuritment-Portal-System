public class TooManyAttemptsException
        extends RuntimeException {

    public TooManyAttemptsException() {

        super(
                "You have reached the maximum of 3 attempts."
        );
    }
}