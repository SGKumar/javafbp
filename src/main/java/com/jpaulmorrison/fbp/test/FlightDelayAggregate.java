package com.jpaulmorrison.fbp.test;

public class FlightDelayAggregate {
  String uniqueCarrier;
  long arrDelayMins;
  long numFlights;

  public FlightDelayAggregate() {
    arrDelayMins = 0;
    numFlights = 0;
    System.out.println("no-op");
  }
  public FlightDelayAggregate(String carrier, long delay) {
    uniqueCarrier = carrier;
    arrDelayMins = delay;
    numFlights = 1;
  }
  public void addDelay(long delay) {
    arrDelayMins += delay;
    numFlights++;
  }
  @Override
  public String toString() {
    return String.format("Delays for carrier %s: %d average mins, %d delayed flights", uniqueCarrier, (arrDelayMins/numFlights), numFlights);
  }
}
