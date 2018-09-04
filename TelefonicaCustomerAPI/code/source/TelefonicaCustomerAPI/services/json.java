package TelefonicaCustomerAPI.services;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
// --- <<IS-END-IMPORTS>> ---

public final class json

{
	// ---( internal utility methods )---

	final static json _instance = new json();

	static json _newInstance() { return new json(); }

	static json _cast(Object o) { return (json)o; }

	// ---( server methods )---




	public static final void xmlToJson (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(xmlToJson)>> ---
		// @sigtype java 3.5
		// [i] field:0:required xmlString
		// [o] field:0:required jsonString
		try{
			JacksonXmlModule xmlModule = new JacksonXmlModule();
			xmlModule.setDefaultUseWrapper(false);
						XmlMapper xmlMapper = new XmlMapper(xmlModule);
			// pipeline
			IDataCursor pipelineCursor = pipeline.getCursor();
			String	xmlString = IDataUtil.getString( pipelineCursor, "xmlString" );
			JsonNode node = xmlMapper.readTree(xmlString.getBytes("UTF-8"));
			ObjectMapper jsonMapper = new ObjectMapper();
			IDataUtil.put( pipelineCursor, "jsonString", jsonMapper.writeValueAsString(node));
			
			pipelineCursor.destroy();
		
			
		}catch(Exception e){
			throw new ServiceException (e);
		}
		// --- <<IS-END>> ---

                
	}
}

