package TelefonicaCustomerAPI.tcdb;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.terracottatech.store.Cell;
import com.terracottatech.store.Dataset;
import com.terracottatech.store.DatasetReader;
import com.terracottatech.store.DatasetWriterReader;
import com.terracottatech.store.Record;
import com.terracottatech.store.StoreException;
import java.util.Optional;
import com.gcs.tcclient.DataSetManagerFactory;
import com.gcs.tcclient.DataSetManagerFactory.CustAddress;
import com.gcs.tcclient.DataSetManagerFactory.CustCrossWalk;
import com.gcs.tcclient.DataSetManagerFactory.CustMaster;
import com.gcs.tcclient.DataSetManagerFactory.CustService;
// --- <<IS-END-IMPORTS>> ---

public final class telefonica

{
	// ---( internal utility methods )---

	final static telefonica _instance = new telefonica();

	static telefonica _newInstance() { return new telefonica(); }

	static telefonica _cast(Object o) { return (telefonica)o; }

	// ---( server methods )---




	public static final void createDataSets (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createDataSets)>> ---
		// @sigtype java 3.5
		try{
			String tcURL = System.getProperty("watt.tcdb.customer.uri");
			
			
			
			if(tcURL == null || !tcURL.startsWith("terracotta"))  
				throw new ServiceException("TCDB URL is not configured in extended settings watt.tcdb.customer.uri property");
			com.gcs.tcclient.DataSetManagerFactory.tcURL=tcURL;
			com.gcs.tcclient.DataSetManagerFactory.timeout=300;			
			com.gcs.tcclient.DataSetManagerFactory.createCustCrossWalkDataSet();
			com.gcs.tcclient.DataSetManagerFactory.createCustMasterDataSet();
			com.gcs.tcclient.DataSetManagerFactory.createAddressDataSet();
			com.gcs.tcclient.DataSetManagerFactory.createServiceDataSet();
			
			System.out.println("NEW: CREATING DatasetManager while package startup");
		}catch(Exception e){
			System.out.println("Excpetion: DSM" + e.getCause().getMessage()+ "*********" + e.getMessage());
			
			e.printStackTrace();
			throw new ServiceException(e);
		}
		// --- <<IS-END>> ---

                
	}



	public static final void getCustomerMaster (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCustomerMaster)>> ---
		// @sigtype java 3.5
		// [i] field:0:required CUST_ID
		// [o] record:0:required master
		// [o] - field:0:required ID
		// [o] - field:0:required CUST_NAME
		// [o] - field:0:required CUST_LNG_NAME
		// [o] - field:0:required CUST_SHRT_NAME
		// [o] - field:0:required CUST_SURNM1
		// [o] - field:0:required CUST_SURNM2
		// [o] - field:0:required SURVIVORSHIP_DETAILS
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		String	key = IDataUtil.getString( pipelineCursor, "CUST_ID" );
		// pipeline
		
		try  {
			long start_time = System.currentTimeMillis();
			Dataset<Long> ds = com.gcs.tcclient.DataSetManagerFactory.getCustMasterDataSet(); //getDataset();
			//  Dataset<String> ds = getDatasetFromStack(); 
			
			DatasetReader<Long> reader = ds.reader();
			
			Optional<Record<Long>> recordOptional = reader.get(Long.parseLong(key));
			
			long end_time = System.currentTimeMillis();
			//release(ds);
			
			pipelineCursor.insertAfter("processingTime", ""+(end_time - start_time) );
		      if (recordOptional.isPresent()) {
		
		    	
		
		    	// master
		    	IData	master = IDataFactory.create();
		    	IDataCursor masterCursor = master.getCursor();
		    	IDataUtil.put( masterCursor, "ID",  recordOptional.get().getKey() );
		    	IDataUtil.put( masterCursor, "CUST_NAME", recordOptional.get().get(CustMaster.CUST_NAME).get());
		    	IDataUtil.put( masterCursor, "CUST_LNG_NAME", recordOptional.get().get(CustMaster.CUST_LNG_NAME).get());
		    	IDataUtil.put( masterCursor, "CUST_SHRT_NAME", recordOptional.get().get(CustMaster.CUST_SHRT_NAME).get());
		    	IDataUtil.put( masterCursor, "CUST_SURNM1", recordOptional.get().get(CustMaster.CUST_SURNM1).get());
		    	IDataUtil.put( masterCursor, "CUST_SURNM2", recordOptional.get().get(CustMaster.CUST_SURNM2).get());
		    	//IDataUtil.put( masterCursor, "SURVIVORSHIP_DETAILS", "SURVIVORSHIP_DETAILS" );
		    	masterCursor.destroy();
		    	IDataUtil.put( pipelineCursor, "master", master );
		    	pipelineCursor.destroy();
		
		    
		        
		      }
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
		pipelineCursor.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void pushCustomer360 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(pushCustomer360)>> ---
		// @sigtype java 3.5
		// [i] recref:0:required MDMCustomer360 TelefonicaMDM.docs:CUSTOMER_HIERARCHY
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
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
								
								java.util.Date start = new java.util.Date();
								long start_time = System.nanoTime();
								Dataset custMasterDS = DataSetManagerFactory.getCustMasterDataSet();
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
		
										
				
										custWriter.add(Long.parseLong(CUSTOMER_ID), 
																CustMaster.CUST_NAME.newCell(NAME),
																CustMaster.CUST_LNG_NAME.newCell(LONGNAME),
																CustMaster.CUST_SHRT_NAME.newCell(SHORTNAME),
																CustMaster.CUST_SURNM1.newCell(SURNAME1),
																CustMaster.CUST_SURNM2.newCell(SURNAME2));
											
		
										// i_1.CUSTOMER_CROSSWALK
										IData	CUSTOMER_CROSSWALK = IDataUtil.getIData( datarowCursor, "CUSTOMER_CROSSWALK" );
										if ( CUSTOMER_CROSSWALK != null)
										{  Dataset custCrosswalkDS = DataSetManagerFactory.getCustCrossWalkDataSet();
										DatasetWriterReader<String> crosswalkWriter = custCrosswalkDS.writerReader();	
										IDataCursor CUSTOMER_CROSSWALKCursor = CUSTOMER_CROSSWALK.getCursor();
											
												// i_1.datarow
												IData[]	datarow_1 = IDataUtil.getIDataArray( CUSTOMER_CROSSWALKCursor, "datarow" );
												if ( datarow_1 != null)
												{
													for ( int i_1 = 0; i_1 < datarow_1.length; i_1++ )
													{
														IDataCursor datarow_1Cursor = datarow_1[i_1].getCursor();
															String	NAME_1 = IDataUtil.getString( datarow_1Cursor, "NAME" );
															String	ADDRESSLINE1 = IDataUtil.getString( datarow_1Cursor, "ADDRESSLINE1" );
															String	ADDRESSLINE2 = IDataUtil.getString( datarow_1Cursor, "ADDRESSLINE2" );
															String	ADMINISTRATIVEAREA = IDataUtil.getString( datarow_1Cursor, "ADMINISTRATIVEAREA" );
															String	LOCALITY = IDataUtil.getString( datarow_1Cursor, "LOCALITY" );
															String	POSTALCODE = IDataUtil.getString( datarow_1Cursor, "POSTALCODE" );
															String	C_ADDRESSLINE1 = IDataUtil.getString( datarow_1Cursor, "C_ADDRESSLINE1" );
															String	C_ADDRESSLINE2 = IDataUtil.getString( datarow_1Cursor, "C_ADDRESSLINE2" );
															String	C_ADMINISTRATIVEAREA = IDataUtil.getString( datarow_1Cursor, "C_ADMINISTRATIVEAREA" );
															String	C_LOCALITY = IDataUtil.getString( datarow_1Cursor, "C_LOCALITY" );
															String	C_POSTALCODE = IDataUtil.getString( datarow_1Cursor, "C_POSTALCODE" );
															String	C_COUNTRY = IDataUtil.getString( datarow_1Cursor, "C_COUNTRY" );
															String	C_LATTITUDE = IDataUtil.getString( datarow_1Cursor, "C_LATTITUDE" );
															String	C_LONGITUDE = IDataUtil.getString( datarow_1Cursor, "C_LONGITUDE" );
															String	C_GEO_DISTANCE = IDataUtil.getString( datarow_1Cursor, "C_GEO_DISTANCE" );
															String	MATCHING_RULE = IDataUtil.getString( datarow_1Cursor, "MATCHING_RULE" );
															String	MATCHING_SCORE = IDataUtil.getString( datarow_1Cursor, "MATCHING_SCORE" );
															String	ADDRESS_ID = IDataUtil.getString( datarow_1Cursor, "ADDRESS_ID" );
															String	SOURCE_SYSTEM = IDataUtil.getString( datarow_1Cursor, "SOURCE_SYSTEM" );
															String	SOURCE_SYSTEM_CUSTOMER_ID = IDataUtil.getString( datarow_1Cursor, "SOURCE_SYSTEM_CUSTOMER_ID" );
															String	CONTRACT_ID = IDataUtil.getString( datarow_1Cursor, "CONTRACT_ID" );
															String	SERVICE_ID = IDataUtil.getString( datarow_1Cursor, "SERVICE_ID" );
															String	CUSTOMER_ID_1 = IDataUtil.getString( datarow_1Cursor, "CUSTOMER_ID" );
														datarow_1Cursor.destroy();
														
														// AsyncDatasetWriterReader<String> asyncAccess 		 = writerReader.async(); // <1>
								
														crosswalkWriter.add(SOURCE_SYSTEM+"_"+SOURCE_SYSTEM_CUSTOMER_ID, 																					
																				CustCrossWalk.CUSTOMER_ID.newCell(CUSTOMER_ID_1),
																				CustCrossWalk.NAME.newCell(NAME_1),	
																				CustCrossWalk.SOURCE_SYSTEM.newCell(SOURCE_SYSTEM),
																				CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.newCell(SOURCE_SYSTEM_CUSTOMER_ID),
																				CustCrossWalk.ADDRESSLINE1.newCell(ADDRESSLINE1),
																				CustCrossWalk.ADDRESSLINE2.newCell(ADDRESSLINE2),
																				CustCrossWalk.LOCALITY.newCell(LOCALITY),
																				CustCrossWalk.POSTALCODE.newCell(POSTALCODE),
																				CustCrossWalk.ADMINISTRATIVEAREA.newCell(ADMINISTRATIVEAREA),
																				CustCrossWalk.C_ADDRESSLINE1.newCell(C_ADDRESSLINE1),
																				CustCrossWalk.C_ADDRESSLINE2.newCell(C_ADDRESSLINE2),
																				CustCrossWalk.C_ADMINISTRATIVEAREA.newCell(C_ADMINISTRATIVEAREA),
																				CustCrossWalk.C_COUNTRY.newCell(C_COUNTRY),
																				CustCrossWalk.C_GEO_DISTANCE.newCell(C_GEO_DISTANCE),
																				CustCrossWalk.C_LATTITUDE.newCell(C_LATTITUDE),
																				CustCrossWalk.C_LOCALITY.newCell(C_LOCALITY),
																				CustCrossWalk.C_LONGITUDE.newCell(C_LONGITUDE),
																				CustCrossWalk.C_POSTALCODE.newCell(C_POSTALCODE),
																				CustCrossWalk.CONTRACT_ID.newCell(CONTRACT_ID),
																				CustCrossWalk.SERVICE_ID.newCell(SERVICE_ID)
																				
																				);
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
															String	ADDRESSLINE1_1 = IDataUtil.getString( datarow_2Cursor, "ADDRESSLINE1" );
															String	ADDRESSLINE2_1 = IDataUtil.getString( datarow_2Cursor, "ADDRESSLINE2" );
															String	ADMINISTRATIVEAREA_1 = IDataUtil.getString( datarow_2Cursor, "ADMINISTRATIVEAREA" );
															String	LOCALITY_1 = IDataUtil.getString( datarow_2Cursor, "LOCALITY" );
															String	POSTALCODE_1 = IDataUtil.getString( datarow_2Cursor, "POSTALCODE" );
															String	COUNTRY = IDataUtil.getString( datarow_2Cursor, "COUNTRY" );
															String	ADDRESSTYPE = IDataUtil.getString( datarow_2Cursor, "ADDRESSTYPE" );
															//String	CUSTOMER_ID_2 = IDataUtil.getString( datarow_2Cursor, "CUSTOMER_ID" );
															String	LATTITUDE = IDataUtil.getString( datarow_2Cursor, "LATTITUDE" );
															String	LONGITUDE = IDataUtil.getString( datarow_2Cursor, "LONGITUDE" );
															String	ADDRESS_ID_1 = IDataUtil.getString( datarow_2Cursor, "ADDRESS_ID" );
															
															addressWriter.add(Long.parseLong(ADDRESS_ID_1),
																	CustAddress.CUST_ID.newCell(CUSTOMER_ID),
																	CustAddress.CUST_ADDR_ID.newCell(ADDRESS_ID_1),
																	CustAddress.ADDRESSLINE1.newCell(ADDRESSLINE1_1),
																	CustAddress.ADDRESSLINE2.newCell(ADDRESSLINE2_1),
																	CustAddress.ADMINISTRATIVEAREA.newCell(ADMINISTRATIVEAREA_1),
																	CustAddress.LOCALITY.newCell(LOCALITY_1),
																	CustAddress.POSTALCODE.newCell(POSTALCODE_1),
																	CustAddress.COUNTRY.newCell(COUNTRY),
																	CustAddress.CUST_ADDR_TYPE.newCell(ADDRESSTYPE),																	
																	CustAddress.LATTITUDE.newCell(LATTITUDE),
																	CustAddress.LONGITUDE.newCell(LONGITUDE));
																	
																	
																	
																					
																					
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
																				String	CUSTOMER_ID_3 = IDataUtil.getString( datarow_3Cursor, "CUSTOMER_ID" );
																				String	SERVICE_ID_1 = IDataUtil.getString( datarow_3Cursor, "SERVICE_ID" );
																				String	ADDRESS_ID_2 = IDataUtil.getString( datarow_3Cursor, "ADDRESS_ID" );
																				String	CONTRACT_SOURCE_SYSTEM = IDataUtil.getString( datarow_3Cursor, "CONTRACT_SOURCE_SYSTEM" );
																				//String	SOURCE_SYSTEM_CONTRACT_ID = IDataUtil.getString( datarow_3Cursor, "SOURCE_SYSTEM_CONTRACT_ID" );
																				String	SERVICE_TYPE = IDataUtil.getString( datarow_3Cursor, "SERVICE_TYPE" );
																				String	SERVICE_TYPE_CODE = IDataUtil.getString( datarow_3Cursor, "SERVICE_TYPE_CODE" );
																				String	SERVICE_SOURCE_SYSTEM = IDataUtil.getString( datarow_3Cursor, "SERVICE_SOURCE_SYSTEM" );
																				String	SOURCE_SYSTEM_SERVICE_ID = IDataUtil.getString( datarow_3Cursor, "SOURCE_SYSTEM_SERVICE_ID" );
																				String	SERVICE_START_DATE = IDataUtil.getString( datarow_3Cursor, "SERVICE_START_DATE" );
																				String	SERVICE_END_DATE = IDataUtil.getString( datarow_3Cursor, "SERVICE_END_DATE" );
																				String	SERVICE_CODE = IDataUtil.getString( datarow_3Cursor, "SERVICE_CODE" );
																				String	SERVICE_DESCRIPTION = IDataUtil.getString( datarow_3Cursor, "SERVICE_DESCRIPTION" );
																				String	READY_RECKONER = IDataUtil.getString( datarow_3Cursor, "READY_RECKONER" );
																				String	INSTALLED_INDICATOR = IDataUtil.getString( datarow_3Cursor, "INSTALLED_INDICATOR" );
																				String	CONFIDENTIAL_INDICATOR = IDataUtil.getString( datarow_3Cursor, "CONFIDENTIAL_INDICATOR" );
																				String	FAMILY_SERVICE_CODE = IDataUtil.getString( datarow_3Cursor, "FAMILY_SERVICE_CODE" );
																				String	CATALOG_SERVICE_CODE = IDataUtil.getString( datarow_3Cursor, "CATALOG_SERVICE_CODE" );
																				String	CATALOG_INDICATOR = IDataUtil.getString( datarow_3Cursor, "CATALOG_INDICATOR" );
																				String	MODIFY_USER = IDataUtil.getString( datarow_3Cursor, "MODIFY_USER" );
																				String	MODIFY_DATE = IDataUtil.getString( datarow_3Cursor, "MODIFY_DATE" );
																			datarow_3Cursor.destroy();
																			
													
																				serviceWriter.add(Long.parseLong(SERVICE_ID_1), 
																									CustService.CUSTOMER_ID.newCell(CUSTOMER_ID_3),
																									CustService.CUST_ADDR_ID.newCell(ADDRESS_ID_2),
																									CustService.SERVICE_TYPE.newCell(SERVICE_TYPE),
																									CustService.SERVICE_TYPE_CODE.newCell(SERVICE_TYPE_CODE),
																									CustService.SERVICE_SOURCE_SYSTEM.newCell(SERVICE_SOURCE_SYSTEM),
																									CustService.SOURCE_SYSTEM_SERVICE_ID.newCell(SOURCE_SYSTEM_SERVICE_ID),
																									CustService.SERVICE_DESCRIPTION.newCell(SERVICE_DESCRIPTION),
																									CustService.SERVICE_CODE.newCell(SERVICE_CODE),
																									CustService.SERVICE_START_DATE.newCell(SERVICE_START_DATE),
																									CustService.SERVICE_END_DATE.newCell(SERVICE_END_DATE),
																									CustService.READY_RECKONER.newCell(READY_RECKONER),
																									CustService.INSTALLED_INDICATOR.newCell(INSTALLED_INDICATOR),
																									CustService.CONFIDENTIAL_INDICATOR.newCell(CONFIDENTIAL_INDICATOR),
																									CustService.FAMILY_SERVICE_CODE.newCell(FAMILY_SERVICE_CODE),
																									CustService.CATALOG_SERVICE_CODE.newCell(CATALOG_SERVICE_CODE),
																									CustService.CATALOG_INDICATOR.newCell(CATALOG_INDICATOR),
																									CustService.MODIFY_USER.newCell(MODIFY_USER),
																									CustService.MODIFY_DATE.newCell(MODIFY_DATE));
																									
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
		 throw new ServiceException(e);
		}
		pipelineCursor.destroy();
		
		// pipeline
			
		// --- <<IS-END>> ---

                
	}
}

