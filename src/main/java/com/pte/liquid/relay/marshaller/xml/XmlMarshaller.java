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
package com.pte.liquid.relay.marshaller.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

public class XmlMarshaller implements Marshaller{

	private org.springframework.oxm.Marshaller marshaller;
	private Unmarshaller unmarshaller;
	private final static Logger logger = Logger.getLogger(XmlMarshaller.class);
	
	@Override
	public synchronized String marshal(Message message) throws RelayException{
		logger.debug("Marshalling xml message...");								
		
		StringWriter sw = new StringWriter();
		try {
			marshaller.marshal(message, new StreamResult(sw));
		} catch (XmlMappingException e) {
			logger.debug(e.getMessage());
			throw new RelayException(e);
		} catch (IOException e) {
			logger.debug(e.getMessage());
			throw new RelayException(e);
		}
		logger.debug("Done marshalling");
		return sw.toString();
	}

	@Override
	public synchronized Message unmarshal(String message) throws RelayException{
		logger.debug("Unmarshalling xml message...");			
		
		StringReader sr = new StringReader(message);
		Message msg;
		try {
			msg = (Message) unmarshaller.unmarshal(new StreamSource(sr));
			return msg;	
		} catch (XmlMappingException e) {
			logger.debug(e.getMessage());
			throw new RelayException(e);
		} catch (IOException e) {
			logger.debug(e.getMessage());
			throw new RelayException(e);
		}
		
	}

	public org.springframework.oxm.Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(org.springframework.oxm.Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	
	
	
}
