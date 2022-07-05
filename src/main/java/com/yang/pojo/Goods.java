package com.yang.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author:Yang
 */
public class Goods implements Serializable {
    private Integer gid;
    private String gname;
    private BigDecimal gprice;
    private Integer gnum;

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public BigDecimal getGprice() {
        return gprice;
    }

    public void setGprice(BigDecimal gprice) {
        this.gprice = gprice;
    }

    public Integer getGnum() {
        return gnum;
    }

    public void setGnum(Integer gnum) {
        this.gnum = gnum;
    }

    @Override
    public String toString() {
        return gid +","+gname +","+ gnum+","+ gprice ;
    }
}
