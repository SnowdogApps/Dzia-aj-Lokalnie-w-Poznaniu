package pl.snowdog.dzialajlokalnie.gcm;

import android.os.Bundle;

/**
 * Created by chomi3 on 2015-07-29.
 */
public class PushNotification {
    String message;
    String title;
    String subtitle;
    String tickerText;
    int vibrate;
    int sound;
    String largeIcon;
    String smallIcon;
    int action;

    public PushNotification(Bundle data) {
        if(data.containsKey("message")) {
            message = data.getString("message");
        }
        if(data.containsKey("title")) {
            title = data.getString("title");
        }
        if(data.containsKey("subtitle")) {
            subtitle = data.getString("subtitle");
        }
        if(data.containsKey("tickerText")) {
            tickerText = data.getString("tickerText");
        }
        if(data.containsKey("vibrate")) {
            vibrate = data.getInt("vibrate");
        }
        if(data.containsKey("sound")) {
            sound = data.getInt("sound");
        }
        if(data.containsKey("largeIcon")) {
            largeIcon = data.getString("largeIcon");
        }
        if(data.containsKey("smallIcon")) {
            smallIcon = data.getString("smallIcon");
        }
        if(data.containsKey("action")) {
            action = data.getInt("action");
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }

    public int getVibrate() {
        return vibrate;
    }

    public void setVibrate(int vibrate) {
        this.vibrate = vibrate;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
