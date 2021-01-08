package cn.yoyo.ws.model;

import java.util.List;

public class Face {
    private String sn;
    private String Count;
    private List<FaceLogs> logs;

    @Override
    public String toString() {
        return "Face{" +
                "sn='" + sn + '\'' +
                ", Count='" + Count + '\'' +
                ", logs=" + logs +
                '}';
    }

    public Face() {
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public List<FaceLogs> getLogs() {
        return logs;
    }

    public void setLogs(List<FaceLogs> logs) {
        this.logs = logs;
    }
}
