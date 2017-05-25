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
package org.edgexfoundry.domain;

import org.edgexfoundry.support.logging.client.EdgeXLogger;
import org.edgexfoundry.support.logging.client.EdgeXLoggerFactory;
import com.google.gson.Gson;

public class ProtocolAttribute {
	
	private final static EdgeXLogger logger = EdgeXLoggerFactory.getEdgeXLogger(ProtocolAttribute.class);
	
	// Replace these attributes with the protocol
	// specific metadata needed by the Protocol Driver
	private String type;
	private String instance;
	
	public ProtocolAttribute(Object attributes) {
		try {
			Gson gson = new Gson();
			// 1. Java object to JSON, and save into a file
			String jsonString = gson.toJson(attributes);
			ProtocolAttribute thisObject = gson.fromJson(jsonString, this.getClass());
			
			this.setInstance(thisObject.getInstance());
			this.setType(thisObject.getType());
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("Cannot Construct ProtocolAttribute: " + e.getMessage());
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	

}
