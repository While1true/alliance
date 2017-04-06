package Constance;

/**
 * Created by S0005 on 2017/3/30.
 */

public class NetUrl {
    public static final String Domain = "http:";
    public static final String Host = "http://www.saibaizi.com";
    //公用开头接口
    public static final String BaseUrl = "https://api.saibaizi.com/index.php?mod=";
    //开始获取验证码
    public static final String START_CODE="https://api.saibaizi.com/index.php?mod=php_yzm_login&mobile=";
    //登录
    public static final String LOGIN=BaseUrl+"v230_bestshow_login";
    //明细
    public static final String MONEY_DETAIL=BaseUrl+"v230_bestshow_count";
    //状态信息
    public static final String STATE=BaseUrl+"v230_bestshow_count2";
    //联盟订单明细
    public static final String ALLIANCE_DETAIL=BaseUrl+"v230_bestshow_order";
    //赛百姿联盟主接口
    public static final String MEMBER_SBZ_ALL_1=BaseUrl+"member_sbz_main_1";
    public static final String MEMBER_SBZ_ALL_2=BaseUrl+"member_sbz_main_2";
    //赛手提现
    public static final String ALLIANCE_TX=BaseUrl+"v211_saishoumoney";

}
