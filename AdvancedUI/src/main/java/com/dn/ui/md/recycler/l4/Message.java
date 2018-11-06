package com.dn.ui.md.recycler.l4;

public class Message {
    private int logo;
    private String name;
    private String lastMsg;
    private String time;
    private int pop;

    public Message(int logo, String name, String lastMsg, String time) {
        this.logo = logo;
        this.name = name;
        this.lastMsg = lastMsg;
        this.time = time;
    }

    public Message(int logo, String name, String lastMsg, String time, int pop) {
        this.logo = logo;
        this.name = name;
        this.lastMsg = lastMsg;
        this.time = time;
        this.pop = pop;
    }


    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    @Override
    public String toString() {
        return "Message{" +
                "logo=" + logo +
                ", name='" + name + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                ", time='" + time + '\'' +
                ", pop=" + pop +
                '}';
    }
}
