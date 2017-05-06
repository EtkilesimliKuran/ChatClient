package smyy.qsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sümeyye on 14.1.2017.
 */

public class Message implements Serializable {

    public Message(int MessageID, boolean mine, String MessageContent) {
        this.MessageID = MessageID;
        this.isMine = mine;
        this.MessageContent = MessageContent;
    }
    public Message() {
    }
    public Boolean getisMine() {
        return isMine;
    }

    public void setisMine(Boolean isMine) {
        this.isMine = isMine;
    }

    @SerializedName("isMine")
    @Expose
    public Boolean isMine;

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    @SerializedName("MessageID")
    @Expose
    public int MessageID;

    public int getSourceType() {
        return SourceType;
    }

    public void setSourceType(int sourceType) {
        SourceType = sourceType;
    }

    @SerializedName("SourceType")
    @Expose
    public int SourceType;

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    @SerializedName("MessageContent")
    @Expose
    public String MessageContent;
}
