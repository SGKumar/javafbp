package com.jpaulmorrison.fbp.test;

public class FlightDelayRecord {
  String year;
  String month;
  String dayOfMonth;
  String uniqueCarrier;
  String flightNum;
  String arrDelayMins;

  @Override
  public String toString() {
    //System.out.format("%s/%s/%s - %s %s - %s%n", year, month, dayOfMonth, uniqueCarrier, flightNum, arrDelayMins);
    return String.format("%s/%s/%s - %s %s - %s%n", year, month, dayOfMonth, uniqueCarrier, flightNum, arrDelayMins);
  }
}
