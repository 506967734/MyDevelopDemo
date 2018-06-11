package com.demo.autotest.pages;

import com.demo.autotest.base.PageAppium;

import io.appium.java_client.android.AndroidDriver;

public class IndexPage extends PageAppium {
    public IndexPage(AndroidDriver androidDriver) {
        super(androidDriver);
    }

    //个人中心界面的文本
    private final String PERSON_FLAG_TEXT = "退出应用";
    //个人中心界面的ID
    private final String PERSON_FLAG_ID = "my_slidingmenu_avatar";


    //不是专家首页的ID
    private final String INDEX_FLAG_NOT_EXPERT_ID = "expert_listview";
    //专家首页的ID
    private final String INDEX_FLAG_EXPERT_ID = "expert_article_listview";


    //首页专家搜索页面的ID
    private final String SERVER_FLAG_ID = "search_expert_et";


    //学府页面每一个item的轮播ID
    private final String SCHOOL_FLAG_ID = "view_pager_school_advert_layout";
    //学府页面判断文本
    private final String SCHOOL_FLAG_TEXT = "我的收藏";
    //学府页面tab1点击文本
    public static final String[] SCHOOL_TAB_FLAG_TEXT = {"精卫科普", "在线学习", "前沿资讯", "政策法规"};

    //自查页面的文本
    private final String INSPECTION_FLAG_TEXT = "答非所问";
    //自查页面判断id
    private final String INSPECTION_FLAG_ID = "inspection_search_et";


    //监护页面存在的标识文本
    private final String INTENSIVECARE_ALIVE_TEXT_FLAG = "监护";
    //监护页面判断文本
    private final String INTENSIVECARE_FLAG_TEXT = "填写监护记录";
    //监护页面加载成功文本框的id
    private final String INTENSIVECARE_FLAG_ID = "reg_baseinfo_et";


    //消息中心页面的文本
    private final String NOTICE_FLAG_TEXT = "消息中心";
    //消息中心页面的id
    private final String NOTICE_FLAG_ID = "empty_conversation_tv";


    public String getPERSON_FLAG_ID() {
        return PERSON_FLAG_ID;
    }

    public String[] getSCHOOL_TAB_FLAG_TEXT() {
        return SCHOOL_TAB_FLAG_TEXT;
    }


    /**
     * 是否在个人中心的界面
     *
     * @return
     */
    public boolean isCurrPersonCenter() {
        return isIdElementExist(PERSON_FLAG_ID);
    }


    /**
     * 是否在专家首页的界面
     *
     * @return
     */
    public boolean isCurrExpertMain() {
        return isIdElementExist(INDEX_FLAG_EXPERT_ID);
    }

    /**
     * 是否在非专家首页的界面
     *
     * @return
     */
    public boolean isCurrNotExpertMain() {
        return isIdElementExist(INDEX_FLAG_NOT_EXPERT_ID);
    }


    /**
     * 首页是否在专家搜索页面
     *
     * @return
     */
    public boolean isCurrExpertSearch() {
        return isIdElementExist(SERVER_FLAG_ID);
    }


    /**
     * 首页是否在学府页面
     *
     * @return
     */
    public boolean isCurrSchool() {
        return waitAutoById(SCHOOL_FLAG_ID, 5) != null;
    }


    /**
     * 首页是否在实践页面
     *
     * @return
     */
    public boolean isCurrInspection() {
        return isNameElementExist("最佳临床实践",5);
        //return isIdElementExist(INSPECTION_FLAG_ID);
    }

    public boolean isTextExist(){
        return isXpathExist("//android.widget.TextView[@text='最佳临床实践']");
    }

    public boolean clickListView(String fatherClassName, int fatherNum, String childrenClassName,int clickPosition){
        return clickListElementByClassName(fatherClassName,fatherNum,childrenClassName,clickPosition);
    }


    /**
     * 首页是否在监护页面
     *
     * @return
     */
    public boolean isCurrIntentcare() {
        return isIdElementExist(INTENSIVECARE_FLAG_ID);
    }


    /**
     * 首页是否在监护页面
     *
     * @return
     */
    public boolean isExistIntentcare() {
        return isTextExist(INTENSIVECARE_ALIVE_TEXT_FLAG);
    }


    /**
     * 首页是否在消息中心页面
     *
     * @return
     */
    public boolean isCurrNotice() {
        return isIdElementExist(NOTICE_FLAG_ID);
    }


    public String getPERSON_FLAG_TEXT() {
        return PERSON_FLAG_TEXT;
    }


    public String getSERVER_FLAG_ID() {
        return SERVER_FLAG_ID;
    }

    public String getSCHOOL_FLAG_ID() {
        return SCHOOL_FLAG_ID;
    }


    public String getNOTICE_FLAG_TEXT() {
        return NOTICE_FLAG_TEXT;
    }

    public String getINTENSIVECARE_ALIVE_TEXT_FLAG() {
        return INTENSIVECARE_ALIVE_TEXT_FLAG;
    }

    public String getINDEX_FLAG_NOT_EXPERT_ID() {
        return INDEX_FLAG_NOT_EXPERT_ID;
    }

    public String getINDEX_FLAG_EXPERT_ID() {
        return INDEX_FLAG_EXPERT_ID;
    }

    public String getSCHOOL_FLAG_TEXT() {
        return SCHOOL_FLAG_TEXT;
    }

    public String getINSPECTION_FLAG_TEXT() {
        return INSPECTION_FLAG_TEXT;
    }

    public String getINSPECTION_FLAG_ID() {
        return INSPECTION_FLAG_ID;
    }

    public String getINTENSIVECARE_FLAG_TEXT() {
        return INTENSIVECARE_FLAG_TEXT;
    }

    public String getINTENSIVECARE_FLAG_ID() {
        return INTENSIVECARE_FLAG_ID;
    }

    public String getNOTICE_FLAG_ID() {
        return NOTICE_FLAG_ID;
    }
}
