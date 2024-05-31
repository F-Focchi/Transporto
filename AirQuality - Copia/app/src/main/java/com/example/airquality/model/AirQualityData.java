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
            private float co;
            @SerializedName("no")
            private float no;
            @SerializedName("no2")
            private float no2;
            @SerializedName("o3")
            private float o3;
            @SerializedName("so2")
            private float so2;
            @SerializedName("pm2_5")
            private float pm2_5;
            @SerializedName("pm10")
            private float pm10;
            @SerializedName("nh3")
            private float nh3;

            public float getCo() {
                return co;
            }

            public float getNo() {
                return no;
            }

            public float getNo2() {
                return no2;
            }

            public float getO3() {
                return o3;
            }

            public float getSo2() {
                return so2;
            }

            public float getPm2_5() {
                return pm2_5;
            }

            public float getPm10() {
                return pm10;
            }

            public float getNh3() {
                return nh3;
            }
        }

    }

}
