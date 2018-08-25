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
import com.terracottatech.store.definition.CellDefinition;
import com.terracottatech.store.stream.MutableRecordStream;
import java.util.Optional;
import com.gcs.tcclient.DataSetManagerFactory;
import com.gcs.tcclient.DataSetManagerFactory.CustAddress;
import com.gcs.tcclient.DataSetManagerFactory.CustCrossWalk;
import com.gcs.tcclient.DataSetManagerFactory.CustMaster;
import com.gcs.tcclient.DataSetManagerFactory.CustService;
// --- <<IS-END-IMPORTS>> ---

public final class customer360

{
	// ---( internal utility methods )---

	final static customer360 _instance = new customer360();

	static customer360 _newInstance() { return new customer360(); }

	static customer360 _cast(Object o) { return (customer360)o; }

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



	public static final void dropAllRecords (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(dropAllRecords)>> ---
		// @sigtype java 3.5
		long start_time = System.currentTimeMillis();
		try{
			 com.gcs.tcclient.DataSetManagerFactory.createCustCrossWalkDataSet();
			Dataset<String> crossWalkDS = com.gcs.tcclient.DataSetManagerFactory.getCustCrossWalkDataSet();
			DatasetWriterReader<String> crosswalkReader = crossWalkDS.writerReader();
			
			try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
			
				java.util.List<Record<String>> multiRecs = 
		
							recordStream	.collect(java.util.stream.Collectors.toList());	
					if(multiRecs!=null)
						for(Record<String> record: multiRecs){
							crosswalkReader.delete(record.getKey());
							
						}		
		
				}//3
		
			
					Dataset<Long> custDS = com.gcs.tcclient.DataSetManagerFactory.getCustMasterDataSet();
					//  Dataset<String> ds = getDatasetFromStack(); 
		
					DatasetWriterReader<Long> custReader = custDS.writerReader();	
					try(MutableRecordStream<Long> recordStream = 	custReader.records()){
						java.util.List<Record<Long>> multiRecs =								
								recordStream	.collect(java.util.stream.Collectors.toList());	
						if(multiRecs!=null)
							for(Record<Long> record: multiRecs){
								custReader.delete(record.getKey());
								
							}		
		
					}
					
		
					Dataset<Long> addresssDS = com.gcs.tcclient.DataSetManagerFactory.getAddressDataSet();
					//  Dataset<String> ds = getDatasetFromStack(); 
		
					DatasetWriterReader<Long> addresReader = addresssDS.writerReader();	
					try(MutableRecordStream<Long> recordStream = 	addresReader.records()){
						java.util.List<Record<Long>> multiRecs =								
								recordStream	.collect(java.util.stream.Collectors.toList());	
						if(multiRecs!=null)
							for(Record<Long> record: multiRecs){
								addresReader.delete(record.getKey());
								
							}		
		
					}
		
					Dataset<Long> serviceDS = com.gcs.tcclient.DataSetManagerFactory.getServiceDataSet();
					//  Dataset<String> ds = getDatasetFromStack(); 
		
					DatasetWriterReader<Long> serviceReader = serviceDS.writerReader();	
					try(MutableRecordStream<Long> recordStream = 	serviceReader.records()){
						java.util.List<Record<Long>> multiRecs =								
								recordStream	.collect(java.util.stream.Collectors.toList());	
						if(multiRecs!=null)
							for(Record<Long> record: multiRecs){
								serviceReader.delete(record.getKey());
								
							}		
		
					}
						}catch(Exception e){
			e.printStackTrace();
		}
		// --- <<IS-END>> ---

                
	}



	public static final void getCustomer360 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getCustomer360)>> ---
		// @sigtype java 3.5
		// [i] field:0:required CUST_NAME
		// [i] field:0:required CUST_ID
		// [i] field:0:required ADDRESS_NAME
		// [i] field:0:required CITY
		// [o] recref:0:required customers TelefonicaCustomerAPI.docTypes:Customer360
		// [o] recref:0:required status TelefonicaCustomerAPI.docTypes:Status
		// pipeline
				IDataCursor pipelineCursor = pipeline.getCursor();
		
				DataSetManagerFactory.tcURL= "terracotta://daehgcs28835.daedmz.loc:9410";
				Optional<String> CUST_NAME = Optional.ofNullable(null);//IDataUtil.getString( pipelineCursor, "CUST_NAME" ));
				Optional<String> CUST_ID =  Optional.ofNullable(""+17477911); //66032589
				Optional<String> ADDRESS_NAME = Optional.ofNullable(null);
				Optional<String> CITY = Optional.ofNullable(null);
				java.util.List<Record<String>> multiRecs = null ;
				Optional<Record<String>> singleRec = null ;
				boolean isSingleRec =false;
				//long start_time = System.nanoTime();
				long start_time = System.currentTimeMillis();
				try{
					com.gcs.tcclient.DataSetManagerFactory.createCustCrossWalkDataSet();
					Dataset<String> crossWalkDS = com.gcs.tcclient.DataSetManagerFactory.getCustCrossWalkDataSet();
					DatasetWriterReader<String> crosswalkReader = crossWalkDS.writerReader();
		
					if(CUST_NAME.isPresent() && CUST_ID.isPresent() && ADDRESS_NAME.isPresent() &&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
							isSingleRec=true;		
						}
						// all four
					}else if(CUST_NAME.isPresent() && CUST_ID.isPresent() && ADDRESS_NAME.isPresent() ){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec =
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
							isSingleRec=true;	
		
						}//any 3
					}else if(CUST_NAME.isPresent() && CUST_ID.isPresent()&&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
							isSingleRec=true;	
		
						}//3
					}else if(CUST_NAME.isPresent()  && ADDRESS_NAME.isPresent() &&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									.collect(java.util.stream.Collectors.toList());	
		
						}//3
					}else if(CUST_ID.isPresent() && ADDRESS_NAME.isPresent() &&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
							isSingleRec=true;	
		
						}//3
					}else if(CUST_NAME.isPresent() && CUST_ID.isPresent() ){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
							isSingleRec=true;	
		
						}//2
					}else if(CUST_NAME.isPresent() &&  ADDRESS_NAME.isPresent() ){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))								
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))							
									.collect(java.util.stream.Collectors.toList());	
		
						}
					}else if(CUST_NAME.isPresent()&&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									.collect(java.util.stream.Collectors.toList());	
		
						}
					}else if(CUST_ID.isPresent() && ADDRESS_NAME.isPresent() ){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id	
							isSingleRec=true;	
		
						}
					}else if( CUST_ID.isPresent()&&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
							isSingleRec=true;	
		
						}
					}else if(ADDRESS_NAME.isPresent() &&CITY.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 
		
									recordStream	
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									.collect(java.util.stream.Collectors.toList());	
		
						}
					}else if(CUST_ID.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							singleRec = 
		
									recordStream.filter(CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID.value().is(CUST_ID.get()))	
									//.collect(java.util.stream.Collectors.toList());	
									.findAny(); //find any one record to get the cust master id
		
							isSingleRec=true;	
		
						}
					}else if(CUST_NAME.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 
		
									recordStream.filter(CustCrossWalk.NAME.value().is(CUST_NAME.get()))	
									.collect(java.util.stream.Collectors.toList());	
		
						}
					}else if(ADDRESS_NAME.isPresent()){
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 
		
									recordStream
									.filter(CustCrossWalk.C_ADDRESSLINE1.value().is(ADDRESS_NAME.get()))
									.collect(java.util.stream.Collectors.toList());	
		
						}
					}else if(CITY.isPresent()){ //bad performance
						try(MutableRecordStream<String> recordStream = 	crosswalkReader.records()){
							multiRecs = 		
									recordStream.filter(CustCrossWalk.C_LOCALITY.value().is(CITY.get().toUpperCase()))
									.collect(java.util.stream.Collectors.toList());	
		
						}
					}
		
					java.util.ArrayList<String> al = new java.util.ArrayList<String>();
		
					if(isSingleRec&&singleRec.isPresent()){
						al.add(singleRec.get().get(CustCrossWalk.CUSTOMER_ID).get());
					}else{
						if(multiRecs!=null)
							for(Record<String> record: multiRecs){
								al.add(record.get(CustCrossWalk.CUSTOMER_ID).get());
							}		
		
					}
		
					//get all customers
					// customers
					IData	customers = IDataFactory.create();
					IDataCursor customersCursor = customers.getCursor();
		
					// customers.customer
					java.util.ArrayList<IData>	customerAL = new java.util.ArrayList<IData>();
					if(al.size()>0)
		
		
						for(String custID: al){
							Dataset<Long> custDS = com.gcs.tcclient.DataSetManagerFactory.getCustMasterDataSet();
							//  Dataset<String> ds = getDatasetFromStack(); 
		
							DatasetReader<Long> custReader = custDS.reader();				
							
							Optional<Record<Long>> custRec = custReader.get(Long.parseLong(custID));
							IData customer = IDataFactory.create();
							IDataCursor customerCursor = customer.getCursor();
							IData	master = IDataFactory.create();
							IDataCursor masterCursor = master.getCursor();
							IDataUtil.put( masterCursor, "ID",  custRec.get().getKey() );
							IDataUtil.put( masterCursor, "CUST_NAME", custRec.get().get(CustMaster.CUST_NAME).get());
							IDataUtil.put( masterCursor, "CUST_LNG_NAME", custRec.get().get(CustMaster.CUST_LNG_NAME).get());
							IDataUtil.put( masterCursor, "CUST_SHRT_NAME", custRec.get().get(CustMaster.CUST_SHRT_NAME).get());
							IDataUtil.put( masterCursor, "CUST_SURNM1", custRec.get().get(CustMaster.CUST_SURNM1).get());
							IDataUtil.put( masterCursor, "CUST_SURNM2", custRec.get().get(CustMaster.CUST_SURNM2).get());
							//IDataUtil.put( masterCursor, "SURVIVORSHIP_DETAILS", "SURVIVORSHIP_DETAILS" );
							masterCursor.destroy();
							IDataUtil.put(customerCursor,"master",master);
							//now get the Addresses
							Dataset<Long> addressDS = com.gcs.tcclient.DataSetManagerFactory.getAddressDataSet();
							DatasetWriterReader<Long> addressReader = addressDS.writerReader();
							java.util.List<Record<Long>> addressRecs = null ;
		
		
							try(MutableRecordStream<Long> addressStream = 	addressReader.records()){
								// get all adddressf or the given customerID.
								addressRecs = 		
										addressStream.filter(CustAddress.CUST_ID.value().is(custID))						
										.collect(java.util.stream.Collectors.toList());	
								java.util.ArrayList<IData> addressAL=new java.util.ArrayList<IData>();
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
									//IDataUtil.put( addressCursor, "CUST_ADDR_EMAIL",  addrRec.get(CustAddress.CUST_ADDR_EMAIL).get() );
									//IDataUtil.put( addressCursor, "CUST_ADDR_PHONE_NUMBER", addrRec.get(CustAddress.CUST_ADDR_PHONE_NUMBER).get() );
									//IDataUtil.put( addressCursor, "CUST_ADDR_FAX_NUMBER",  addrRec.get(CustAddress.CUST_ADDR_FAX_NUMBER).get() );
									//IDataUtil.put( addressCursor, "WINKEY_ADDR", "WINKEY_ADDR" );
									//IDataUtil.put( addressCursor, "WINKEY_EMAIL", "WINKEY_EMAIL" );
									//IDataUtil.put( addressCursor, "WINKEY_PHNMBR", "WINKEY_PHNMBR" );
									//IDataUtil.put( addressCursor, "WINKEY_FXNMBR", "WINKEY_FXNMBR" );
									IDataUtil.put( addressCursor, "CUST_ADDR_TYPE", addrRec.get(CustAddress.CUST_ADDR_TYPE).get() );
		
									addressAL.add(address);
									//getservices for each address
									Dataset<Long> servicesDS = com.gcs.tcclient.DataSetManagerFactory.getServiceDataSet();
									DatasetWriterReader<Long> servicesReader = servicesDS.writerReader();
		
		
									try(MutableRecordStream<Long> servicesStream = 	servicesReader.records()){
										// get all adddressf or the given customerID.
										java.util.List<Record<Long>> serviceRecs = 		
												servicesStream.filter(CustService.CUSTOMER_ID.value().is(custID))
												.filter(CustService.CUST_ADDR_ID.value().is(CUST_ADDR_ID))
												.collect(java.util.stream.Collectors.toList());	
										java.util.ArrayList<IData> servicesAL=new java.util.ArrayList<IData>();
										for(Record<Long> svcRec: serviceRecs){
											IData	service = IDataFactory.create();
											IDataCursor serviceCursor = service.getCursor();
		
											IDataUtil.put( serviceCursor, "SRVC_ID",svcRec.getKey()+"" );
											if(svcRec.get(CustService.SERVICE_TYPE).isPresent()) 
												IDataUtil.put( serviceCursor, "SRVC_TYP", svcRec.get(CustService.SERVICE_TYPE).get() );
											if(svcRec.get(CustService.SERVICE_TYPE_CODE).isPresent()) 
											IDataUtil.put( serviceCursor, "SRVC_TYP_CD",svcRec.get(CustService.SERVICE_TYPE_CODE).get() );
											if(svcRec.get(CustService.SOURCE_SYSTEM_SERVICE_ID).isPresent())
												IDataUtil.put( serviceCursor, "SRC_SYS_ID", svcRec.get(CustService.SOURCE_SYSTEM_SERVICE_ID).get() );
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
		
											servicesAL.add(service);
											//getservices for each address
		
										}
										if(servicesAL.size()>0)//insert Addresses
											IDataUtil.put(addressCursor,"service",servicesAL.toArray(new IData[servicesAL.size()]));			
									}
									addressCursor.destroy();
								}
		
								if(addressAL.size()>0)//insert Addresses
									IDataUtil.put(customerCursor,"address",addressAL.toArray(new IData[addressAL.size()]));			
							}
		
		
							customerCursor.destroy();
							customerAL.add(customer);
						}
		
					IDataUtil.put(customersCursor,"customer",customerAL.toArray(new IData[customerAL.size()]));
					//long end_time = System.nanoTime();
					customersCursor.destroy();
					long end_time = System.currentTimeMillis();
					IDataUtil.put( pipelineCursor, "customers", customers );
					// status
					IData	status = IDataFactory.create();
					IDataCursor statusCursor = status.getCursor();
					IDataUtil.put( statusCursor, "STATUS_CODE", "OK" );
					IDataUtil.put( statusCursor, "STATUS_MESG", "Successfully retrieved the data" );
					statusCursor.destroy();
					IDataUtil.put( pipelineCursor, "status", status );
		
				}catch(Exception e){
					e.printStackTrace(); //TBD. Should be deleted beforee golive
					IData	status = IDataFactory.create();
					IDataCursor statusCursor = status.getCursor();
					IDataUtil.put( statusCursor, "STATUS_CODE", "ERROR" );
					IDataUtil.put( statusCursor, "STATUS_MESG",e.getMessage() ); // TBD: need to replace with some business message
					statusCursor.destroy();
				}
				pipelineCursor.destroy();
		
		
		
		
		
		
		
		
		
		
		
			
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
									String	SOURCE_SYSTEM = IDataUtil.getString( datarow_1Cursor, "SOURCE_SYSTEM" );
									String	SOURCE_SYSTEM_CUSTOMER_ID = IDataUtil.getString( datarow_1Cursor, "SOURCE_SYSTEM_CUSTOMER_ID" );
									
									
									
									datarow_1Cursor.destroy(); 
									
		
									// AsyncDatasetWriterReader<String> asyncAccess 		 = writerReader.async(); // <1>
									java.util.ArrayList<Cell<String>> cwAl= new java.util.ArrayList<Cell<String>>();
									addToCells(cwAl,CustCrossWalk.CUSTOMER_ID,IDataUtil.getString( datarow_1Cursor, "CUSTOMER_ID" ));
									addToCells(cwAl,CustCrossWalk.ADDRESS_ID,IDataUtil.getString( datarow_1Cursor, "ADDRESS_ID" ));
									addToCells(cwAl,CustCrossWalk.NAME,IDataUtil.getString( datarow_1Cursor, "NAME" ));	
									addToCells(cwAl,CustCrossWalk.SOURCE_SYSTEM,SOURCE_SYSTEM);
									addToCells(cwAl,CustCrossWalk.SOURCE_SYSTEM_CUSTOMER_ID,SOURCE_SYSTEM_CUSTOMER_ID);
									addToCells(cwAl,CustCrossWalk.ADDRESSLINE1, IDataUtil.getString( datarow_1Cursor, "ADDRESSLINE1" ));
									addToCells(cwAl,CustCrossWalk.ADDRESSLINE2,IDataUtil.getString( datarow_1Cursor, "ADDRESSLINE2" ));
									addToCells(cwAl,CustCrossWalk.LOCALITY,IDataUtil.getString( datarow_1Cursor, "LOCALITY" ));
									addToCells(cwAl,CustCrossWalk.POSTALCODE,IDataUtil.getString( datarow_1Cursor, "POSTALCODE" ));
									addToCells(cwAl,CustCrossWalk.ADMINISTRATIVEAREA,IDataUtil.getString( datarow_1Cursor, "ADMINISTRATIVEAREA" ));
									addToCells(cwAl,CustCrossWalk.C_ADDRESSLINE1,IDataUtil.getString( datarow_1Cursor, "C_ADDRESSLINE1" ));
									addToCells(cwAl,CustCrossWalk.C_ADDRESSLINE2,IDataUtil.getString( datarow_1Cursor, "C_ADDRESSLINE2" ));
									addToCells(cwAl,CustCrossWalk.C_ADMINISTRATIVEAREA,IDataUtil.getString( datarow_1Cursor, "C_ADMINISTRATIVEAREA" ));
									addToCells(cwAl,CustCrossWalk.C_COUNTRY,IDataUtil.getString( datarow_1Cursor, "C_COUNTRY" ));
									addToCells(cwAl,CustCrossWalk.C_GEO_DISTANCE,IDataUtil.getString( datarow_1Cursor, "C_GEO_DISTANCE" ));
									addToCells(cwAl,CustCrossWalk.C_LATTITUDE,IDataUtil.getString( datarow_1Cursor, "C_LATTITUDE" ));
									addToCells(cwAl,CustCrossWalk.C_LOCALITY,IDataUtil.getString( datarow_1Cursor, "C_LOCALITY" ));
									addToCells(cwAl,CustCrossWalk.C_LONGITUDE,IDataUtil.getString( datarow_1Cursor, "C_LONGITUDE" ));
									addToCells(cwAl,CustCrossWalk.C_POSTALCODE,IDataUtil.getString( datarow_1Cursor, "C_POSTALCODE" ));
									addToCells(cwAl,CustCrossWalk.CONTRACT_ID,IDataUtil.getString( datarow_1Cursor, "CONTRACT_ID" ));
									addToCells(cwAl,CustCrossWalk.SERVICE_ID,IDataUtil.getString( datarow_1Cursor, "SERVICE_ID" ));
									crosswalkWriter.add(SOURCE_SYSTEM+"_"+SOURCE_SYSTEM_CUSTOMER_ID, 																					
											(Cell[])cwAl.toArray(new Cell[cwAl.size()]));
		
											
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
												addToCells(al,CustService.CUSTOMER_ID, IDataUtil.getString( datarow_3Cursor, "CUSTOMER_ID" ));
												addToCells(al,CustService.CUST_ADDR_ID,IDataUtil.getString( datarow_3Cursor, "ADDRESS_ID" ));
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
		
		
												serviceWriter.add(Long.parseLong(SERVICE_ID_1), 
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
		pipelineCursor.destroy();
		
		// pipeline
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static void addToCells(java.util.ArrayList<Cell<String>> al, CellDefinition<String> cell,String value){
		if(value!=null)
			al.add(cell.newCell(value));
	}
	// --- <<IS-END-SHARED>> ---
}

