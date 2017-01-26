package yss.com.myrongappication.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Table;


public class FriendsListEntity  extends SugarRecord {
    private String userId;
    private String userName;
    private String nickName;
    private String sex;
    private String phone;
    private String portrait;
    private String state;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
        public FriendsListEntity() {
    }

    public FriendsListEntity(String userId, String userName, String nickName, String sex, String phone, String portrait, String state) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.sex = sex;
        this.phone = phone;
        this.portrait = portrait;
        this.state = state;
    }
}