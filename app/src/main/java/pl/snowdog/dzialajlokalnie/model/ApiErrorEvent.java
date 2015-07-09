package pl.snowdog.dzialajlokalnie.model;

/**
 * Created by bartek on 09.07.15.
 */
public class ApiErrorEvent {

    private int status;
    private String reason;
    private String url;

    public ApiErrorEvent(int status, String reason, String url) {
        this.status = status;
        this.reason = reason;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ApiErrorEvent{" +
                "status=" + status +
                ", reason='" + reason + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
