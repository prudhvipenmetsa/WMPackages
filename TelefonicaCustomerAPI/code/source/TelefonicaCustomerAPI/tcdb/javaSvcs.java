package TelefonicaCustomerAPI.tcdb;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import com.terracottatech.store.Dataset;
import com.terracottatech.store.DatasetWriterReader;
import com.terracottatech.store.Record;
import com.terracottatech.store.StoreException;
import com.terracottatech.store.definition.CellDefinition;
import com.terracottatech.store.definition.StringCellDefinition;
import com.terracottatech.store.manager.DatasetManager;
// --- <<IS-END-IMPORTS>> ---

public final class javaSvcs

{
	// ---( internal utility methods )---

	final static javaSvcs _instance = new javaSvcs();

	static javaSvcs _newInstance() { return new javaSvcs(); }

	static javaSvcs _cast(Object o) { return (javaSvcs)o; }

	// ---( server methods )---




	public static final void getDataSet (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getDataSet)>> ---
		// @sigtype java 3.5
		// [i] field:0:required dataSetName
		/*
		DatasetConfiguration customerConfig =
				datasetManager.datasetConfiguration() // <3>
				.offheap("second") // <4>
				.disk("customer")
				.index(FNAME,  IndexSettings.BTREE)// <5>
				.index(LNAME,IndexSettings.BTREE)
				.index(MPHONE,IndexSettings.BTREE)
				.build(); // <6>
		
		*/
		
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	dataSetName = IDataUtil.getString( pipelineCursor, "dataSetName" );
		
		// pipeline
		
		
			try{
				pipelineCursor.insertAfter("dataset", getDatasetManager().getDataset(dataSetName, com.terracottatech.store.Type.STRING));
						
			
				
			}catch(StoreException e){
				throw new ServiceException(e.getMessage());
			}catch(Exception e){
				throw new ServiceException(e.getMessage());
			}
			pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getRecordByKey (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRecordByKey)>> ---
		// @sigtype java 3.5
		// [i] field:0:required dataSetName
		// [i] field:0:required key
		// [o] field:0:required firstName
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	dataSetName = IDataUtil.getString( pipelineCursor, "dataSetName" );
		String	key = IDataUtil.getString( pipelineCursor, "key" );
		// pipeline
		
		try (Dataset<String> dataset =
				datasetManager.getDataset(dataSetName, com.terracottatech.store.Type.STRING)) {
			DatasetWriterReader<String> writerReader = dataset.writerReader();
			
			Optional<Record<String>> recordOptional = writerReader.get(key);
		
		      if (recordOptional.isPresent()) {
		    	  recordOptional.get().getKey();
		        String custNo = recordOptional.get().getKey();
		        String firstName = recordOptional.get().get(FNAME).get();
		        String lastName = recordOptional.get().get(LNAME).get();
		        String email = recordOptional.get().get(EMAIL).get();
		        String mobilePhone = recordOptional.get().get(MPHONE).get();
		        String homePhone = recordOptional.get().get(HPHONE).get();
		        String address1 = recordOptional.get().get(ADDR1).get();
		        String address2 = recordOptional.get().get(ADDR2).get();
		        String city = recordOptional.get().get(CITY).get();
		        String st = recordOptional.get().get(ST).get();
		        String zip = recordOptional.get().get(ZIP).get();
		        pipelineCursor.insertAfter("customerNo", custNo);
		        pipelineCursor.insertAfter("firstName", firstName);
		        pipelineCursor.insertAfter("lastName", lastName);
		        pipelineCursor.insertAfter("email", email);
		        pipelineCursor.insertAfter("mobilePhone", mobilePhone);
		        pipelineCursor.insertAfter("homePhone", homePhone);
		        pipelineCursor.insertAfter("address1", address1);
		        pipelineCursor.insertAfter("address2", address2);
		        pipelineCursor.insertAfter("city", city);
		        pipelineCursor.insertAfter("st", st);
		        pipelineCursor.insertAfter("zip", zip);
		      }
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static  DatasetManager datasetManager;
	private static final StringCellDefinition FNAME = CellDefinition.defineString("firstName");
	private static final StringCellDefinition LNAME = CellDefinition.defineString("lastName");
	private static final StringCellDefinition EMAIL = CellDefinition.defineString("email");
	private static final StringCellDefinition MPHONE = CellDefinition.defineString("mobilePhone");
	private static final StringCellDefinition HPHONE = CellDefinition.defineString("homePhone");
	private static final StringCellDefinition ADDR1 = CellDefinition.defineString("address1");
	private static final StringCellDefinition ADDR2 = CellDefinition.defineString("address2");
	private static final StringCellDefinition CITY = CellDefinition.defineString("city");
	private static final StringCellDefinition ST = CellDefinition.defineString("state");
	private static final StringCellDefinition ZIP = CellDefinition.defineString("zip");
	public static DatasetManager getDatasetManager() throws URISyntaxException,ServiceException,StoreException{
		if(datasetManager!=null ) return datasetManager;
		String tcURL = System.getProperty("watt.tcdb.customer.uri");
		if(tcURL == null || !tcURL.startsWith("terracotta"))  throw new ServiceException("TCDB URL is not configured in extended settings watt.tcdb.customer.uri property");
		java.net.URI clusterUri = new java.net.URI(tcURL);
	    return
				DatasetManager.clustered(clusterUri).withConnectionTimeout(300,TimeUnit.SECONDS).build() ;
	
	
			
			// datasetManager.destroyDataset(STORE_NAME);
	}
	// --- <<IS-END-SHARED>> ---
}

