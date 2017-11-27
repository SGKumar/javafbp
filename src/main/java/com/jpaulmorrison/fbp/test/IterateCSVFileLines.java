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

import java.io.RandomAccessFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import com.jpaulmorrison.fbp.core.engine.Component;
import com.jpaulmorrison.fbp.core.engine.ComponentDescription;
import com.jpaulmorrison.fbp.core.engine.InPort;
import com.jpaulmorrison.fbp.core.engine.InPorts;
import com.jpaulmorrison.fbp.core.engine.InputPort;
import com.jpaulmorrison.fbp.core.engine.OutPort;
import com.jpaulmorrison.fbp.core.engine.OutputPort;
import com.jpaulmorrison.fbp.core.engine.Packet;


/**
 * Component to read data from a file, generating a packet OUT.
 * The file is specified in the IIP SOURCE as a name with optional format (separated from name by comma).
 * This component converts the specified format (if one is specified) to Unicode.
 * The IP SEEK specifies an inital seek (file pointer) value and line count.
 * The OUT packet is a Hashtable with keys SEEK for the given file pointer, PAGE for a String array of lines from the file, and NEXT for the final file pointer value.
 * 
 * Developed for Appkata project (?)
 * 
 */
@ComponentDescription("Read & send input csv file line by line")
//@OutPort(value = "OUT", description = "SEEK: initial file pointer, PAGE: of lines, NEXT: file pointer", type = Hashtable.class)
@InPort(value = "IN", description = "File name to read", type = String.class)
@OutPort(value = "OUT", description = "String of CSVs", type = String.class)
public class IterateCSVFileLines extends Component {
 
  private InputPort inPort;
  private OutputPort outPort;

  @SuppressWarnings({ "unchecked" })
  @Override
  protected void execute() {

    Packet rp = inPort.receive();
    if (rp == null) {
      return;
    }
    inPort.close();

    String fileName = (String) rp.getContent();
    drop(rp);

    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(fileName));
      String line = reader.readLine(); // skip header or worst case first line
      for(line = reader.readLine(); line != null; line = reader.readLine()) {
        //System.out.println(line);
        if (!outPort.isClosed()) {
          Packet pOut = create(line); // send out
          outPort.send(pOut);
        }
      }
      reader.close();

    } catch (IOException e) {
      System.out.println(e.getMessage() + " - file: " + fileName + " - component: " + this.getName());
    }
  }

  @Override
  protected void openPorts() {
    inPort = openInput("IN");
    outPort = openOutput("OUT");
  }
}
