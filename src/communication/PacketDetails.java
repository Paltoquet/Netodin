package communication;

public class PacketDetails {
    private long lastModified;
    private String message;

    public PacketDetails() {
        message = "";
    }

    public void updateMessage(String message) {
        this.message += message;
        lastModified = System.currentTimeMillis() / 1000;
    }

    public String getMessage() {
        return message.substring(0, message.length() - 1);
    }

    /**
     * If the packet was not modified
     * since one minute, we consider that the
     * connection has been lost
     *
     * @return true if the packet is outdated, false otherwise
     */
    public boolean isOutdated() {
        return ((System.currentTimeMillis() / 1000) - lastModified) > 60;
    }

    /**
     * A packet is complete if the last character
     * of the message is a '\n'
     *
     * @return true if the packet is complete, false otherwise
     */
    public boolean isComplete() {
        return message.charAt(message.length() - 1) == '\n';
    }
}
