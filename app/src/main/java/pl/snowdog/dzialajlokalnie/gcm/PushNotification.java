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
    int objectID;

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
            try {
                vibrate = Integer.parseInt(data.getString("vibrate"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(data.containsKey("sound")) {
            try {
                sound = Integer.parseInt(data.getString("sound"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(data.containsKey("largeIcon")) {
            largeIcon = data.getString("largeIcon");
        }
        if(data.containsKey("smallIcon")) {
            smallIcon = data.getString("smallIcon");
        }
        if(data.containsKey("action")) {
            try{
                action = Integer.parseInt(data.getString("action"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if(data.containsKey("objectID")) {
            try {
                objectID = Integer.parseInt(data.getString("objectID"));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
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

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    @Override
    public String toString() {
        return "PushNotification{" +
                "message='" + message + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", tickerText='" + tickerText + '\'' +
                ", vibrate=" + vibrate +
                ", sound=" + sound +
                ", largeIcon='" + largeIcon + '\'' +
                ", smallIcon='" + smallIcon + '\'' +
                ", action=" + action +
                ", objectID=" + objectID +
                '}';
    }
}
