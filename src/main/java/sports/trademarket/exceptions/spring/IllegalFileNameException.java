package sports.trademarket.exceptions.spring;

public class IllegalFileNameException extends NullPointerException {

    public IllegalFileNameException() {
        super();
    }

    public IllegalFileNameException(String s) {
        super(s);
    }
}
