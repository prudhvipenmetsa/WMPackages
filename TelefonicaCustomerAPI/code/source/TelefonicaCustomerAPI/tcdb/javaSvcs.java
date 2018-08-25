package TelefonicaCustomerAPI.tcdb;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import com.terracottatech.store.Dataset;
import com.terracottatech.store.DatasetReader;
import com.terracottatech.store.DatasetWriterReader;
import com.terracottatech.store.Record;
import com.terracottatech.store.StoreException;
import com.terracottatech.store.definition.CellDefinition;
import com.terracottatech.store.definition.StringCellDefinition;
import com.terracottatech.store.function.Collectors;
import com.terracottatech.store.manager.DatasetManager;
import com.terracottatech.store.stream.MutableRecordStream;
import com.terracottatech.store.stream.RecordStream;
// --- <<IS-END-IMPORTS>> ---

public final class javaSvcs

{
	// ---( internal utility methods )---

	final static javaSvcs _instance = new javaSvcs();

	static javaSvcs _newInstance() { return new javaSvcs(); }

	static javaSvcs _cast(Object o) { return (javaSvcs)o; }

	// ---( server methods )---




	public static final void closeDatasetManager (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(closeDatasetManager)>> ---
		// @sigtype java 3.5
		com.gcs.tcclient.DataSetManagerFactory.close();	 
		// --- <<IS-END>> ---

                
	}



	public static final void createDatasetManager (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createDatasetManager)>> ---
		// @sigtype java 3.5
		if(datasetManager!=null )  datasetManager.close();
		try{
			String tcURL = System.getProperty("watt.tcdb.customer.uri");
			
			
			
			if(tcURL == null || !tcURL.startsWith("terracotta"))  
				throw new ServiceException("TCDB URL is not configured in extended settings watt.tcdb.customer.uri property");
			com.gcs.tcclient.DataSetManagerFactory.tcURL=tcURL;
			com.gcs.tcclient.DataSetManagerFactory.timeout=300;			
			com.gcs.tcclient.DataSetManagerFactory.getDataset();
			
			
			System.out.println("NEW: CREATING DatasetManager while package startup");
		}catch(Exception e){
			System.out.println("Excpetion: DSM" + e.getCause().getMessage()+ "*********" + e.getMessage());
			e.printStackTrace();
			throw new ServiceException(e);
		}
		// --- <<IS-END>> ---

                
	}



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
		
		
		
			pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getOpenDatasetsCount (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getOpenDatasetsCount)>> ---
		// @sigtype java 3.5
		// [o] field:0:required count
		// pipeline
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "count",availDatasetStack.size()+"" );
		pipelineCursor.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void getRecordByFilter (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRecordByFilter)>> ---
		// @sigtype java 3.5
		// [i] field:0:required firstName
		// [i] field:0:required lastName
		// [i] field:0:required mobilePhone
		// [o] record:1:required customer
		// [o] - field:0:required customerNo
		// [o] - field:0:required firstName
		// [o] - field:0:required lastName
		// [o] - field:0:required mobilePhone
		// [o] - field:0:required address1
		// [o] - field:0:required address2
		// [o] - field:0:required city
		// [o] - field:0:required zip
		// [o] - field:0:required homePhone
		// [o] - field:0:required state
		// [o] - field:0:required email
		// [o] field:0:required processingTime
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	dataSetName = IDataUtil.getString( pipelineCursor, "dataSetName" );
		Optional<String> firstName_1 = Optional.ofNullable(IDataUtil.getString( pipelineCursor, "firstName" ));
		Optional<String> lastName_1 =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "lastName" ));
		Optional<String>mobilePhone_1 =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "mobilePhone" ));
		
		
		
		// pipeline
		
		try  {
			List<Record<String>> results = null ;
			//long start_time = System.nanoTime();
			long start_time = System.currentTimeMillis();
			Dataset<String> ds = com.gcs.tcclient.DataSetManagerFactory.getDataset(); //getDataset();// getDatasetFromStack(); // getDataset();
			DatasetWriterReader<String> writerReader = ds.writerReader();
			if(firstName_1.isPresent() && lastName_1.isPresent() && mobilePhone_1.isPresent()){
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
		
							//recordStream.filter(FNAME.value().is("/^.Prudhvi0*$/"))
							//recordStream.filter(FNAME.value().is("/^.*Prudhvi0.*$/")) //firstName_1.get()))
							recordStream.filter(FNAME.value().is(firstName_1.get()))
							.filter(LNAME.value().is(lastName_1.get()))	
							.filter(MPHONE.value().is(mobilePhone_1.get()))
							//.batch(10) 
							.collect(java.util.stream.Collectors.toList());		
					//.findAny();
					//result.forEach(System.out::println);
		
				}
		
			}else if(firstName_1.isPresent() && lastName_1.isPresent()){
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
							recordStream
							.filter(FNAME.value().is(firstName_1.get()))	
							.filter(LNAME.value().is(lastName_1.get()))	
							.collect(java.util.stream.Collectors.toList());	
				}
			}else if(firstName_1.isPresent() && mobilePhone_1.isPresent()){
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
							recordStream.filter(FNAME.value().is(firstName_1.get()))	
							.filter(MPHONE.value().is(mobilePhone_1.get()))	
							.collect(java.util.stream.Collectors.toList());
				}
		
			}else if(mobilePhone_1.isPresent() && lastName_1.isPresent()){
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
							recordStream.filter(LNAME.value().is(lastName_1.get()))	
							.filter(MPHONE.value().is(mobilePhone_1.get()))	
							.collect(java.util.stream.Collectors.toList());	
				}
		
			}else if(firstName_1.isPresent()){
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
							recordStream.filter(FNAME.value().is(firstName_1.get()))	
							.collect(java.util.stream.Collectors.toList());	
				}
		
			}else if(lastName_1.isPresent()){
		
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
							recordStream.filter(LNAME.value().is(lastName_1.get()))		
							.collect(java.util.stream.Collectors.toList());	
				}
		
		
		
			}else if(mobilePhone_1.isPresent()){
				try(MutableRecordStream<String> recordStream = 	writerReader.records()){
					results = 
							recordStream.filter(MPHONE.value().is(mobilePhone_1.get())).
							collect(java.util.stream.Collectors.toList());						
		
				}
		
			}
		
			java.util.ArrayList<IData> al = new java.util.ArrayList<IData>();
			//long end_time = System.nanoTime();
			long end_time = System.currentTimeMillis();
		
			pipelineCursor.insertAfter("processingTime", ""+(end_time - start_time));
			if(results!=null)
				for(Record<String> record: results){
		
					String custNo = record.getKey();
					String firstName = record.get(FNAME).get();
					String lastName = record.get(LNAME).get();
					String email = record.get(EMAIL).get(); 
					String mobilePhone = record.get(MPHONE).get();
					String homePhone = record.get(HPHONE).get();
					String address1 = record.get(ADDR1).get();
					String address2 = record.get(ADDR2).get();
					String city = record.get(CITY).get();
					String st = record.get(ST).get();
					String zip = record.get(ZIP).get();
					IData cust = IDataFactory.create();
					IDataCursor custCurso = cust.getCursor();
					custCurso.insertAfter("customerNo", custNo);
					custCurso.insertAfter("firstName", firstName);
					custCurso.insertAfter("lastName", lastName);
					custCurso.insertAfter("email", email);
					custCurso.insertAfter("mobilePhone", mobilePhone);
					custCurso.insertAfter("homePhone", homePhone);
					custCurso.insertAfter("address1", address1);
					custCurso.insertAfter("address2", address2);
					custCurso.insertAfter("city", city);
					custCurso.insertAfter("st", st);
					custCurso.insertAfter("zip", zip);
					custCurso.destroy();
					al.add(cust);
		
				}
			if(al.size()>0)
				pipelineCursor.insertAfter("customer", al.toArray());
			al.clear();
		
			//release(ds);
		
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
		// [o] record:0:required customer
		// [o] - field:0:required customerNo
		// [o] - field:0:required firstName
		// [o] - field:0:required lastName
		// [o] - field:0:required mobilePhone
		// [o] - field:0:required address1
		// [o] - field:0:required address2
		// [o] - field:0:required city
		// [o] - field:0:required zip
		// [o] - field:0:required homePhone
		// [o] - field:0:required state
		// [o] - field:0:required email
		// [o] field:0:required processingTime
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	dataSetName = IDataUtil.getString( pipelineCursor, "dataSetName" );
		String	key = IDataUtil.getString( pipelineCursor, "key" );
		// pipeline
		
		try  {
			long start_time = System.currentTimeMillis();
			Dataset<String> ds = com.gcs.tcclient.DataSetManagerFactory.getDataset(); //getDataset();
			//  Dataset<String> ds = getDatasetFromStack(); 
			
			DatasetReader<String> reader = ds.reader();
			
			Optional<Record<String>> recordOptional = reader.get(key);
			
			long end_time = System.currentTimeMillis();
			//release(ds);
			
			pipelineCursor.insertAfter("processingTime", ""+(end_time - start_time) );
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
		        IData cust = IDataFactory.create();
		        IDataCursor custCurso = cust.getCursor();
		        custCurso.insertAfter("customerNo", custNo);
		        custCurso.insertAfter("firstName", firstName);
		        custCurso.insertAfter("lastName", lastName);
		        custCurso.insertAfter("email", email);
		        custCurso.insertAfter("mobilePhone", mobilePhone);
		        custCurso.insertAfter("homePhone", homePhone);
		        custCurso.insertAfter("address1", address1);
		        custCurso.insertAfter("address2", address2);
		        custCurso.insertAfter("city", city);
		        custCurso.insertAfter("st", st);
		        custCurso.insertAfter("zip", zip);
		        custCurso.destroy();
		        pipelineCursor.insertAfter("customer", cust);
		        
		      }
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void getRecordCount (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRecordCount)>> ---
		// @sigtype java 3.5
		// [i] field:0:required firstName
		// [i] field:0:required lastName
		// [i] field:0:required mobilePhone
		// [o] record:1:required customer
		// [o] - field:0:required customerNo
		// [o] - field:0:required firstName
		// [o] - field:0:required lastName
		// [o] - field:0:required mobilePhone
		// [o] - field:0:required address1
		// [o] - field:0:required address2
		// [o] - field:0:required city
		// [o] - field:0:required zip
		// [o] - field:0:required homePhone
		// [o] - field:0:required state
		// [o] - field:0:required email
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	dataSetName = IDataUtil.getString( pipelineCursor, "dataSetName" );
		Optional<String> firstName_1 = Optional.ofNullable(IDataUtil.getString( pipelineCursor, "firstName" ));
		Optional<String> lastName_1 =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "lastName" ));
		Optional<String>mobilePhone_1 =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "mobilePhone" ));
		
		
		
		// pipeline
		
		try  {
			
			Dataset<String> ds = com.gcs.tcclient.DataSetManagerFactory.getDataset();// getDataset();
			DatasetWriterReader<String> writerReader = ds.writerReader();
			
				long con = 
								writerReader.records()
						.explain(System.out::println) // <1>
						.count();		
			
				pipelineCursor.insertAfter("count",con);
			
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static volatile DatasetManager datasetManager;
	public static volatile Dataset<String> dataset ;
	public static Stack<Dataset<String>> availDatasetStack = new Stack<Dataset<String>>();
	public static Stack<DatasetManager> availDSManagerStack = new Stack<DatasetManager>();
	 
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
	/*
	public static void loadDatasets() throws Exception{
		if(availDatasetStack.size()==0)
			for(int i =0;i<10;i++){
				dataset =	getDatasetManager().getDataset("customers", com.terracottatech.store.Type.STRING);
				availDatasetStack.push(dataset);
			}
		
	}
	public static Dataset<String> getDatasetFromStack() throws Exception{
		if(availDatasetStack.size()==0) loadDatasets() ;
		
		return availDatasetStack.pop();
	}
	public static void  release(Dataset<String> ds){
		availDatasetStack.push(ds);
		//while(availDatasetStack.size()>10) // close any datasets that are more than 10
			//availDatasetStack.pop().close();
		 
	}
	
	public static DatasetManager getDatasetManagerFromStack() throws URISyntaxException,ServiceException,StoreException{
		if( availDSManagerStack.size()==0){ //pool 10 dataset managers
			for(int i=0;i<10;i++)
			availDSManagerStack.push(getDatasetManager());
					}
	     return availDSManagerStack.pop();
		
	
	
			
			// datasetManager.destroyDataset(STORE_NAME);
	}
	
	*
	*/
	public static Dataset<String> getDataset() throws Exception{
		if(dataset!= null){
			//System.out.println("REUSE: Dataset Found in memory");
		}else {
			dataset =	getDatasetManager().getDataset("customers", com.terracottatech.store.Type.STRING);
			System.out.println("NEW: Dataset not found in memory. Creating new dataset");
		}
		return dataset;
	}
	
	public static DatasetManager getDatasetManager() throws URISyntaxException,ServiceException,StoreException{
		if(datasetManager!=null ){
			//System.out.println("REUSE: DatasetManager Found in memory");
		}else{
			String tcURL = System.getProperty("watt.tcdb.customer.uri");
			if(tcURL == null || !tcURL.startsWith("terracotta"))  
				throw new ServiceException("TCDB URL is not configured in extended settings watt.tcdb.customer.uri property");
			java.net.URI clusterUri = new java.net.URI(tcURL);
			datasetManager = DatasetManager.clustered(clusterUri).withConnectionTimeout(300,TimeUnit.SECONDS).build() ;
			System.out.println("NEW: DatasetManager not Found in memory. Building the DSM");
		}
	    return datasetManager;
				
	
	
			
			// datasetManager.destroyDataset(STORE_NAME);
	}
	// --- <<IS-END-SHARED>> ---
}

