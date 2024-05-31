package com.example.airquality.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AirQualityData {
    @SerializedName("coord")
    private Coord coord;

    @SerializedName("list")
    private List<AirQualityList> list;

    public Coord getCoord() {
        return coord;
    }

    public List<AirQualityList> getList() {
        return list;
    }

    public static class Coord{
        @SerializedName("lon")
        private double lon;
        @SerializedName("lat")
        private double lat;

        public double getLon() {return lon;}

        public double getLat() {return lat;}
    }

    public static class AirQualityList {
        @SerializedName("main")
        private Main main;

        @SerializedName("components")
        private Components components;

        @SerializedName("dt")
        private int dt;

        public Main getMain() {
            return main;
        }

        public Components getComponents() {
            return components;
        }

        public int getDt() {
            return dt;
        }

        public static class Main {
            @SerializedName("aqi")
            private int aqi;

            public int getAqi() {
                return aqi;
            }
        }

        public static class Components{
            @SerializedName("co")
            private String co;
            @SerializedName("no")
            private String no;
            @SerializedName("no2")
            private String no2;
            @SerializedName("o3")
            private String o3;
            @SerializedName("so2")
            private String so2;
            @SerializedName("pm2_5")
            private String pm2_5;
            @SerializedName("pm10")
            private String pm10;
            @SerializedName("nh3")
            private String nh3;

            public String getCo() {
                return co;
            }

            public String getNo() {
                return no;
            }

            public String getNo2() {
                return no2;
            }

            public String getO3() {
                return o3;
            }

            public String getSo2() {
                return so2;
            }

            public String getPm2_5() {
                return pm2_5;
            }

            public String getPm10() {
                return pm10;
            }

            public String getNh3() {
                return nh3;
            }
        }

    }

}
