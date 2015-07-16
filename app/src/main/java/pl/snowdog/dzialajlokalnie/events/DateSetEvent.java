package pl.snowdog.dzialajlokalnie.events;

import java.util.Date;

/**
 * Created by chomi3 on 14.07.15.
 */
public class DateSetEvent {

    private Date startDate;
    private Date endDate;

    public DateSetEvent(Date dateFrom, Date dateTo) {
        this.startDate = dateFrom;
        this.endDate = dateTo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "DateSetEvent{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
