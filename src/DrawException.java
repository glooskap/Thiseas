public class DrawException extends Exception {

    private static final String DEFAULT_MESSAGE = "Error drawing the labyrinth";
    private final String messageCode;

    public DrawException() {
        super(DEFAULT_MESSAGE);
        messageCode = null;
    }

    public DrawException(String message) {
        super(DEFAULT_MESSAGE+"\n"+message);
        messageCode = null;
    }

    public DrawException(String message, String messageCode) {
        super(DEFAULT_MESSAGE+"\n"+message);
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        if (messageCode==null) return "EXCEPTION";
        return messageCode;
    }
}
