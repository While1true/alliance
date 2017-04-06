/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bean;

/**
 * Created by S0005 on 2017/3/17.
 */

public class AllianceOrderDetailBean {
    /**
     * pic : http://img02.taobaocdn.com/bao/uploaded/i1/1836855465/TB2vVoIXYJkpuFjy1zcXXa5FFXa_!!1836855465.jpg
     * name : 月子服春全棉春夏纯棉哺乳衣长袖孕妇产后睡衣喂奶外出夏薄套装
     * num : 1
     * real_pay : 68.00
     * id_code_per : 5.00
     * pre_money : 3.4
     * create_order_time : 0000-00-00 00:00:00
     * order_status : 2
     */

    private int alimama_status;
    private int is_check_order;
    private int id;
    private String pic;
    private String name;
    private String num;
    private String real_pay;
    private String id_code_per;
    private double pre_money;
    private String create_order_time;
    private String order_status;
    private String readme;

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public int getIs_check_order() {
        return is_check_order;
    }

    public void setIs_check_order(int is_check_order) {
        this.is_check_order = is_check_order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlimama_status() {
        return alimama_status;
    }

    public void setAlimama_status(int alimama_status) {
        this.alimama_status = alimama_status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getReal_pay() {
        return real_pay;
    }

    public void setReal_pay(String real_pay) {
        this.real_pay = real_pay;
    }

    public String getId_code_per() {
        return id_code_per;
    }

    public void setId_code_per(String id_code_per) {
        this.id_code_per = id_code_per;
    }

    public double getPre_money() {
        return pre_money;
    }

    public void setPre_money(double pre_money) {
        this.pre_money = pre_money;
    }

    public String getCreate_order_time() {
        return create_order_time;
    }

    public void setCreate_order_time(String create_order_time) {
        this.create_order_time = create_order_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
