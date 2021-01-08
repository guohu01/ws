package cn.yoyo.ws.model;

import java.util.List;

public class TextMessage {
    private String sn;
    private String is_complete;
    private List<MessageData> data;

    @Override
    public String toString() {
        return "TextMessage{" +
                "sn='" + sn + '\'' +
                ", is_complete='" + is_complete + '\'' +
                ", data=" + data +
                '}';
    }

    public TextMessage() {
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIs_complete() {
        return is_complete;
    }

    public void setIs_complete(String is_complete) {
        this.is_complete = is_complete;
    }

    public List<MessageData> getData() {
        return data;
    }

    public void setData(List<MessageData> data) {
        this.data = data;
    }
}
