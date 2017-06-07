package dh.data.model;

import java.util.Date;

/**
 * Created by MT-T450 on 2017/6/7.
 * 航班最终表
 */
public class Ultimate {
    private Integer flightId;
    private Integer down500n;
    private Date last1Down500Time;
    private Integer down0n;
    private Date last1Down0Time;
    private Date durationTime;
    private Sample wxdMdc; //无线电高度最大下降率
    private Sample qnhMdc; //QNH高度最大下降率
    private Sample heightMdc; //Height高度最大下降率
    private Integer downRateGt500n; //下降率超过500英尺每分钟-次数
    private Sample downRateGt500Ld; //下降率超过500英尺每分钟-最长一次持续毫秒

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Integer getDown500n() {
        return down500n;
    }

    public void setDown500n(Integer down500n) {
        this.down500n = down500n;
    }

    public Date getLast1Down500Time() {
        return last1Down500Time;
    }

    public void setLast1Down500Time(Date last1Down500Time) {
        this.last1Down500Time = last1Down500Time;
    }

    public Integer getDown0n() {
        return down0n;
    }

    public void setDown0n(Integer down0n) {
        this.down0n = down0n;
    }

    public Date getLast1Down0Time() {
        return last1Down0Time;
    }

    public void setLast1Down0Time(Date last1Down0Time) {
        this.last1Down0Time = last1Down0Time;
    }

    public Date getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Date durationTime) {
        this.durationTime = durationTime;
    }

    public Sample getWxdMdc() {
        return wxdMdc;
    }

    public void setWxdMdc(Sample wxdMdc) {
        this.wxdMdc = wxdMdc;
    }

    public Sample getQnhMdc() {
        return qnhMdc;
    }

    public void setQnhMdc(Sample qnhMdc) {
        this.qnhMdc = qnhMdc;
    }

    public Sample getHeightMdc() {
        return heightMdc;
    }

    public void setHeightMdc(Sample heightMdc) {
        this.heightMdc = heightMdc;
    }

    public Integer getDownRateGt500n() {
        return downRateGt500n;
    }

    public void setDownRateGt500n(Integer downRateGt500n) {
        this.downRateGt500n = downRateGt500n;
    }

    public Sample getDownRateGt500Ld() {
        return downRateGt500Ld;
    }

    public void setDownRateGt500Ld(Sample downRateGt500Ld) {
        this.downRateGt500Ld = downRateGt500Ld;
    }
}