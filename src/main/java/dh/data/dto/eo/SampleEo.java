package dh.data.dto.eo;

/**
 * Created by MT-T450 on 2017/6/7.
 * 采样 Eo
 */
public class SampleEo {
    private String startTime; //起始时刻
    private String endTime; //截止时刻
    private String downRate; //下降率
    private String durationSec; //持续毫秒

    public SampleEo() {
    }

    public SampleEo(String startTime, String endTime, String downRate, String durationSec) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.downRate = downRate;
        this.durationSec = durationSec;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDownRate() {
        return downRate;
    }

    public void setDownRate(String downRate) {
        this.downRate = downRate;
    }

    public String getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(String durationSec) {
        this.durationSec = durationSec;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", downRate=" + downRate +
                ", durationSec=" + durationSec +
                '}';
    }
}
