/*******************************************************************************
 * Copyright 2016-2017 Dell Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @microservice:  device-sdk
 * @author: Tyler Cox, Dell
 * @version: 1.0.0
 *******************************************************************************/
package org.edgexfoundry.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.edgexfoundry.data.DeviceStore;
import org.edgexfoundry.data.ObjectStore;
import org.edgexfoundry.data.ProfileStore;
import org.edgexfoundry.domain.ProtocolAttribute;
import org.edgexfoundry.domain.ProtocolObject;
import org.edgexfoundry.domain.ScanList;
import org.edgexfoundry.domain.meta.Addressable;
import org.edgexfoundry.domain.meta.Device;
import org.edgexfoundry.domain.meta.ResourceOperation;
import org.edgexfoundry.handler.ProtocolHandler;
import org.edgexfoundry.support.logging.client.EdgeXLogger;
import org.edgexfoundry.support.logging.client.EdgeXLoggerFactory;

@Service
public class ProtocolDriver {
	
	private final static EdgeXLogger logger = EdgeXLoggerFactory.getEdgeXLogger(ProtocolDriver.class);
	
	@Autowired
	ProfileStore profiles;
	
	@Autowired
	DeviceStore devices;
	
	@Autowired
	ObjectStore objectCache;
	
	@Autowired
	ProtocolHandler handler;
	
	public ScanList discover() {
		ScanList scan = new ScanList();
		
		// TODO 4: [Optional] For discovery enabled device services:
		// Replace with Protocol specific discovery mechanism
		// TODO 5: [Required] Remove next code block if discovery is not used
		for(int i = 0; i < 10; i++) {
			Map<String, String> identifiers = new HashMap<>();
			identifiers.put("name", String.valueOf(i));
			identifiers.put("address", "02:01:00:11:12:1" + String.valueOf(i));
			identifiers.put("interface", "default");
			scan.add(identifiers);
		}
		
		return scan;
	}
	
	// operation is get or set
	// Device to be written to
	// Protocol Object to be written to
	// value is string to be written or null
	public void process(ResourceOperation operation, Device device, ProtocolObject object, String value, String transactionId, String opId) {
		String result = "";
		
		// TODO 2: [Optional] Modify this processCommand call to pass any additional required metadata from the profile to the driver stack
		result = processCommand(operation.getOperation(), device.getAddressable(), object.getAttributes(), value);
		
		objectCache.put(device, operation, result);
		handler.completeTransaction(transactionId, opId, objectCache.getResponses(device, operation));
	}

	// Modify this function as needed to pass necessary metadata from the device and its profile to the driver interface
	public String processCommand(String operation, Addressable addressable, ProtocolAttribute attributes, String value) {
		String address = addressable.getPath();
		String intface = addressable.getAddress();
		logger.debug("ProcessCommand: " + operation + ", interface: " + intface + ", address: " + address + ", attributes: " + attributes + ", value: " + value );
		String result = ""; 
		
		// TODO 1: [Required] ${Protocol name} stack goes here, return the raw value from the device as a string for processing
		if (operation.toLowerCase().equals("get")) {
			Random rand = new Random();
			result = Float.toString(rand.nextFloat()*100);
		} else {
			result = value;
		}
		
		return result;
	}

	public void initialize() {
		// TODO 3: [Optional] Initialize the interface(s) here if necessary, runs once on service startup
		
	}

	public void disconnectDevice(Addressable address) {
		// TODO 6: [Optional] Disconnect devices here using driver level operations
		
	}
	
	@SuppressWarnings("unused")
	private void receive() {
		// TODO 7: [Optional] Fill with your own implementation for handling asynchronous data from the driver layer to the device service
		Device device = null;
		String result = "";
		ResourceOperation operation = null;		
		
		objectCache.put(device, operation, result);
	}

}
