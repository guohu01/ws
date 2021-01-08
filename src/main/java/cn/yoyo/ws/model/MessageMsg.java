package cn.yoyo.ws.model;

import java.util.List;

public class MessageMsg {
    private List<TextMessage> data;

    @Override
    public String toString() {
        return "MessageMsg{" +
                "data=" + data +
                '}';
    }

    public MessageMsg() {
    }

    public List<TextMessage> getData() {
        return data;
    }

    public void setData(List<TextMessage> data) {
        this.data = data;
    }
}
