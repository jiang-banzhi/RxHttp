package com.banzhi.rxhttpsample;

import com.google.gson.Gson;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/8/22.
 * @desciption :
 * @version :
 * </pre>
 */

public class LoginRequest {


    /**
     * UserName : string
     * PassWord : string
     */

    private String UserName;
    private String PassWord;

    public static LoginRequest objectFromData(String str) {

        return new Gson().fromJson(str, LoginRequest.class);
    }

    public LoginRequest(String userName, String passWord) {
        UserName = userName;
        PassWord = passWord;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String PassWord) {
        this.PassWord = PassWord;
    }
}
