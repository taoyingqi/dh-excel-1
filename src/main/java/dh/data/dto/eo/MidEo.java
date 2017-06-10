package dh.data.dto.eo;

import dh.data.common.dto.BaseEo;
import dh.data.model.Mid;
import dh.data.util.MidUtil;
import dh.data.util.NumericUtil;

import static dh.data.util.NumericUtil.toStr;
import static dh.data.util.TimeUtil.TIME_MILLIS_TYPE;
import static dh.data.util.TimeUtil.formatDate;

/**
 * Created by MT-T450 on 2017/6/7.
 * 航班中间表
 */
public class MidEo extends BaseEo<Mid> {
    private String flightId;
    private FH wxdFh;
    private FH qnhFh;
    private FH heightFh;
    private String wxdCond;
    private String qnhCond;
    private String heightCond;
    private String multiCond;
    private String durationSec;

    //航班高度
    public static class FH {
        private String time;
        private String height;
        private SampleEo sample1;
        private SampleEo sample2;

        public FH() {
        }

        public FH(String time, String height, SampleEo sample1, SampleEo sample2) {
            this.time = time;
            this.height = height;
            this.sample1 = sample1;
            this.sample2 = sample2;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public SampleEo getSample1() {
            return sample1;
        }

        public void setSample1(SampleEo sample1) {
            this.sample1 = sample1;
        }

        public SampleEo getSample2() {
            return sample2;
        }

        public void setSample2(SampleEo sample2) {
            this.sample2 = sample2;
        }
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public FH getWxdFh() {
        return wxdFh;
    }

    public void setWxdFh(FH wxdFh) {
        this.wxdFh = wxdFh;
    }

    public FH getQnhFh() {
        return qnhFh;
    }

    public void setQnhFh(FH qnhFh) {
        this.qnhFh = qnhFh;
    }

    public FH getHeightFh() {
        return heightFh;
    }

    public void setHeightFh(FH heightFh) {
        this.heightFh = heightFh;
    }

    public String getWxdCond() {
        return wxdCond;
    }

    public void setWxdCond(String wxdCond) {
        this.wxdCond = wxdCond;
    }

    public String getQnhCond() {
        return qnhCond;
    }

    public void setQnhCond(String qnhCond) {
        this.qnhCond = qnhCond;
    }

    public String getHeightCond() {
        return heightCond;
    }

    public void setHeightCond(String heightCond) {
        this.heightCond = heightCond;
    }

    public String getMultiCond() {
        return multiCond;
    }

    public void setMultiCond(String multiCond) {
        this.multiCond = multiCond;
    }

    public String getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(String durationSec) {
        this.durationSec = durationSec;
    }

    @Override
    public String toString() {
        return "MidEo{" +
                "flightId='" + flightId + '\'' +
                ", wxdFh=" + wxdFh +
                ", qnhFh=" + qnhFh +
                ", heightFh=" + heightFh +
                ", wxdCond='" + wxdCond + '\'' +
                ", qnhCond='" + qnhCond + '\'' +
                ", heightCond='" + heightCond + '\'' +
                ", multiCond='" + multiCond + '\'' +
                ", durationSec='" + durationSec + '\'' +
                '}';
    }

    public MidEo() {
    }

    public MidEo(Mid model) {
        fromModel(model);
    }


    @Override
    protected void processBean(Mid mid) {
        this.setFlightId(toStr(mid.getFlightId()));

        // 无线电高度口径
        this.setWxdFh(new FH(formatDate(mid.getWxdFh().getTime(), TIME_MILLIS_TYPE),
                toStr(MidUtil.devWxdFactor(mid.getWxdFh().getHeight())),
                new SampleEo(formatDate(mid.getWxdFh().getSample1().getStartTime(), TIME_MILLIS_TYPE),
                        formatDate(mid.getWxdFh().getSample1().getEndTime(), TIME_MILLIS_TYPE),
                        NumericUtil.toDecimal(MidUtil.devWxdFactor(mid.getWxdFh().getSample1().getDownRate()),0),
                        null),
                new SampleEo(formatDate(mid.getWxdFh().getSample2().getStartTime(), TIME_MILLIS_TYPE),
                        formatDate(mid.getWxdFh().getSample2().getEndTime(), TIME_MILLIS_TYPE),
                        NumericUtil.toDecimal(MidUtil.devWxdFactor(mid.getWxdFh().getSample2().getDownRate()), 0),
                        null)));
        // QNH高度口径
        this.setQnhFh(new FH(formatDate(mid.getQnhFh().getTime(), TIME_MILLIS_TYPE),
                toStr(mid.getQnhFh().getHeight()),
                new SampleEo(formatDate(mid.getQnhFh().getSample1().getStartTime(), TIME_MILLIS_TYPE),
                        formatDate(mid.getQnhFh().getSample1().getEndTime(), TIME_MILLIS_TYPE),
                        toStr(mid.getQnhFh().getSample1().getDownRate()),
                        null),
                new SampleEo(formatDate(mid.getQnhFh().getSample2().getStartTime(), TIME_MILLIS_TYPE),
                        formatDate(mid.getQnhFh().getSample2().getEndTime(), TIME_MILLIS_TYPE),
                        toStr(mid.getQnhFh().getSample2().getDownRate()),
                        null)));
        // Height高度口径
        this.setHeightFh(new FH(formatDate(mid.getHeightFh().getTime(), TIME_MILLIS_TYPE),
                toStr(mid.getHeightFh().getHeight()),
                new SampleEo(formatDate(mid.getHeightFh().getSample1().getStartTime(), TIME_MILLIS_TYPE),
                        formatDate(mid.getHeightFh().getSample1().getEndTime(), TIME_MILLIS_TYPE),
                        toStr(mid.getHeightFh().getSample1().getDownRate()),
                        null),
                new SampleEo(formatDate(mid.getHeightFh().getSample2().getStartTime(), TIME_MILLIS_TYPE),
                        formatDate(mid.getHeightFh().getSample2().getEndTime(), TIME_MILLIS_TYPE),
                        toStr(mid.getHeightFh().getSample2().getDownRate()),
                        null)));
        this.setWxdCond(toStr(mid.getWxdCond()));
        this.setQnhCond(toStr(mid.getQnhCond()));
        this.setHeightCond(toStr(mid.getHeightCond()));
        this.setMultiCond(toStr(mid.getMultiCond()));
        this.setDurationSec(toStr(mid.getDurationSec()));
    }

}
