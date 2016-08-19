//Copyright 2015 Paul Tegelaar

//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.pte.liquid.relay.marshaller.json;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

public class JsonMarshaller implements Marshaller{

	private final static Logger logger = Logger.getLogger(JsonMarshaller.class.getName());
	private Gson gson;
	
	public JsonMarshaller(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		gson = gsonBuilder.create();
		
	}
	
	@Override
	public synchronized String marshal(Message message) throws RelayException {
		logger.finest("Marshalling json message...");
		return gson.toJson(message);
	}

	@Override
	public synchronized Message unmarshal(String message) throws RelayException {
		logger.finest("Unarshalling json message...");
		return gson.fromJson(message, Message.class);
	}
	
	

}
