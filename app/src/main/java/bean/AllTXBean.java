/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bean;

/**
 * Created by S0005 on 2017/3/20.
 */

public class AllTXBean {
    /**
     * id : 1
     * uid : 10
     * money : 100.00
     * status : 3
     * count_time : 2017-03-02 00:00:00
     * apply_time : 0000-00-00 00:00:00
     * end_time : 0000-00-00 00:00:00
     * u_mobile : 965413241
     * u_cardtype : 2
     * u_cardnumber : 123456798
     * u_realname : 雷升庆
     */

    private String id;
    private String uid;
    private String money;
    private String status;
    private String count_time;
    private String apply_time;
    private String end_time;
    private String u_mobile;
    private String u_cardtype;
    private String u_cardnumber;
    private String u_realname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount_time() {
        return count_time;
    }

    public void setCount_time(String count_time) {
        this.count_time = count_time;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getU_mobile() {
        return u_mobile;
    }

    public void setU_mobile(String u_mobile) {
        this.u_mobile = u_mobile;
    }

    public String getU_cardtype() {
        return u_cardtype;
    }

    public void setU_cardtype(String u_cardtype) {
        this.u_cardtype = u_cardtype;
    }

    public String getU_cardnumber() {
        return u_cardnumber;
    }

    public void setU_cardnumber(String u_cardnumber) {
        this.u_cardnumber = u_cardnumber;
    }

    public String getU_realname() {
        return u_realname;
    }

    public void setU_realname(String u_realname) {
        this.u_realname = u_realname;
    }
}
