package cn.yoyo.ws.model;

import java.util.List;

public class MessageData {
    private String user_id;
    private String name;
    private String phone;
    private String id_card;
    private String ic_card;
    private String validity_period;
    private String confidence_level;
    private String photo;
    private String inf_photo;
    private String user_type;
    private List<MessageValidCycle> valid_cycle;

    @Override
    public String toString() {
        return "MessageData{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", id_card='" + id_card + '\'' +
                ", ic_card='" + ic_card + '\'' +
                ", validity_period='" + validity_period + '\'' +
                ", confidence_level='" + confidence_level + '\'' +
                ", photo='" + photo + '\'' +
                ", inf_photo='" + inf_photo + '\'' +
                ", user_type='" + user_type + '\'' +
                ", valid_cycle=" + valid_cycle +
                '}';
    }

    public MessageData() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getIc_card() {
        return ic_card;
    }

    public void setIc_card(String ic_card) {
        this.ic_card = ic_card;
    }

    public String getValidity_period() {
        return validity_period;
    }

    public void setValidity_period(String validity_period) {
        this.validity_period = validity_period;
    }

    public String getConfidence_level() {
        return confidence_level;
    }

    public void setConfidence_level(String confidence_level) {
        this.confidence_level = confidence_level;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInf_photo() {
        return inf_photo;
    }

    public void setInf_photo(String inf_photo) {
        this.inf_photo = inf_photo;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public List<MessageValidCycle> getValid_cycle() {
        return valid_cycle;
    }

    public void setValid_cycle(List<MessageValidCycle> valid_cycle) {
        this.valid_cycle = valid_cycle;
    }
}
