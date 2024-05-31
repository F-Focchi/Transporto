package com.example.airquality.data.repository.aqi;

import com.example.airquality.model.AirQualityData;
import com.example.airquality.util.Global;
import com.example.airquality.util.Support;

public class AqiData {

    private static AqiInfo info;
    public static void setAqiData(AirQualityData airQualityData) {
        AqiInfo setInfo = new AqiInfo();
       // setInfo.setCity(Support.setCityToString(airQualityData.getCoord().getLat(), airQualityData.getCoord().getLon(),getApplicationContext()));
        setInfo.setAqi(airQualityData.getList().get(0).getMain().getAqi());
        setInfo.setDescription(Support.setDescriptionToString(airQualityData.getList().get(0).getMain().getAqi()));
        setInfo.setCo(airQualityData.getList().get(0).getComponents().getCo());
        setInfo.setNo2(airQualityData.getList().get(0).getComponents().getNo2());
        setInfo.setO3(airQualityData.getList().get(0).getComponents().getO3());
        setInfo.setSo2(airQualityData.getList().get(0).getComponents().getSo2());
        setInfo.setPm2_5(airQualityData.getList().get(0).getComponents().getPm2_5());
        setInfo.setPm10(airQualityData.getList().get(0).getComponents().getPm10());
        AqiData.setInfo(setInfo);
    }
    public static void setInfo(AqiInfo info) {
        AqiData.info = info;
    }
    public static AqiInfo getInfo() {
        return info;
    }
}
