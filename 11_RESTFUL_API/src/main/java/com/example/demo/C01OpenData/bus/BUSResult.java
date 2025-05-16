package com.example.demo.C01OpenData.bus;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BUSResult")
public class BUSResult {

    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {

        @XmlElement(name = "success")
        private boolean success;

        @XmlElement(name = "resultCode")
        private String resultCode;

        @XmlElement(name = "resultMsg")
        private String resultMsg;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {

        @XmlElement(name = "items")
        private Items items;

        @XmlElement(name = "totalCount")
        private int totalCount;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Items {

        @XmlElement(name = "routeNo")
        private String routeNo;

        @XmlElementWrapper(name = "arrList")
        @XmlElement(name = "arrList")
        private List<ArrList> arrList;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ArrList {

        @XmlElement(name = "routeId")
        private String routeId;

        @XmlElement(name = "routeNo")
        private String routeNo;

        @XmlElement(name = "moveDir")
        private int moveDir;

        @XmlElement(name = "bsGap")
        private int bsGap;

        @XmlElement(name = "bsNm")
        private String bsNm;

        @XmlElement(name = "vhcNo2")
        private String vhcNo2;

        @XmlElement(name = "busTCd2")
        private String busTCd2;

        @XmlElement(name = "busTCd3")
        private String busTCd3;

        @XmlElement(name = "busAreaCd")
        private String busAreaCd;

        @XmlElement(name = "arrState")
        private String arrState;

        @XmlElement(name = "prevBsGap")
        private int prevBsGap;
    }
}
