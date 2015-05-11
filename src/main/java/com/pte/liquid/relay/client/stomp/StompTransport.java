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
package com.pte.liquid.relay.client.stomp;

import java.io.IOException;

import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.projectodd.stilts.stomp.StompException;
import org.projectodd.stilts.stomp.StompMessages;
import org.projectodd.stilts.stomp.client.StompClient;
import org.springframework.scheduling.annotation.Async;

import com.pte.liquid.relay.Marshaller;
import com.pte.liquid.relay.Transport;
import com.pte.liquid.relay.exception.RelayException;
import com.pte.liquid.relay.model.Message;

public class StompTransport implements Transport {

	private static final String RELAY_STOMP_PORT = "relay_stomp_port";
	private static final String RELAY_STOMP_HOSTNAME = "relay_stomp_hostname";
	private static final String RELAY_DESTINATION = "relay_destination";
	
	
	private Marshaller marshaller;
	private final static Logger logger = Logger.getLogger(StompTransport.class);
	private String hostname = "localhost";
	private int port = 33555;
	private String destination = "com.pte.liquid.relay.in";
	private StompClient client;
	

	public StompTransport() {
	}

	@Override
	@Async
	public synchronized void send(Message msg) throws RelayException {
		logger.debug("Getting trigger to send");
		final String Stringcontent = marshaller.marshal(msg);
		try {
			if (client == null) {
				client = new StompClient("stomp://" + hostname + ":" + port);
			}
			client.connect();
			client.send(StompMessages.createStompMessage("/queue/"
					+ destination, Stringcontent));
			client.disconnect();
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new RelayException(e);
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
			throw new RelayException(e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
			throw new RelayException(e);
		} catch (TimeoutException e) {
			logger.error(e.getMessage());
			throw new RelayException(e);
		} catch (StompException e) {
			logger.error(e.getMessage());
			throw new RelayException(e);
		}

		logger.debug("Done sending");

	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	@Override
	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public void setProperties(Properties properties) {
		if (properties != null && properties.getProperty(RELAY_DESTINATION) !=null && !"".equals(properties.getProperty(RELAY_DESTINATION))) {
			destination = properties.getProperty(RELAY_DESTINATION);
		}
		if (properties != null && properties.getProperty(RELAY_STOMP_HOSTNAME) !=null && !"".equals(properties.getProperty(RELAY_STOMP_HOSTNAME))) {
			hostname = properties.getProperty(RELAY_STOMP_HOSTNAME);
		}
		if (properties != null && properties.getProperty(RELAY_STOMP_PORT) !=null && !"".equals(properties.getProperty(RELAY_STOMP_PORT))){
			port = Integer.parseInt(properties.getProperty(RELAY_STOMP_PORT));
		}		
		
		
		
	}

}
