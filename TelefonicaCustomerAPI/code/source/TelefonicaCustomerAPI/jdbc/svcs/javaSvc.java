package TelefonicaCustomerAPI.jdbc.svcs;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class javaSvc

{
	// ---( internal utility methods )---

	final static javaSvc _instance = new javaSvc();

	static javaSvc _newInstance() { return new javaSvc(); }

	static javaSvc _cast(Object o) { return (javaSvc)o; }

	// ---( server methods )---




	public static final void getCustFromDB (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCustFromDB)>> ---
		// @sigtype java 3.5
		// [i] field:0:required firstName_1
		// [i] field:0:required lastName_2
		// [i] field:0:required mobilePhone_3
		// input
		IDataCursor pipelineCursor = pipeline.getCursor();
		IData input = IDataFactory.create();
		IDataCursor inputCursor = input.getCursor();
		
		// getRecInput
		IData	getRecInput = IDataFactory.create();
		IDataCursor getRecInputCursor = getRecInput.getCursor();
		
		// getRecInput.overrideCredentials
		IData	overrideCredentials = IDataFactory.create();
		IDataCursor overrideCredentialsCursor = overrideCredentials.getCursor();
		
		overrideCredentialsCursor.destroy();
		IDataUtil.put( getRecInputCursor, "overrideCredentials", overrideCredentials );
		IDataUtil.put( getRecInputCursor, "firstName_1", IDataUtil.getString(pipelineCursor,"firstName_1") );
		IDataUtil.put( getRecInputCursor, "lastName_2",IDataUtil.getString(pipelineCursor,"lastName_2") );
		IDataUtil.put( getRecInputCursor, "mobilePhone_3",IDataUtil.getString(pipelineCursor,"mobilePhone_3") );
		getRecInputCursor.destroy();
		IDataUtil.put( inputCursor, "getRecInput", getRecInput );
		
		inputCursor.destroy();
		long start_time = System.currentTimeMillis();
		// output
		IData 	output = IDataFactory.create();
		try{
			output = Service.doInvoke( "TelefonicaCustomerAPI.jdbc.svcs", "getRec", input );
		}catch( Exception e){}
		
		
		
		
		IDataCursor outputCursor = output.getCursor();
		
			// getRecOutput
			IData	getRecOutput = IDataUtil.getIData( outputCursor, "getRecOutput" );
		//pipelineCursor.insertAfter("getRecOutput", getRecOutput);
		outputCursor.destroy();
		long end_time = System.currentTimeMillis();
		
		pipelineCursor.insertAfter("processingTime", ""+(end_time - start_time));
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}
}

