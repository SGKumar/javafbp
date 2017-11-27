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

package com.jpaulmorrison.fbp.resourcekit.examples.networks;

import com.jpaulmorrison.fbp.core.components.io.ReadFile;
import com.jpaulmorrison.fbp.test.CsvLineToFlightEvent;
import com.jpaulmorrison.fbp.test.FilterAndConvertFlightEvents;
import com.jpaulmorrison.fbp.test.AverageCarrierDelays;


import com.jpaulmorrison.fbp.core.engine.Network;
import com.jpaulmorrison.fbp.core.components.swing.ShowText;
import com.jpaulmorrison.fbp.core.components.routing.Sort;
import com.jpaulmorrison.fbp.core.components.misc.GenerateTestData;

import com.jpaulmorrison.fbp.core.components.misc.WriteToConsole;

import java.io.File;

/** 
 * Network to demo FlightDelayStreaming data analysis - output is written to Swing pane
 *
 */
public class FlightDelayStreaming extends Network {

  String description = "Network to demo FlightDelayStreaming data analysis - output is written to Swing pane";
  // from GenerateTestData

  @Override
  protected void define() {
    
	//component("_Discard", com.jpaulmorrison.fbp.core.components.routing.Discard.class);
/*    component("_Write_text_to_pane", ShowText.class);
    component("_Sort", Sort.class);
    component("_Generate_1st_group", GenerateTestData.class);
    component("_Generate_2nd_group", GenerateTestData.class);
    initialize("100 ", component("_Generate_1st_group"), port("COUNT"));
    connect(component("_Generate_2nd_group"), port("OUT"), component("_Sort"), port("IN"));
    connect(component("_Generate_1st_group"), port("OUT"), component("_Sort"), port("IN"));
    //connect(component("_Write_text_to_pane"), port("OUT"), component("_Discard"), port("IN"));
    initialize("Sorted Data", component("_Write_text_to_pane"), port("TITLE"));
    connect(component("_Sort"), port("OUT"), component("_Write_text_to_pane"), port("IN"));
    initialize("50", component("_Generate_2nd_group"), port("COUNT"));
*/

    // MASTER VERSION BEGINS
    // define components first
    // make sure to comment out input csv's header row
    component("_readFile", ReadFile.class); // existing
    component("_csvToFlightEvent", CsvLineToFlightEvent.class); // custom, ideally should be customizable with the flow
    component("_filterFlightsToDelays", FilterAndConvertFlightEvents.class);
    component("_evalFlightDelays", AverageCarrierDelays.class);
     
    // connects
    connect(component("_readFile"), port("OUT"), component("_csvToFlightEvent"), port("IN"));
    connect(component("_csvToFlightEvent"), port("OUT"), component("_filterFlightsToDelays"), port("SOURCE"));
    connect(component("_filterFlightsToDelays"), port("DELAYS"), component("_evalFlightDelays"), port("IN"));

    // in future need to be POSTed to a REST service (writeSocket) for my demo
    //connect(component("_filterFlightsToDelays"), port("PROMPTS"), component("_evalFlightDelays"), port("IN"));

    connect(component("_evalFlightDelays"), port("OUT"), component("Write", WriteToConsole.class), port("IN"));

    // initialize at the end, why ?
    initialize("src/main/resources/testdata/flights-4486rows.csv".replace("/", File.separator), component("_readFile"), port("SOURCE"));
    // MASTER VERSION ENDS

  }

  public static void main(final String[] argv) throws Exception {

    new FlightDelayStreaming().go();
  }
}
