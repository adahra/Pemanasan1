package com.sebangsa.pemanasan1.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class UserData {
    private static final String[] username = {"Eko", "Dwi", "Tri"};
    private static final String[] name = {"Bagus", "Yahya", "Wahyu"};

    public static List getListUser() {
        List users = new ArrayList<User>();
        for (int i = 0; i < username.length; i++) {
            User u = new User();
            u.setUsername(username[i]);
            u.setName(name[i]);
            users.add(u);
        }

        return users;
    }

}
