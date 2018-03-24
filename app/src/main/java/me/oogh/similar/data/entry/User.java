package me.oogh.similar.data.entry;

import cn.bmob.v3.BmobUser;

/**
 * Created by oogh on 18-3-17.
 */

public class User extends BmobUser {

    public User() {
        super();
    }

    public User(String username, String password) {
        super.setUsername(username);
        super.setPassword(password);
    }


}
