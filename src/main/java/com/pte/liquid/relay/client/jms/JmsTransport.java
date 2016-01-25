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
package com.pte.liquid.relay.client.jms;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Async;

import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

public class JmsTransport implements Transport {
	
	
	private static final String RELAY_DESTINATION = "relay_destination";
	private JmsTemplate template;	
	private Marshaller marshaller;
	private String destination;
	
	
	@Override
	@Async
	public synchronized void send(Message msg) throws RelayException{										
		final String Stringcontent = marshaller.marshal(msg);
		
		
		if(destination!=null && !"".equals(destination)){
			template.send(destination, new MessageCreator() {
	            public javax.jms.Message createMessage(Session session) throws JMSException {
	                return session.createTextMessage(Stringcontent);
	              }
	          });
		}else{
			template.send(new MessageCreator() {
	            public javax.jms.Message createMessage(Session session) throws JMSException {
	                return session.createTextMessage(Stringcontent);
	              }
	          });				
		}
		
	}

	public JmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	@Override
	public void setMarshaller(Marshaller marshaller) {
			this.marshaller = marshaller;
	}

	@Override
	public void setProperties(Properties properties) {
		if(properties!=null && properties.getProperty(RELAY_DESTINATION)!=null && !"".equals(properties.getProperty(RELAY_DESTINATION))){
			destination = properties.getProperty(RELAY_DESTINATION);
		}		
	}

	@Override
	public void destroy() {

	}


	
	
	
}
