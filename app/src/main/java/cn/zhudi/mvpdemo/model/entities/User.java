package cn.zhudi.mvpdemo.model.entities;

/**
 * 类名称：android_mvp_demo
 * 类描述：
 * 创建人：zhudi
 * 创建时间：2016/11/16 13:19
 * 修改人：${user}
 * 修改时间：${date} ${time}
 * 修改备注：
 */
public class User {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userName) {
        this.userName = userName;
    }
}
