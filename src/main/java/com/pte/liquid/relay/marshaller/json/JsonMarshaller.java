package com.pte.liquid.relay.marshaller.json;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

public class JsonMarshaller implements Marshaller{

	private final static Logger logger = Logger.getLogger(JsonMarshaller.class);
	private Gson gson;
	
	public JsonMarshaller(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gson = gsonBuilder.create();		
	}
	
	@Override
	public synchronized String marshal(Message message) throws RelayException {
		logger.debug("Marshalling json message...");		
		return gson.toJson(message);
	}

	@Override
	public synchronized Message unmarshal(String message) throws RelayException {
		logger.debug("Unarshalling json message...");
		return gson.fromJson(message, Message.class);
	}
	
	

}
