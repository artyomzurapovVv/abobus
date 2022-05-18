package com.example.abobus;

public class Message {
    public int MessageID;
    public int UserID;
    public String Data;
    public String Text;

    public Message() {

    }

    public Message(int messageID, int userID, String data, String text) {
        MessageID = messageID;
        UserID = userID;
        Data = data;
        Text = text;
    }
}
