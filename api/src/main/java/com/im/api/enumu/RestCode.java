package com.im.api.enumu;

/**
 * response code
 * Created by BlueT on 2018/11.
 */
public enum RestCode {

    SUCCESS("100000"),PARAMMENTSERROR("100001"), SESSIONTIMEOUT("100002"),NOTUSERINFO("100003"),SYSTEMERROR("100004"),用户存在("100005");

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    RestCode(String action) {
        this.action = action;
    }
}
