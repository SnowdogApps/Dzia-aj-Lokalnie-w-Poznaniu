package pl.snowdog.dzialajlokalnie.model;

import retrofit.http.Field;

/**
 * Created by bartek on 20.07.15.
 */
public class Login {

    private String username;
    private String pass;
    private int isSessionAuthByAPIkey;

    public Login(String username, String pass, int isSessionAuthByAPIkey) {
        this.username = username;
        this.pass = pass;
        this.isSessionAuthByAPIkey = isSessionAuthByAPIkey;
    }

    @Override
    public String toString() {
        return "Login{" +
                "username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", isSessionAuthByAPIkey=" + isSessionAuthByAPIkey +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getIsSessionAuthByAPIkey() {
        return isSessionAuthByAPIkey;
    }

    public void setIsSessionAuthByAPIkey(int isSessionAuthByAPIkey) {
        this.isSessionAuthByAPIkey = isSessionAuthByAPIkey;
    }
}
