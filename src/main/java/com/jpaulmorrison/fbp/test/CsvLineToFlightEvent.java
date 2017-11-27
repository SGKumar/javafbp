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


import com.jpaulmorrison.fbp.core.engine.Component;
import com.jpaulmorrison.fbp.core.engine.ComponentDescription;
import com.jpaulmorrison.fbp.core.engine.InPort;
import com.jpaulmorrison.fbp.core.engine.InPorts;
import com.jpaulmorrison.fbp.core.engine.InputPort;
import com.jpaulmorrison.fbp.core.engine.OutPort;
import com.jpaulmorrison.fbp.core.engine.OutPorts;
import com.jpaulmorrison.fbp.core.engine.OutputPort;
import com.jpaulmorrison.fbp.core.engine.Packet;

import com.jpaulmorrison.fbp.test.FlightEvent;

/**
 * Provide words from a stream of space-separated records - essentially same as routing.DeCompose
 * Bob Corrick December 2011
 */
@ComponentDescription("Take CSWs from line IN and deliver words (FlightEvent abstraction) OUT")
@OutPorts({ @OutPort(value = "OUT") })
@InPorts({ @InPort("IN") })
public class CsvLineToFlightEvent extends Component {

  private InputPort inPort;
  private OutputPort outPort;

  @Override
  protected void execute() {
    Packet pIn;
    while ((pIn = inPort.receive()) != null) {
      String w = (String) pIn.getContent();
      drop(pIn);

      // Get words for this record
      String[] cols = w.split(",");

      // could have used the Builder pattern and created FlightEvent - but quick and dirty here.
      //FlightEvent flightEvent = new FlightEvent(words);

      // Send whole array as single packet
      Packet pOut = create(cols);
      outPort.send(pOut);
    }
  }

  @Override
  protected void openPorts() {
    inPort = openInput("IN");
    outPort = openOutput("OUT");
  }
}
