/*
 * JavaFBP - A Java Implementation of Flow-Based Programming (FBP)
 * Copyright (C) 2009, 2016 J. Paul Morrison
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, see the GNU Library General Public License v3
 * at https://www.gnu.org/licenses/lgpl-3.0.en.html for more details.
 */
package com.jpaulmorrison.fbp.test;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

import com.jpaulmorrison.fbp.core.engine.Component;
import com.jpaulmorrison.fbp.core.engine.ComponentDescription;
import com.jpaulmorrison.fbp.core.engine.InPort;
import com.jpaulmorrison.fbp.core.engine.InputPort;
import com.jpaulmorrison.fbp.core.engine.OutPort;
import com.jpaulmorrison.fbp.core.engine.OutPorts;
import com.jpaulmorrison.fbp.core.engine.OutputPort;
import com.jpaulmorrison.fbp.core.engine.Packet;


/**
 * Component to step through a directory, generating two streams of packets:
 *   - directories go to port DIRS
 *   - files go to port FILES 
 * The file name is specified as a String via an InitializationConnection.
 */

@ComponentDescription("Generate two streams of packets from FlightEvents: on-time arrivals and delay records")
@OutPorts({  
  //@OutPort(value = "PROMPTS", description = "On-time arrivals"),
  @OutPort(value = "DELAYS", description = "Delay records")
  })
@InPort(value = "SOURCE", description = "Flight Events")

public class FilterAndConvertFlightEvents extends Component {

  
  private OutputPort prompts, delays;

  private InputPort source;
  

  @Override
  protected void execute() {
    int dirCount = 1;
    Packet rp = null;
    
    while (null != (rp = source.receive())) { 

      String[] flightEvent = (String [])rp.getContent();
      drop(rp);

      int arrDelayMins = -1;
      try {
        // arrDelayMins - 14 in 0-based index.
        arrDelayMins = Integer.parseInt(flightEvent[14]);
      } catch (NumberFormatException e) {
        // ignore because already initialized to -1
      }

      if(arrDelayMins > 0) {
        // just get FlightDelayRecord specific columns here
        // ### ideally should pass FlightEvent/FlightDelayRecord objects and use Builder
        // ### but don't know how serialization works here
        String[] flightDelay = new String[6];
        flightDelay[0] = flightEvent[0]; // year
        flightDelay[1] = flightEvent[1]; // month
        flightDelay[2] = flightEvent[2]; // dayOfMonth,
        flightDelay[3] = flightEvent[8]; // uniqueCarrier,
        flightDelay[4] = flightEvent[9]; // flightNum,
        flightDelay[5] = flightEvent[14]; // arrDelayMins

        Packet pOut = create(flightDelay);
        delays.send(pOut);
      }
      else {
        // WriteToSocketFromJava
        // POST to a GO-lang service that does airline ratings with on-time arrivals !
      }      
    }
  }

  @Override
  protected void openPorts() {

    //prompts = openOutput("PROMPTS");
    delays = openOutput("DELAYS");

    source = openInput("SOURCE");

  }
}
