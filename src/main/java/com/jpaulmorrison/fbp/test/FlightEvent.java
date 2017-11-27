package com.jpaulmorrison.fbp.test;

public class FlightEvent {

  public FlightEvent() {
    System.out.println("no arg constructor does nothing");
  }
  public FlightEvent(String[] details) {
    for(int i = 0; i < details.length; i++) {
      switch(i) {
        case 0:
          this.year = details[i]; break;
        case 1:
          this.month = details[i]; break;
        case 2:
          this.dayOfMonth = details[i]; break;
        case 3:
          this.dayOfWeek = details[i]; break;
        case 4:
          this.depTime = details[i]; break;
        case 5:
          this.scheduledDepTime = details[i]; break;
        case 6:
          this.arrTime = details[i]; break;
        case 7:
          this.scheduledArrTime = details[i]; break;
        case 8:
          this.uniqueCarrier = details[i]; break;
        case 9:
          this.flightNum = details[i]; break;
        case 10:
          this.tailNum = details[i]; break;
        case 11:
          this.actualElapsedMins = details[i]; break;
        case 12:
          this.crsElapsedMins = details[i]; break;
        case 13:
          this.airMins = details[i]; break;
        case 14:
          this.arrDelayMins = details[i]; break;
        case 15:
          this.depDelayMins = details[i]; break;
        case 16:
          this.originAirportCode = details[i]; break;
        case 17:
          this.destinationAirportCode = details[i]; break;
        case 18:
          this.distanceInMiles = details[i]; break;
        case 19:
          this.taxiInTimeMins = details[i]; break;
        case 20:
          this.taxiOutTimeMins = details[i]; break;
        case 21:
          this.flightCancelled = details[i]; break;
        case 22:
          this.cancellationCode = details[i]; break;
        case 23:
          this.diverted = details[i]; break;
        case 24:
          this.carrierDelayMins = details[i]; break;
        case 25:
          this.weatherDelayMins = details[i]; break;
        case 26:
          this.nasDelayMins = details[i]; break;
        case 27:
          this.securityDelayMins = details[i]; break;
        case 28:
          this.lateAircraftDelayMins = details[i]; break;
      }
    }
  }

  String year;
  String month;
  String dayOfMonth;
  String dayOfWeek;
  String depTime;
  String scheduledDepTime;
  String arrTime;
  String scheduledArrTime;
  String uniqueCarrier;
  String flightNum;
  String tailNum;
  String actualElapsedMins;
  String crsElapsedMins;
  String airMins;
  String arrDelayMins;
  String depDelayMins;
  String originAirportCode;
  String destinationAirportCode;
  String distanceInMiles;
  String taxiInTimeMins;
  String taxiOutTimeMins;
  String flightCancelled;
  String cancellationCode; // (A = carrier; B = weather; C = NAS; D = security)
  String diverted; // 1 = yes; 0 = no
  String carrierDelayMins;
  String weatherDelayMins;
  String nasDelayMins;
  String securityDelayMins;
  String lateAircraftDelayMins;
}