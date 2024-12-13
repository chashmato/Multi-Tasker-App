package rtp.raidtechpro.co_tasker.models;

public class ChatModel {

    private String sender;
    private String message;
    private String datetime;
    private String sts;

    public ChatModel(String sender, String message, String datetime, String sts) {
        this.sender = sender;
        this.message = message;
        this.datetime = datetime;
        this.sts = sts;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }
}
