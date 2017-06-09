package dh.data.dto.eo;

import dh.data.common.dto.BaseEo;
import dh.data.model.Ultimate;

/**
 * Created by MT-T450 on 2017/6/7.
 * 航班最终表
 */
public class UltimateEo extends BaseEo<Ultimate> {
    private String flightId;
    private String down500n;
    private String last1Down500Time;
    private String down0n;
    private String first1Down0Time;
    private String durationTime;
    private SampleEo wxdMdc; //无线电高度最大下降率
    private SampleEo qnhMdc; //QNH高度最大下降率
    private SampleEo heightMdc; //Height高度最大下降率
    private String downRateGt500n; //下降率超过500英尺每分钟-次数
    private SampleEo downRateGt500Ld; //下降率超过500英尺每分钟-最长一次持续毫秒

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getDown500n() {
        return down500n;
    }

    public void setDown500n(String down500n) {
        this.down500n = down500n;
    }

    public String getLast1Down500Time() {
        return last1Down500Time;
    }

    public void setLast1Down500Time(String last1Down500Time) {
        this.last1Down500Time = last1Down500Time;
    }

    public String getDown0n() {
        return down0n;
    }

    public void setDown0n(String down0n) {
        this.down0n = down0n;
    }

    public String getFirst1Down0Time() {
        return first1Down0Time;
    }

    public void setFirst1Down0Time(String first1Down0Time) {
        this.first1Down0Time = first1Down0Time;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public SampleEo getWxdMdc() {
        return wxdMdc;
    }

    public void setWxdMdc(SampleEo wxdMdc) {
        this.wxdMdc = wxdMdc;
    }

    public SampleEo getQnhMdc() {
        return qnhMdc;
    }

    public void setQnhMdc(SampleEo qnhMdc) {
        this.qnhMdc = qnhMdc;
    }

    public SampleEo getHeightMdc() {
        return heightMdc;
    }

    public void setHeightMdc(SampleEo heightMdc) {
        this.heightMdc = heightMdc;
    }

    public String getDownRateGt500n() {
        return downRateGt500n;
    }

    public void setDownRateGt500n(String downRateGt500n) {
        this.downRateGt500n = downRateGt500n;
    }

    public SampleEo getDownRateGt500Ld() {
        return downRateGt500Ld;
    }

    public void setDownRateGt500Ld(SampleEo downRateGt500Ld) {
        this.downRateGt500Ld = downRateGt500Ld;
    }

    @Override
    public String toString() {
        return "Ultimate{" +
                "flightId=" + flightId +
                ", down500n=" + down500n +
                ", last1Down500Time=" + last1Down500Time +
                ", down0n=" + down0n +
                ", first1Down0Time=" + first1Down0Time +
                ", durationTime=" + durationTime +
                ", wxdMdc=" + wxdMdc +
                ", qnhMdc=" + qnhMdc +
                ", heightMdc=" + heightMdc +
                ", downRateGt500n=" + downRateGt500n +
                ", downRateGt500Ld=" + downRateGt500Ld +
                '}';
    }

    @Override
    protected void processBean(Ultimate paramT) {

    }
}