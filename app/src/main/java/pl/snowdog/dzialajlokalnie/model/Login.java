package pl.snowdog.dzialajlokalnie.model;

/**
 * Created by bartek on 20.07.15.
 */
public class Login {

    private String email;
    private String pass;
    private int isSessionAuthByAPIkey;

    public Login(String email, String pass, int isSessionAuthByAPIkey) {
        this.email = email;
        this.pass = pass;
        this.isSessionAuthByAPIkey = isSessionAuthByAPIkey;
    }

    @Override
    public String toString() {
        return "Login{" +
                "email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", isSessionAuthByAPIkey=" + isSessionAuthByAPIkey +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public class Facebook {
        private String email;
        private String accessToken;
        private User user;
        private Session session;

        public Facebook(String email, String accessToken) {
            this.email = email;
            this.accessToken = accessToken;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        @Override
        public String toString() {
            return "Facebook{" +
                    "email='" + email + '\'' +
                    ", accessToken='" + accessToken + '\'' +
                    ", user=" + user +
                    ", session=" + session +
                    '}';
        }
    }
}
