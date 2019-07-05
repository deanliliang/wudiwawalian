package com.leyou.threadlocals;

import com.leyou.auth.bean.UserInfo;

/**
 * @author 虎哥
 */
public class UserHolder {
    private static final ThreadLocal<UserInfo> TL = new ThreadLocal<>();

    public static void setUser(UserInfo user) {
        TL.set(user);
    }

    public static UserInfo getUser() {
        return TL.get();
    }

    public static void remove() {
        TL.remove();
    }
}