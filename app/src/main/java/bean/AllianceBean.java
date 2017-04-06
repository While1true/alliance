/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bean;

import java.util.List;

/**
 * Created by S0005 on 2017/3/6.
 */

public class AllianceBean {
    /**
     * id_code : 100718
     * id_code_level : 0
     * id_code_per : 0
     * wait_money : 0
     * notice : [{"id":"7","title":"新年红利分享","content":"内容。。。。","addtime":"2017-03-04 17:05:47","news_type":"1"},{"id":"6","title":"关于加大推广佣金比例的通知","content":"通知内容。。。。","addtime":"2017-03-04 17:05:16","news_type":"1"}]
     * rule : [{"id":"9","title":"禁止违规推广","content":"禁止违规推广禁止违规推广禁止违规推广禁止违规推广","addtime":"2017-03-04 17:07:10","news_type":"2"},{"id":"8","title":"赛百姿联盟基本规则","content":"规则。。。","addtime":"2017-03-04 17:06:45","news_type":"2"}]
     * today_click : 0
     * yesterday_click : 0
     * this_month : 0
     * last_month : 0
     * today_onum : 0
     * yesterday_onum : 0
     * this_month_onum : 0
     * last_month_onum : 0
     * today_device : 0
     * yesterday_device : 0
     * this_month_device : 0
     * last_month_device : 0
     */

    private String id_code;
    private String id_code_level;
    private int id_code_per;
    private String wait_money;
    private int today_click;
    private int yesterday_click;
    private int this_month;
    private int last_month;
    private String today_onum;
    private String yesterday_onum;
    private String this_month_onum;
    private String last_month_onum;
    private String today_device;
    private String yesterday_device;
    private String this_month_device;
    private String last_month_device;
    /**
     * id : 7
     * title : 新年红利分享
     * content : 内容。。。。
     * addtime : 2017-03-04 17:05:47
     * news_type : 1
     */

    private List<NoticeBean> notice;
    /**
     * id : 9
     * title : 禁止违规推广
     * content : 禁止违规推广禁止违规推广禁止违规推广禁止违规推广
     * addtime : 2017-03-04 17:07:10
     * news_type : 2
     */

    private List<RuleBean> rule;

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getId_code_level() {
        return id_code_level;
    }

    public void setId_code_level(String id_code_level) {
        this.id_code_level = id_code_level;
    }

    public int getId_code_per() {
        return id_code_per;
    }

    public void setId_code_per(int id_code_per) {
        this.id_code_per = id_code_per;
    }

    public String getWait_money() {
        return wait_money;
    }

    public void setWait_money(String wait_money) {
        this.wait_money = wait_money;
    }

    public int getToday_click() {
        return today_click;
    }

    public void setToday_click(int today_click) {
        this.today_click = today_click;
    }

    public int getYesterday_click() {
        return yesterday_click;
    }

    public void setYesterday_click(int yesterday_click) {
        this.yesterday_click = yesterday_click;
    }

    public int getThis_month() {
        return this_month;
    }

    public void setThis_month(int this_month) {
        this.this_month = this_month;
    }

    public int getLast_month() {
        return last_month;
    }

    public void setLast_month(int last_month) {
        this.last_month = last_month;
    }

    public String getToday_onum() {
        return today_onum;
    }

    public void setToday_onum(String today_onum) {
        this.today_onum = today_onum;
    }

    public String getYesterday_onum() {
        return yesterday_onum;
    }

    public void setYesterday_onum(String yesterday_onum) {
        this.yesterday_onum = yesterday_onum;
    }

    public String getThis_month_onum() {
        return this_month_onum;
    }

    public void setThis_month_onum(String this_month_onum) {
        this.this_month_onum = this_month_onum;
    }

    public String getLast_month_onum() {
        return last_month_onum;
    }

    public void setLast_month_onum(String last_month_onum) {
        this.last_month_onum = last_month_onum;
    }

    public String getToday_device() {
        return today_device;
    }

    public void setToday_device(String today_device) {
        this.today_device = today_device;
    }

    public String getYesterday_device() {
        return yesterday_device;
    }

    public void setYesterday_device(String yesterday_device) {
        this.yesterday_device = yesterday_device;
    }

    public String getThis_month_device() {
        return this_month_device;
    }

    public void setThis_month_device(String this_month_device) {
        this.this_month_device = this_month_device;
    }

    public String getLast_month_device() {
        return last_month_device;
    }

    public void setLast_month_device(String last_month_device) {
        this.last_month_device = last_month_device;
    }

    public List<NoticeBean> getNotice() {
        return notice;
    }

    public void setNotice(List<NoticeBean> notice) {
        this.notice = notice;
    }

    public List<RuleBean> getRule() {
        return rule;
    }

    public void setRule(List<RuleBean> rule) {
        this.rule = rule;
    }

    public static class NoticeBean {
        private String id;
        private String title;
        private String content;
        private String addtime;
        private String news_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getNews_type() {
            return news_type;
        }

        public void setNews_type(String news_type) {
            this.news_type = news_type;
        }
    }

    public static class RuleBean {
        private String id;
        private String title;
        private String content;
        private String addtime;
        private String news_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getNews_type() {
            return news_type;
        }

        public void setNews_type(String news_type) {
            this.news_type = news_type;
        }

        @Override
        public String toString() {
            return "RuleBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", news_type='" + news_type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AllianceBean{" +
                "id_code='" + id_code + '\'' +
                ", id_code_level='" + id_code_level + '\'' +
                ", id_code_per=" + id_code_per +
                ", wait_money=" + wait_money +
                ", today_click=" + today_click +
                ", yesterday_click=" + yesterday_click +
                ", this_month=" + this_month +
                ", last_month=" + last_month +
                ", today_onum='" + today_onum + '\'' +
                ", yesterday_onum='" + yesterday_onum + '\'' +
                ", this_month_onum='" + this_month_onum + '\'' +
                ", last_month_onum='" + last_month_onum + '\'' +
                ", today_device='" + today_device + '\'' +
                ", yesterday_device='" + yesterday_device + '\'' +
                ", this_month_device='" + this_month_device + '\'' +
                ", last_month_device='" + last_month_device + '\'' +
                ", notice=" + notice +
                ", rule=" + rule +
                '}';
    }
}
