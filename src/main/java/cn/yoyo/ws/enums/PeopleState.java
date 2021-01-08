package cn.yoyo.ws.enums;

public enum PeopleState {
    AllOW(1,"正常状态"),FORBID(0,"禁用状态");

    private int code;
    private String msg;

    PeopleState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static PeopleState getPeopleStateByCode(int code){
        for (PeopleState ps:PeopleState.values()) {
            if (ps.code==code){
                return ps;
            }
        }
        return null;
    }

}
