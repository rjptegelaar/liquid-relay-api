//Copyright 2014 Paul Tegelaar
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

import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Async;

import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

public class JmsTransport implements Transport {
	
	
	private JmsTemplate template;	
	private Marshaller marshaller;
	private final static Logger logger = Logger.getLogger(JmsTransport.class);
	
	
	@Override
	@Async
	public synchronized void send(Message msg) throws RelayException{
		logger.debug("Getting trigger to send");										
		final String Stringcontent = marshaller.marshal(msg);
		
		template.send(new MessageCreator() {
            public javax.jms.Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(Stringcontent);
              }
          });	
		
		logger.debug("Done sending");
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


	
	
	
}
