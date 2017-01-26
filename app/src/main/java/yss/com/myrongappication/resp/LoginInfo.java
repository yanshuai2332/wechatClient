package yss.com.myrongappication.resp;


public class LoginInfo {

    /**
     * token : 5n10pM7eGADaVa+wRfSL8yeetfphO7zoE3iUt3Il63Fzgphxz7RsFikjymSwlDoWacyGI3w3ZmeKtS3byr3NTg==
     * loginInfo : {"id":"ea9c0d68-abc9-11e6-8424-408d5c7878fd","userName":"admin01","password":"123456","nickName":"测试1","sex":"0","phone":"201601"}
     */

    private String token;
    /**
     * id : ea9c0d68-abc9-11e6-8424-408d5c7878fd
     * userName : admin01
     * password : 123456
     * nickName : 测试1
     * sex : 0
     * phone : 201601
     */

    private LoginInfoBean loginInfo;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginInfoBean getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfoBean loginInfo) {
        this.loginInfo = loginInfo;
    }

    public static class LoginInfoBean {
        private String id;
        private String userName;
        private String password;
        private String nickName;
        private String sex;
        private String phone;
        private String portrait;

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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
    }
}
