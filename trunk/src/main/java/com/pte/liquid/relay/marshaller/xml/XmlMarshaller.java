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
			logger.error(e.getMessage());
			throw new RelayException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
			throw new RelayException(e);
		} catch (IOException e) {
			logger.error(e.getMessage());
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
