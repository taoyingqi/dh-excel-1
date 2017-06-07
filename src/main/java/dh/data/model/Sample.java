package dh.data.model;

import java.util.Date;

/**
 * Created by MT-T450 on 2017/6/7.
 * 采样
 */
public class Sample {
    private Date start;
    private Date end;
    private Integer downRate;
    private Integer durationSec;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getDownRate() {
        return downRate;
    }

    public void setDownRate(Integer downRate) {
        this.downRate = downRate;
    }

    public Integer getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(Integer durationSec) {
        this.durationSec = durationSec;
    }
}
