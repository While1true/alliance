package bean;

import java.io.Serializable;

/**
 * Created by S0005 on 2017/3/30.
 */

public class UserBean implements  Serializable{

    /**
     * userid : 66
     * token : ZDE2YzJhNWM2MmRjMmNkNDgzYTQ3MjExOGY1MTdiM2Q=
     */

    private String userid;
    private String token;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userid='" + userid + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
