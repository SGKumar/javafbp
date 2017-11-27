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

import java.util.HashMap;

import com.jpaulmorrison.fbp.core.engine.Component;
import com.jpaulmorrison.fbp.core.engine.ComponentDescription;
import com.jpaulmorrison.fbp.core.engine.InPort;
import com.jpaulmorrison.fbp.core.engine.InputPort;
import com.jpaulmorrison.fbp.core.engine.OutPort;
import com.jpaulmorrison.fbp.core.engine.OutputPort;
import com.jpaulmorrison.fbp.core.engine.Packet;

/**
 * Component to aggregate delays
 */
@ComponentDescription("Generate Flight Delay metrics")
@OutPort("OUT")
@InPort("IN")
public class AverageCarrierDelays extends Component {

  private InputPort inPort;

  private OutputPort outPort;

  @SuppressWarnings("unchecked")
  @Override
  protected void execute() {
    
    HashMap<String, FlightDelayAggregate> hm = new HashMap<String, FlightDelayAggregate>();

    Packet p;
    while ((p = inPort.receive()) != null) {
    	String[] flightDelay = (String []) p.getContent();
      drop(p);

    	String uniqueCarrier = flightDelay[3];
      int arrDelayMins = Integer.parseInt(flightDelay[5]);
      FlightDelayAggregate agg = null;
      if(!hm.containsKey(uniqueCarrier)) {
        // if it has come so far there is a valid int there
        agg = new FlightDelayAggregate(uniqueCarrier, arrDelayMins);
        hm.put(uniqueCarrier, agg);
      }
      else {
        agg = hm.get(uniqueCarrier);
        agg.addDelay(arrDelayMins);
      }
    }
    
    for (FlightDelayAggregate agg : hm.values()) {
    	p = create(agg.toString());    	
    	outPort.send(p);    	
    }

  }

  @Override
  protected void openPorts() {
    inPort = openInput("IN");

    outPort = openOutput("OUT");
  }
}
