package cn.yoyo.ws.model;

public class ReturnMsg {
    private Integer Result;
    private String Content;
    private String Msg;

    @Override
    public String toString() {
        return "ReturnMsg{" +
                "Result=" + Result +
                ", Content='" + Content + '\'' +
                ", Msg='" + Msg + '\'' +
                '}';
    }

    public ReturnMsg(Integer result, String content, String msg) {
        Result = result;
        Content = content;
        Msg = msg;
    }

    public ReturnMsg() {
    }

    public Integer getResult() {
        return Result;
    }

    public void setResult(Integer result) {
        Result = result;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
