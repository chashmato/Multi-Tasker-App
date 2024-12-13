package rtp.raidtechpro.co_tasker.models;

import java.util.List;

public class ChatListModel {

    String senderid;
    String sendername;
    String receiverid;
    String receivername;


    public ChatListModel(String senderid, String sendername, String receiverid, String receivername) {
        this.senderid = senderid;
        this.sendername = sendername;
        this.receiverid = receiverid;
        this.receivername = receivername;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }
}
