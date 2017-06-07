package dh.data.model;

import java.util.Date;

/**
 * Created by MT-T450 on 2017/6/7.
 * 结果数据（只有上限）
 */
public class Outcome {
    private Integer id;
    private Date time;
    private Integer raltc;
    private Integer qnh;
    private Integer height;
    private Integer flightId;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getRaltc() {
        return raltc;
    }

    public void setRaltc(Integer raltc) {
        this.raltc = raltc;
    }

    public Integer getQnh() {
        return qnh;
    }

    public void setQnh(Integer qnh) {
        this.qnh = qnh;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
