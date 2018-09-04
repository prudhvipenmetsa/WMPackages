package TelefonicaCustomerAPI.services;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import java.util.Optional;
import com.gcs.tcclient.DataSetManagerFactory;
import com.gcs.tcclient.DataSetManagerFactory.CustAddress;
import com.gcs.tcclient.DataSetManagerFactory.CustCrossWalk;
import com.gcs.tcclient.DataSetManagerFactory.CustMaster;
import com.gcs.tcclient.DataSetManagerFactory.CustService;
import com.terracottatech.store.Cell;
import com.terracottatech.store.Dataset;
import com.terracottatech.store.DatasetReader;
import com.terracottatech.store.DatasetWriterReader;
import com.terracottatech.store.Record;
import com.terracottatech.store.definition.CellDefinition;
import com.terracottatech.store.definition.StringCellDefinition;
import com.terracottatech.store.function.BuildablePredicate;
import com.terracottatech.store.stream.MutableRecordStream;
// --- <<IS-END-IMPORTS>> ---

public final class tcdb

{
	// ---( internal utility methods )---

	final static tcdb _instance = new tcdb();

	static tcdb _newInstance() { return new tcdb(); }

	static tcdb _cast(Object o) { return (tcdb)o; }

	// ---( server methods )---




	public static final void getCustomer360 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCustomer360)>> ---
		// @sigtype java 3.5
		// [i] field:0:required CUST_NAME
		// [i] field:0:required CUST_ID
		// [i] field:0:required ADDRESS_NAME
		// [i] field:0:required CITY
		// [i] field:0:required SRC_SYSTEM
		// [i] field:0:required singleAddressOnly
		// [o] recref:0:required customers TelefonicaCustomerAPI.docTypes:Customer360
		// [o] recref:0:required status TelefonicaCustomerAPI.docTypes:Status
		IDataCursor pipelineCursor = pipeline.getCursor();
		long start_time = System.currentTimeMillis();
		
		/*
		Optional<String> CUST_NAME = Optional.ofNullable(null);//IDataUtil.getString( pipelineCursor, "CUST_NAME" ));
		Optional<String> CUST_ID =  Optional.ofNullable(""+17477911); //66032589
		Optional<String> ADDRESS_NAME = Optional.ofNullable(null);
		Optional<String> CITY = Optional.ofNullable(null);
		 */
		Optional<String> CUST_NAME = Optional.ofNullable(IDataUtil.getString( pipelineCursor, "CUST_NAME" ));
		Optional<String> CUST_ID =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "CUST_ID" ));
		Optional<String> ADDRESS_NAME =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "ADDRESS_NAME" ));
		Optional<String> SRC_SYSTEM =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "SRC_SYSTEM" ));
		Optional<String> CITY =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "CITY" ));
		Optional<String> singleAddressOnly =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "singleAddressOnly" ));
		
		java.util.List<Record<String>> multiRecs = null ;
		Optional<Record<String>> singleRec = null ;
		boolean isSingleRec =false;
		//long start_time = System.nanoTime();
		
		try{
			Dataset<String> crossWalkDS = com.gcs.tcclient.DataSetManagerFactory.getCustCrossWalkDataSet();
			DatasetWriterReader<String> crosswalkReader = crossWalkDS.writerReader();
			try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){				// add Filters
				BuildablePredicate<Record<?>>  filterPredicate = 
						addFilterPredicate(null,CustCrossWalk.SOURCE_SYSTEM,SRC_SYSTEM);
				filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID,CUST_ID);
				filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.NAME,CUST_NAME);
				filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.C_ADDRESSLINE1,ADDRESS_NAME);
				filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.C_LOCALITY,CITY);
				if(filterPredicate==null){
					IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR","No Filter conditions found in the input. Please provide at least one filter condition") );
				}else{
					multiRecs = 						
							recordStream
								.filter(filterPredicate)
								.collect(java.util.stream.Collectors.toList());					
								
					java.util.HashSet<String> custIdList = new java.util.HashSet<String>();
					java.util.Hashtable<String,java.util.HashSet<String>> custIDAddressIds = new java.util.Hashtable<String,java.util.HashSet<String>>();
					if(multiRecs!=null)
						for(Record<String> record: multiRecs){
							//CustCrossWalk.c
							String custMastId=record.get(CustCrossWalk.CUSTOMER_ID).get();
							//System.out.println("custMastId ="+ custMastId);
							custIdList.add(custMastId);
							/*java.util.HashSet<String> hs =  custIDAddressIds.get(custMastId);
							if(hs==null){
								hs = new java.util.HashSet<String>();
								custIDAddressIds.put(custMastId,hs);
							}
							hs.add(record.get(CustCrossWalk.ADDRESS_ID).get())	;	
							*/						
		
						}		
		
		
					//Now get all the customer for the given custId list				
		
					// customers.customer
					java.util.ArrayList<IData>	customerAL = new java.util.ArrayList<IData>();
					if(custIdList.size()==0){
						IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR","No customer records found for the given search criteria") );
					}else{
						IData	customers = IDataFactory.create();
						IDataCursor customersCursor = customers.getCursor();
						Dataset<Long> custDS = com.gcs.tcclient.DataSetManagerFactory.getCustMasterDataSet();
						for(String custID: custIdList){		
							DatasetReader<Long> custReader = custDS.reader();	
							IData customer = IDataFactory.create();
							IDataCursor customerCursor = customer.getCursor();							
							IDataUtil.put(customerCursor,"master",getCustomerIDataByKey(custReader,custID));
							//now get the Addresses
							//java.util.Hashtable<Long,IData>  addressAL = getAddressIDataByCustomerId(custIDAddressIds,singleAddressOnly,ADDRESS_NAME,custID) ;
							java.util.Hashtable<Long,IData>  addressAL = getAddressIDataByCustomerId(singleAddressOnly,ADDRESS_NAME,custID) ;
							if(addressAL.size()>0)//insert Addresses
								IDataUtil.put(customerCursor,"address",addressAL.values().toArray(new IData[addressAL.size()]));
							customerCursor.destroy();
							customerAL.add(customer);
						}
						if(customerAL.size()>0){
							IDataUtil.put(customersCursor,"customer",customerAL.toArray(new IData[customerAL.size()]));
							IDataUtil.put( pipelineCursor, "customers", customers );
							//pipelineCursor.insertAfter("responseTime", (end_time-start_time));
							IDataUtil.put( pipelineCursor, "status", createStatusRecord("OK","Successfully retrieved "+customerAL.size()+" customers.") );
						}else{
							IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR","No customer records found for the given search criteria") );
						}
						customersCursor.destroy();
					}
					
					long end_time = System.currentTimeMillis();
				}
				}
		
		
		
		
			}catch(Exception e){
				e.printStackTrace(); //TBD. Should be deleted beforee golive
				IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR",e.getMessage()));
		
			}
		
			long end_time = System.currentTimeMillis();
			
			pipelineCursor.destroy();
		
		
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void getCustomerCrossWalk (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCustomerCrossWalk)>> ---
		// @sigtype java 3.5
		// [i] field:0:required CUST_NAME
		// [i] field:0:required CUST_ID
		// [i] field:0:required ADDRESS_NAME
		// [i] field:0:required CITY
		// [i] field:0:required SRC_SYSTEM
		// [i] field:0:required singleAddressOnly
		// [o] field:1:required custMasterId
		IDataCursor pipelineCursor = pipeline.getCursor();
		long start_time = System.currentTimeMillis();
		
		/*
		Optional<String> CUST_NAME = Optional.ofNullable(null);//IDataUtil.getString( pipelineCursor, "CUST_NAME" ));
		Optional<String> CUST_ID =  Optional.ofNullable(""+17477911); //66032589
		Optional<String> ADDRESS_NAME = Optional.ofNullable(null);
		Optional<String> CITY = Optional.ofNullable(null);
		 */
		Optional<String> CUST_NAME = Optional.ofNullable(IDataUtil.getString( pipelineCursor, "CUST_NAME" ));
		Optional<String> CUST_ID =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "CUST_ID" ));
		Optional<String> ADDRESS_NAME =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "ADDRESS_NAME" ));
		Optional<String> SRC_SYSTEM =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "SRC_SYSTEM" ));
		Optional<String> CITY =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "CITY" ));
		Optional<String> singleAddressOnly =  Optional.ofNullable(IDataUtil.getString( pipelineCursor, "singleAddressOnly" ));
		
		java.util.List<Record<String>> multiRecs = null ;	
		try{
			Dataset<String> crossWalkDS = com.gcs.tcclient.DataSetManagerFactory.getCustCrossWalkDataSet();
			DatasetWriterReader<String> crosswalkReader = crossWalkDS.writerReader();
			BuildablePredicate<Record<?>>  filterPredicate = 
					addFilterPredicate(null,CustCrossWalk.SOURCE_SYSTEM,SRC_SYSTEM);
			filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID,CUST_ID);
			filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.NAME,CUST_NAME);
			filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.C_ADDRESSLINE1,ADDRESS_NAME);
			filterPredicate = addFilterPredicate(filterPredicate,CustCrossWalk.C_LOCALITY,CITY);
			
				
				if(filterPredicate==null){
					IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR","No Filter conditions found in the input. Please provide at least one filter condition") );
					pipelineCursor.destroy();
					return;
				}else{
					try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){				// add Filters
					multiRecs = 						
							recordStream
								.filter(filterPredicate)
								.collect(java.util.stream.Collectors.toList());					
					}
			}
			java.util.HashSet<String> custIdList = new java.util.HashSet<String>();
			java.util.Hashtable<String,java.util.HashSet<String>> custIDAddressIds = new java.util.Hashtable<String,java.util.HashSet<String>>();
			if(multiRecs!=null)
				for(Record<String> record: multiRecs){
					//CustCrossWalk.c
					String custMastId=record.get(CustCrossWalk.CUSTOMER_ID).get();
					//System.out.println("custMastId ="+ custMastId);
					custIdList.add(custMastId);
		
				}		
		
			if(custIdList.size()==0){
				IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR","No customer records found for the given search criteria") );
			}else{
				IDataUtil.put( pipelineCursor, "custMasterId",custIdList.toArray(new String[custIdList.size()]));
				IDataUtil.put( pipelineCursor, "status", createStatusRecord("OK", custIdList.size() +" customer records found") );
			}
			
			long end_time = System.currentTimeMillis();
		
		
		
		
			}catch(Exception e){
				e.printStackTrace(); //TBD. Should be deleted beforee golive
				IDataUtil.put( pipelineCursor, "status", createStatusRecord("ERROR",e.getMessage()));
		
			}
		
			long end_time = System.currentTimeMillis();
			
			pipelineCursor.destroy();
		
		
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void pushCustomer360 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(pushCustomer360)>> ---
		// @sigtype java 3.5
		// [i] recref:0:required MDMCustomer360 TelefonicaMDM.docs:CUSTOMER_HIERARCHY
		// [o] field:0:required responseTime
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		long start_time = System.nanoTime();
		try{
			// MDMCustomer360
			IData	MDMCustomer360 = IDataUtil.getIData( pipelineCursor, "MDMCustomer360" );
			if ( MDMCustomer360 != null)
			{
				IDataCursor MDMCustomer360Cursor = MDMCustomer360.getCursor();
		
				// i.CUSTOMER_MASTER
				IData	CUSTOMER_MASTER = IDataUtil.getIData( MDMCustomer360Cursor, "CUSTOMER_MASTER" );
				if ( CUSTOMER_MASTER != null)
				{
					IDataCursor CUSTOMER_MASTERCursor = CUSTOMER_MASTER.getCursor();
		
					// i.datarow
					IData[]	datarow = IDataUtil.getIDataArray( CUSTOMER_MASTERCursor, "datarow" );
					if ( datarow != null)
					{ 
		
						
						
						Dataset<Long> custMasterDS = DataSetManagerFactory.getCustMasterDataSet();
						DatasetWriterReader<Long> custWriter = custMasterDS.writerReader();								
						// AsyncDatasetWriterReader<String> asyncAccess 		 = writerReader.async(); // <1>
						for ( int i = 0; i < datarow.length; i++ )
						{
							IDataCursor datarowCursor = datarow[i].getCursor();
							String	NAME = IDataUtil.getString( datarowCursor, "NAME" );
							String	LONGNAME = IDataUtil.getString( datarowCursor, "LONGNAME" );
							String	SHORTNAME = IDataUtil.getString( datarowCursor, "SHORTNAME" );
							String	SURNAME1 = IDataUtil.getString( datarowCursor, "SURNAME1" );
							String	SURNAME2 = IDataUtil.getString( datarowCursor, "SURNAME2" );
							String	CUSTOMER_ID = IDataUtil.getString( datarowCursor, "CUSTOMER_ID" );
		
							if(SHORTNAME==null) SHORTNAME="";
							if(SURNAME1==null) SURNAME1="";
							if(SURNAME2==null) SURNAME2="";
		
		
							custWriter.add(Long.parseLong(CUSTOMER_ID), 
									CustMaster.CUST_NAME.newCell(NAME),
									CustMaster.CUST_LNG_NAME.newCell(LONGNAME),
									CustMaster.CUST_SHRT_NAME.newCell(SHORTNAME),
									CustMaster.CUST_SURNM1.newCell(SURNAME1),
									CustMaster.CUST_SURNM2.newCell(SURNAME2));
		
		
							// i_1.CUSTOMER_CROSSWALK
							IData	CUSTOMER_CROSSWALK = IDataUtil.getIData( datarowCursor, "CUSTOMER_CROSSWALK" );
							if ( CUSTOMER_CROSSWALK != null)
							{  	
							IDataCursor CUSTOMER_CROSSWALKCursor = CUSTOMER_CROSSWALK.getCursor();
		
							// i_1.datarow
							IData[]	cwDataRows = IDataUtil.getIDataArray( CUSTOMER_CROSSWALKCursor, "datarow" );
							if ( cwDataRows != null)
							{ 
								Dataset custCrosswalkDS = DataSetManagerFactory.getCustCrossWalkDataSet();
								DatasetWriterReader<String> crosswalkWriter = custCrosswalkDS.writerReader();
								for ( int i_1 = 0; i_1 < cwDataRows.length; i_1++ )
								{
									IDataCursor cwDataRowsCursor = cwDataRows[i_1].getCursor();
									String	SOURCE_SYSTEM = IDataUtil.getString( cwDataRowsCursor, "SOURCE_SYSTEM" );
									String	SOURCE_SYSTEM_CUSTOMER_ID = IDataUtil.getString( cwDataRowsCursor, "SOURCE_SYSTEM_CUSTOMER_ID" );
									String	CUST_STG_ID = IDataUtil.getString( cwDataRowsCursor, "CUST_STG_ID" );
		
									
		
									
		
									// AsyncDatasetWriterReader<String> asyncAccess 		 = writerReader.async(); // <1>
									java.util.ArrayList<Cell<String>> cwAl= new java.util.ArrayList<Cell<String>>();
									
									addToCells(cwAl,CustCrossWalk.CUSTOMER_ID,IDataUtil.getString( cwDataRowsCursor, "CUSTOMER_ID" ));
									addToCells(cwAl,CustCrossWalk.ADDRESS_ID,IDataUtil.getString( cwDataRowsCursor, "ADDRESS_ID" ));
									addToCells(cwAl,CustCrossWalk.NAME,IDataUtil.getString( cwDataRowsCursor, "NAME" ));	
									addToCells(cwAl,CustCrossWalk.SOURCE_SYSTEM,SOURCE_SYSTEM);
									addToCells(cwAl,CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID,SOURCE_SYSTEM_CUSTOMER_ID);
									//addToCells(cwAl,CustCrossWalk.ADDRESSLINE1, IDataUtil.getString( cwDataRowsCursor, "ADDRESSLINE1" ));
									//addToCells(cwAl,CustCrossWalk.ADDRESSLINE2,IDataUtil.getString( cwDataRowsCursor, "ADDRESSLINE2" ));
									//addToCells(cwAl,CustCrossWalk.LOCALITY,IDataUtil.getString( cwDataRowsCursor, "LOCALITY" ));
									//addToCells(cwAl,CustCrossWalk.POSTALCODE,IDataUtil.getString( cwDataRowsCursor, "POSTALCODE" ));
									//addToCells(cwAl,CustCrossWalk.ADMINISTRATIVEAREA,IDataUtil.getString( cwDataRowsCursor, "ADMINISTRATIVEAREA" ));
									addToCells(cwAl,CustCrossWalk.C_ADDRESSLINE1,IDataUtil.getString( cwDataRowsCursor, "C_ADDRESSLINE1" ));
									//addToCells(cwAl,CustCrossWalk.C_ADDRESSLINE2,IDataUtil.getString( cwDataRowsCursor, "C_ADDRESSLINE2" ));
									//addToCells(cwAl,CustCrossWalk.C_ADMINISTRATIVEAREA,IDataUtil.getString( cwDataRowsCursor, "C_ADMINISTRATIVEAREA" ));
									//addToCells(cwAl,CustCrossWalk.C_COUNTRY,IDataUtil.getString( cwDataRowsCursor, "C_COUNTRY" ));
									//addToCells(cwAl,CustCrossWalk.C_GEO_DISTANCE,IDataUtil.getString( cwDataRowsCursor, "C_GEO_DISTANCE" ));
									//addToCells(cwAl,CustCrossWalk.C_LATTITUDE,IDataUtil.getString( cwDataRowsCursor, "C_LATTITUDE" ));
									addToCells(cwAl,CustCrossWalk.C_LOCALITY,IDataUtil.getString( cwDataRowsCursor, "C_LOCALITY" ));
									//addToCells(cwAl,CustCrossWalk.C_LONGITUDE,IDataUtil.getString( cwDataRowsCursor, "C_LONGITUDE" ));
									addToCells(cwAl,CustCrossWalk.C_POSTALCODE,IDataUtil.getString( cwDataRowsCursor, "C_POSTALCODE" ));
									//addToCells(cwAl,CustCrossWalk.CONTRACT_ID,IDataUtil.getString( cwDataRowsCursor, "CONTRACT_ID" ));
									//addToCells(cwAl,CustCrossWalk.SERVICE_ID,IDataUtil.getString( cwDataRowsCursor, "SERVICE_ID" ));
									
								crosswalkWriter.add(CUST_STG_ID, 																					
											(Cell[])cwAl.toArray(new Cell[cwAl.size()]));
									cwDataRowsCursor.destroy(); 									
									//System.out.println(CUST_STG_ID + "   " +cwAl);
									cwAl.clear();
		
								}
							}
							CUSTOMER_CROSSWALKCursor.destroy();
							}
		
							// i_2.CUSTOMER_ADDRESS
							IData	CUSTOMER_ADDRESS = IDataUtil.getIData( datarowCursor, "CUSTOMER_ADDRESS" );
							if ( CUSTOMER_ADDRESS != null)
							{
								IDataCursor CUSTOMER_ADDRESSCursor = CUSTOMER_ADDRESS.getCursor();
								Dataset custAddressDS = DataSetManagerFactory.getAddressDataSet();
								DatasetWriterReader<Long> addressWriter = custAddressDS.writerReader();
								// AsyncDatasetWriterReader<String> asyncAccess 		 = writerReader.async(); // <1>
		
								// i_2.datarow
								IData[]	datarow_2 = IDataUtil.getIDataArray( CUSTOMER_ADDRESSCursor, "datarow" );
								if ( datarow_2 != null)
								{
									for ( int i_2 = 0; i_2 < datarow_2.length; i_2++ )
									{
										IDataCursor datarow_2Cursor = datarow_2[i_2].getCursor();
		
										String	ADDRESS_ID_1 = IDataUtil.getString( datarow_2Cursor, "ADDRESS_ID" );
										java.util.ArrayList<Cell<String>> addrAl= new java.util.ArrayList<Cell<String>>();
										addToCells(addrAl,CustAddress.CUST_ID,IDataUtil.getString( datarow_2Cursor, "CUSTOMER_ID" ));
										addToCells(addrAl,CustAddress.CUST_ADDR_ID,IDataUtil.getString( datarow_2Cursor, "SERVICE_ID" ));
										addToCells(addrAl,CustAddress.ADDRESSLINE1,IDataUtil.getString( datarow_2Cursor, "ADDRESSLINE1" ));
										addToCells(addrAl,CustAddress.ADDRESSLINE2,IDataUtil.getString( datarow_2Cursor, "ADDRESSLINE2" ));
										addToCells(addrAl,CustAddress.ADMINISTRATIVEAREA,IDataUtil.getString( datarow_2Cursor, "ADMINISTRATIVEAREA" ));
										addToCells(addrAl,CustAddress.LOCALITY,IDataUtil.getString( datarow_2Cursor, "LOCALITY" ));
										addToCells(addrAl,CustAddress.POSTALCODE,IDataUtil.getString( datarow_2Cursor, "POSTALCODE" ));
										addToCells(addrAl,CustAddress.COUNTRY,IDataUtil.getString( datarow_2Cursor, "COUNTRY" ));
										addToCells(addrAl,CustAddress.CUST_ADDR_TYPE,IDataUtil.getString( datarow_2Cursor, "ADDRESSTYPE" ));																	
										addToCells(addrAl,CustAddress.LATTITUDE,IDataUtil.getString( datarow_2Cursor, "LATTITUDE" ));
										addToCells(addrAl,CustAddress.LONGITUDE,IDataUtil.getString( datarow_2Cursor, "LONGITUDE" ));
										addressWriter.add(Long.parseLong(ADDRESS_ID_1),
												(Cell[])addrAl.toArray(new Cell[addrAl.size()]));
										addrAl.clear();
		
		
		
		
		
		
										// i_3.CUSTOMER_CONTRACTS
										IData	CUSTOMER_CONTRACTS = IDataUtil.getIData( datarow_2Cursor, "CUSTOMER_CONTRACTS" );
										if ( CUSTOMER_CONTRACTS != null)
										{  Dataset custServiceDS = DataSetManagerFactory.getServiceDataSet();
										DatasetWriterReader<Long> serviceWriter = custServiceDS.writerReader();
										// AsyncDatasetWriterReader<String> asyncAccess 		 = writerReader.async(); // <1>
										IDataCursor CUSTOMER_CONTRACTSCursor = CUSTOMER_CONTRACTS.getCursor();
		
										// i_3.datarow
										IData[]	datarow_3 = IDataUtil.getIDataArray( CUSTOMER_CONTRACTSCursor, "datarow" );
										if ( datarow_3 != null)
										{
											for ( int i_3 = 0; i_3 < datarow_3.length; i_3++ )
											{
												IDataCursor datarow_3Cursor = datarow_3[i_3].getCursor();
												//String	CONTRACT_ID_1 = IDataUtil.getString( datarow_3Cursor, "CONTRACT_ID" );
												java.util.ArrayList<Cell<String>> al = new java.util.ArrayList<Cell<String>>();
												String	SERVICE_ID_1 = IDataUtil.getString( datarow_3Cursor, "SERVICE_ID" );
												String	CUSTOMER_ID_1 = IDataUtil.getString( datarow_3Cursor, "CUSTOMER_ID" );
												String	ADDRESS_ID_2 = IDataUtil.getString( datarow_3Cursor, "ADDRESS_ID" );
												String svcKey = CUSTOMER_ID_1+SERVICE_ID_1+ADDRESS_ID_2;
												addToCells(al,CustService.CUSTOMER_ID, CUSTOMER_ID_1);
												addToCells(al,CustService.CUST_ADDR_ID,ADDRESS_ID_2);
												addToCells(al,CustService.SERVICE_TYPE,IDataUtil.getString( datarow_3Cursor, "SERVICE_TYPE" ));
												addToCells(al,CustService.SERVICE_TYPE_CODE,IDataUtil.getString( datarow_3Cursor, "SERVICE_TYPE_CODE" ));
												addToCells(al,CustService.SERVICE_SOURCE_SYSTEM,IDataUtil.getString( datarow_3Cursor, "SERVICE_SOURCE_SYSTEM" ));
												addToCells(al,CustService.SOURCE_SYSTEM_SERVICE_ID, IDataUtil.getString( datarow_3Cursor, "SOURCE_SYSTEM_SERVICE_ID" ));
												addToCells(al,CustService.SERVICE_DESCRIPTION,IDataUtil.getString( datarow_3Cursor, "SERVICE_DESCRIPTION" ));
												addToCells(al,CustService.SERVICE_CODE,IDataUtil.getString( datarow_3Cursor, "SERVICE_CODE" ));
												addToCells(al,CustService.SERVICE_START_DATE,IDataUtil.getString( datarow_3Cursor, "SERVICE_START_DATE" ));
												addToCells(al,CustService.SERVICE_END_DATE,IDataUtil.getString( datarow_3Cursor, "SERVICE_END_DATE" ));
												addToCells(al,CustService.READY_RECKONER,IDataUtil.getString( datarow_3Cursor, "READY_RECKONER" ));
												addToCells(al,CustService.INSTALLED_INDICATOR,IDataUtil.getString( datarow_3Cursor, "INSTALLED_INDICATOR" ));
												addToCells(al,CustService.CONFIDENTIAL_INDICATOR,IDataUtil.getString( datarow_3Cursor, "CONFIDENTIAL_INDICATOR" ));
												addToCells(al,CustService.FAMILY_SERVICE_CODE,IDataUtil.getString( datarow_3Cursor, "FAMILY_SERVICE_CODE" ));
												addToCells(al,CustService.CATALOG_SERVICE_CODE,IDataUtil.getString( datarow_3Cursor, "CATALOG_SERVICE_CODE" ));
												addToCells(al,CustService.CATALOG_INDICATOR,IDataUtil.getString( datarow_3Cursor, "CATALOG_INDICATOR" ));
												addToCells(al,CustService.MODIFY_USER,IDataUtil.getString( datarow_3Cursor, "MODIFY_USER" ));
												addToCells(al,CustService.MODIFY_DATE,IDataUtil.getString( datarow_3Cursor, "MODIFY_DATE" ));
												datarow_3Cursor.destroy();
		
		
												serviceWriter.add(Long.parseLong(svcKey), 
														(Cell[])al.toArray(new Cell[al.size()]));
												al.clear();
		
		
											}
										}
										CUSTOMER_CONTRACTSCursor.destroy();
										}
										datarow_2Cursor.destroy();
									}
								}
								CUSTOMER_ADDRESSCursor.destroy();
							}
							datarowCursor.destroy();
						}
					}
					CUSTOMER_MASTERCursor.destroy();
				}
		
		
				MDMCustomer360Cursor.destroy();
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(e);
		}
		long end_time = System.nanoTime();
		pipelineCursor.insertAfter("responseTime", (end_time-start_time));
		pipelineCursor.destroy();
		
		// pipeline
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	public static void addToCells(java.util.ArrayList<Cell<String>> al, CellDefinition<String> cell,String value){
		if(value!=null)
			al.add(cell.newCell(value));
	}
	public static IData createStatusRecord(String ERR_CODE,String ERR_MESG){
		IData	status = IDataFactory.create();
		IDataCursor statusCursor = status.getCursor();
		IDataUtil.put( statusCursor, "STATUS_CODE", ERR_CODE);
		IDataUtil.put( statusCursor, "STATUS_MESG", ERR_MESG );
		statusCursor.destroy();
		return status;
	}
	public static java.util.Hashtable<Long,IData> getAddressIDataByCustomerId(Optional<String> singleAddressOnly,Optional<String> ADDRESS_NAME,String custID)  throws Exception{
		
		java.util.Hashtable<Long,IData> addressAL=new java.util.Hashtable<Long,IData>();
		Dataset<Long> addressDS = com.gcs.tcclient.DataSetManagerFactory.getAddressDataSet();
		DatasetWriterReader<Long> addressReader = addressDS.writerReader();
			
		try(MutableRecordStream<Long> addressStream = 	addressReader.records()){
			// get all adddressf or the given customerID.
			boolean singleAddress =false;
	
			if(singleAddressOnly.isPresent() && singleAddressOnly.get().equals("true")){
				singleAddress=true;
			}
			BuildablePredicate<Record<?>>  filterPredicate = addFilterPredicate(null,CustAddress.CUST_ID, Optional.ofNullable(custID));
			
			
			if(singleAddress && ADDRESS_NAME.isPresent())
				filterPredicate = addFilterPredicate(filterPredicate,CustAddress.ADDRESSLINE1,ADDRESS_NAME);
	
			java.util.List<Record<Long>> addressRecs = addressStream.filter(filterPredicate).collect(java.util.stream.Collectors.toList());			
			if(addressRecs!=null){			
							
				for(Record<Long> addrRec: addressRecs){					
					IData	address = IDataFactory.create();
					IDataCursor addressCursor = address.getCursor();
					String CUST_ADDR_ID = ""+addrRec.getKey();
					IDataUtil.put( addressCursor, "CUST_ADDR_ID",""+CUST_ADDR_ID );
					IDataUtil.put( addressCursor, "ADDRESSLINE1", addrRec.get(CustAddress.ADDRESSLINE1).get() );
					IDataUtil.put( addressCursor, "ADDRESSLINE2", addrRec.get(CustAddress.ADDRESSLINE2).get() );
					IDataUtil.put( addressCursor, "ADMINISTRATIVEAREA", addrRec.get(CustAddress.ADMINISTRATIVEAREA).get() );
					IDataUtil.put( addressCursor, "LOCALITY", addrRec.get(CustAddress.LOCALITY).get() );
					IDataUtil.put( addressCursor, "POSTALCODE",  addrRec.get(CustAddress.POSTALCODE).get() );
					IDataUtil.put( addressCursor, "COUNTRY",  addrRec.get(CustAddress.COUNTRY).get() );
					IDataUtil.put( addressCursor, "CUST_ID",  addrRec.get(CustAddress.CUST_ID).get() );
					IDataUtil.put( addressCursor, "LONGITUDE",  addrRec.get(CustAddress.LONGITUDE).get() );
					IDataUtil.put( addressCursor, "LATTITUDE",  addrRec.get(CustAddress.LATTITUDE).get() );
					if(addrRec.get(CustAddress.CUST_ADDR_EMAIL).isPresent())
						IDataUtil.put( addressCursor, "CUST_ADDR_EMAIL",  addrRec.get(CustAddress.CUST_ADDR_EMAIL).get() );
					if(addrRec.get(CustAddress.CUST_ADDR_PHONE_NUMBER).isPresent())
						IDataUtil.put( addressCursor, "CUST_ADDR_PHONE_NUMBER", addrRec.get(CustAddress.CUST_ADDR_PHONE_NUMBER).get() );
					if(addrRec.get(CustAddress.CUST_ADDR_FAX_NUMBER).isPresent())
						IDataUtil.put( addressCursor, "CUST_ADDR_FAX_NUMBER",  addrRec.get(CustAddress.CUST_ADDR_FAX_NUMBER).get() );
					IDataUtil.put( addressCursor, "CUST_ADDR_TYPE", addrRec.get(CustAddress.CUST_ADDR_TYPE).get() );
					
					//now get all the services that belong
					java.util.Hashtable<Long,IData> services =			 getServicesIDataByAddressId( custID, CUST_ADDR_ID);
					if(services.size()>0)//insert services to each address
							IDataUtil.put(addressCursor,"service",services.values().toArray(new IData[services.size()]));			
					addressCursor.destroy();					
					addressAL.put(addrRec.getKey(), address);
					}
				}
			}
		return addressAL;
	
	
	}
	public static BuildablePredicate<Record<?>>  addFilterPredicate( BuildablePredicate<Record<?>>  existPred, StringCellDefinition  cellDef,Optional<String> celVal){ 
		if(celVal.isPresent()){
			BuildablePredicate<Record<?>> newpred= cellDef.value().is(celVal.get());
			if(existPred!=null)  // add to existing predicate
				newpred = existPred.and(newpred);
			return newpred;
		}else{ //no filter value exists. donot add the predicate
	
			return existPred;	
	
		}
	
	}
	public static IDataCursor createAddressIData(IDataCursor addressCursor ,String CUST_ADDR_ID,Record<Long> addrRec ){
		IDataUtil.put( addressCursor, "CUST_ADDR_ID",""+CUST_ADDR_ID );
		IDataUtil.put( addressCursor, "ADDRESSLINE1", addrRec.get(CustAddress.ADDRESSLINE1).get() );
		IDataUtil.put( addressCursor, "ADDRESSLINE2", addrRec.get(CustAddress.ADDRESSLINE2).get() );
		IDataUtil.put( addressCursor, "ADMINISTRATIVEAREA", addrRec.get(CustAddress.ADMINISTRATIVEAREA).get() );
		IDataUtil.put( addressCursor, "LOCALITY", addrRec.get(CustAddress.LOCALITY).get() );
		IDataUtil.put( addressCursor, "POSTALCODE",  addrRec.get(CustAddress.POSTALCODE).get() );
		IDataUtil.put( addressCursor, "COUNTRY",  addrRec.get(CustAddress.COUNTRY).get() );
		IDataUtil.put( addressCursor, "CUST_ID",  addrRec.get(CustAddress.CUST_ID).get() );
		IDataUtil.put( addressCursor, "LONGITUDE",  addrRec.get(CustAddress.LONGITUDE).get() );
		IDataUtil.put( addressCursor, "LATTITUDE",  addrRec.get(CustAddress.LATTITUDE).get() );
		if(addrRec.get(CustAddress.CUST_ADDR_EMAIL).isPresent())
			IDataUtil.put( addressCursor, "CUST_ADDR_EMAIL",  addrRec.get(CustAddress.CUST_ADDR_EMAIL).get() );
		if(addrRec.get(CustAddress.CUST_ADDR_PHONE_NUMBER).isPresent())
			IDataUtil.put( addressCursor, "CUST_ADDR_PHONE_NUMBER", addrRec.get(CustAddress.CUST_ADDR_PHONE_NUMBER).get() );
		if(addrRec.get(CustAddress.CUST_ADDR_FAX_NUMBER).isPresent())
			IDataUtil.put( addressCursor, "CUST_ADDR_FAX_NUMBER",  addrRec.get(CustAddress.CUST_ADDR_FAX_NUMBER).get() );
		IDataUtil.put( addressCursor, "CUST_ADDR_TYPE", addrRec.get(CustAddress.CUST_ADDR_TYPE).get() );
		return addressCursor;
	}
	public static java.util.Hashtable<Long,IData> getAddressIDataByCustomerId(java.util.Hashtable<String,java.util.HashSet<String>> custIDAddressIds,Optional<String> singleAddressOnly,Optional<String> ADDRESS_NAME,String custID)  throws Exception{
	
		java.util.Hashtable<Long,IData> addressAL=new java.util.Hashtable<Long,IData>();
		Dataset<Long> addressDS = com.gcs.tcclient.DataSetManagerFactory.getAddressDataSet();
		DatasetWriterReader<Long> addressReader = addressDS.writerReader();
	
		if(custIDAddressIds != null && custIDAddressIds.size() > 0 && (custIDAddressIds.get(custID) != null)){
				java.util.HashSet<String> addresIdList = custIDAddressIds.get(custID);
				for(String addrId :addresIdList){
					Optional<Record<Long>> addRec = addressReader.get(Long.parseLong(addrId));
					if(addRec.isPresent()){
						IData	address = IDataFactory.create();
						IDataCursor addressCursor = address.getCursor();					
						addressCursor = createAddressIData( addressCursor ,addrId, addRec.get() );
						//now get all the services that belong
						java.util.Hashtable<Long,IData> services =			 getServicesIDataByAddressId( custID, addrId);
						if(services.size()>0)//insert services to each address
							IDataUtil.put(addressCursor,"service",services.values().toArray(new IData[services.size()]));			
						addressCursor.destroy();					
						addressAL.put(Long.parseLong(addrId), address);
					}
				}
		}else{
				try(MutableRecordStream<Long> addressStream = 	addressReader.records()){
				// get all adddressf or the given customerID.
								
				BuildablePredicate<Record<?>>  filterPredicate = addFilterPredicate(null,CustAddress.CUST_ID, Optional.ofNullable(custID));
	
				if(singleAddressOnly.isPresent() && singleAddressOnly.get().equals("true")  && ADDRESS_NAME.isPresent()){
					filterPredicate = addFilterPredicate(filterPredicate,CustAddress.ADDRESSLINE1,ADDRESS_NAME);
				}
				
					
	
				java.util.List<Record<Long>> addressRecs = addressStream.filter(filterPredicate).collect(java.util.stream.Collectors.toList());			
				if(addressRecs!=null){			
	
					for(Record<Long> addrRec: addressRecs){					
						IData	address = IDataFactory.create();
						IDataCursor addressCursor = address.getCursor();
						String CUST_ADDR_ID = ""+addrRec.getKey();
						
						addressCursor = createAddressIData( addressCursor ,CUST_ADDR_ID, addrRec );
						//now get all the services that belong
						java.util.Hashtable<Long,IData> services =			 getServicesIDataByAddressId( custID, CUST_ADDR_ID);
						if(services.size()>0)//insert services to each address
							IDataUtil.put(addressCursor,"service",services.values().toArray(new IData[services.size()]));			
						addressCursor.destroy();					
						addressAL.put(addrRec.getKey(), address);
					}
				}
			}
		}
		return addressAL;
	
	
	}
	public static IData getCustomerIDataByKey(DatasetReader custReader,String custID ){
		IData	master = IDataFactory.create();
		IDataCursor masterCursor = master.getCursor();
		Optional<Record<Long>> custRec = custReader.get(Long.parseLong(custID));
		IDataUtil.put( masterCursor, "ID",  custRec.get().getKey() );
		IDataUtil.put( masterCursor, "CUST_NAME", custRec.get().get(CustMaster.CUST_NAME).get());
		IDataUtil.put( masterCursor, "CUST_LNG_NAME", custRec.get().get(CustMaster.CUST_LNG_NAME).get());
		IDataUtil.put( masterCursor, "CUST_SHRT_NAME", custRec.get().get(CustMaster.CUST_SHRT_NAME).get());
		IDataUtil.put( masterCursor, "CUST_SURNM1", custRec.get().get(CustMaster.CUST_SURNM1).get());
		IDataUtil.put( masterCursor, "CUST_SURNM2", custRec.get().get(CustMaster.CUST_SURNM2).get());
		//IDataUtil.put( masterCursor, "SURVIVORSHIP_DETAILS", "SURVIVORSHIP_DETAILS" );
		masterCursor.destroy();
		return master;
	}
	public static java.util.Hashtable<Long,IData> getServicesIDataByAddressId(String custID,String CUST_ADDR_ID) throws Exception{
		//getservices for each address
		Dataset<Long> servicesDS = com.gcs.tcclient.DataSetManagerFactory.getServiceDataSet();
		DatasetWriterReader<Long> servicesReader = servicesDS.writerReader();	
		java.util.Hashtable<Long,IData> servicesAL=new java.util.Hashtable<Long,IData>();
		try(MutableRecordStream<Long> servicesStream = 	servicesReader.records()){
			// get all adddressf or the given customerID.
			java.util.List<Record<Long>> serviceRecs = 		
					servicesStream
					.filter(CustService.CUSTOMER_ID.value().is(custID))
					.filter(CustService.CUST_ADDR_ID.value().is(CUST_ADDR_ID))
					.collect(java.util.stream.Collectors.toList());				
			for(Record<Long> svcRec: serviceRecs){
				IData	service = IDataFactory.create();
				IDataCursor serviceCursor = service.getCursor();
				IDataUtil.put( serviceCursor, "SRVC_ID",svcRec.getKey()+"" );
				if(svcRec.get(CustService.SERVICE_TYPE).isPresent()) 
					IDataUtil.put( serviceCursor, "SRVC_TYP", svcRec.get(CustService.SERVICE_TYPE).get() );
				if(svcRec.get(CustService.SERVICE_TYPE_CODE).isPresent()) 
					IDataUtil.put( serviceCursor, "SRVC_TYP_CD",svcRec.get(CustService.SERVICE_TYPE_CODE).get() );
				if(svcRec.get(CustService.SERVICE_SOURCE_SYSTEM).isPresent())
					IDataUtil.put( serviceCursor, "SRC_SYS_ID", svcRec.get(CustService.SERVICE_SOURCE_SYSTEM).get() );
				IDataUtil.put( serviceCursor, "SRC_SYS_SRV_CD", svcRec.get(CustService.SOURCE_SYSTEM_SERVICE_ID).get() );
				IDataUtil.put( serviceCursor, "SRVC_CD", svcRec.get(CustService.SERVICE_CODE).get() );
				IDataUtil.put( serviceCursor, "SRVC_DESC", svcRec.get(CustService.SERVICE_DESCRIPTION).get() );
				if(svcRec.get(CustService.READY_RECKONER).isPresent())
					IDataUtil.put( serviceCursor, "READY_RECKONER",svcRec.get(CustService.READY_RECKONER).get() );
				if(svcRec.get(CustService.INSTALLED_INDICATOR).isPresent())
					IDataUtil.put( serviceCursor, "INSTALLED_INDICATOR", svcRec.get(CustService.INSTALLED_INDICATOR).get() );
				if(svcRec.get(CustService.CONFIDENTIAL_INDICATOR).isPresent())
					IDataUtil.put( serviceCursor, "CONFIDENTIAL_INDICATOR", svcRec.get(CustService.CONFIDENTIAL_INDICATOR).get() );
				if(svcRec.get(CustService.FAMILY_SERVICE_CODE).isPresent())
					IDataUtil.put( serviceCursor, "FAMILY_SERVICE_CODE", svcRec.get(CustService.FAMILY_SERVICE_CODE).get() );
				if(svcRec.get(CustService.CATALOG_SERVICE_CODE).isPresent())
					IDataUtil.put( serviceCursor, "CATALOG_SERVICE_CODE",svcRec.get(CustService.CATALOG_SERVICE_CODE).get() );
				if(svcRec.get(CustService.CATALOG_INDICATOR).isPresent())
					IDataUtil.put( serviceCursor, "CATALOG_INDICATOR",svcRec.get(CustService.CATALOG_INDICATOR).get() );
				if(svcRec.get(CustService.MODIFY_USER).isPresent())
					IDataUtil.put( serviceCursor, "MODIFY_USR", svcRec.get(CustService.MODIFY_USER).get() );
				if(svcRec.get(CustService.SERVICE_START_DATE).isPresent())
					IDataUtil.put( serviceCursor, "SRVC_STRT_DT", svcRec.get(CustService.SERVICE_START_DATE).get() );
				if(svcRec.get(CustService.SERVICE_END_DATE).isPresent())
					IDataUtil.put( serviceCursor, "SRVC_END_DT", svcRec.get(CustService.SERVICE_END_DATE).get() );
				if(svcRec.get(CustService.MODIFY_DATE).isPresent())
					IDataUtil.put( serviceCursor, "MODIFY_DT", svcRec.get(CustService.MODIFY_DATE).get() );
				if(svcRec.get(CustService.MODIFY_USER).isPresent())
					IDataUtil.put( serviceCursor, "CNTRCT_ID",svcRec.get(CustService.MODIFY_USER).get() );
				serviceCursor.destroy();
				servicesAL.put(svcRec.getKey(),service);
				}
		}
		return servicesAL;
	}
	// --- <<IS-END-SHARED>> ---
}

