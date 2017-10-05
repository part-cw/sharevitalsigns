package org.ecemgroup.sharevitalsigns.library;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;

/** ShareVitalSigns Class
 * Library class which provides functions for sending vital signs and other medical data between android applications.
 * The library can be used by a vital sign provider (e.g. that records this from a sensor) or a vital sign receiver (that displays or process this vital sign further).
 * Check  {@link ShareVitalSignsDemoReceiver} for an example of a receiver 
 * Check  {@link ShareVitalSignsDemoSender} for an example of a provider
 * Check {@link RRate} for an example of a provider that records respiratory rate with tapping on the screen.
 * 
 * the library essentially uses {@link Intents}. More on intents check the Android developer website:
 * http://developer.android.com/guide/components/intents-filters.html
 * 
 * VitalSigns currently supported:
 * HR: 		Heart Rate					beats/min
 * RR: 		Respiratory Rate			breaths/min	
 * TEMP: 	Temperature					deg Celcius
 * SPO2:	Oxygen saturation			%
 * BP_SYS:	Blood pressure (Sys )		mmHg
 * BP_DIA:  Blood pressure (Sys )		mmHg
 * 
 * Combinations:
 * BP:		Blood pressure (Sys & Dia) 	mmHg & mmHg
 * PO:		Pulse oximetry (HR & SPO2)	beats/min & % 
 * 
 *
 * @author Walter Karlen (walter.karlen@ieee.org)
 * @version 0.1.0
 * 
 */
public class ShareVitalSigns  {

	private static final String libraryclassname = "ShareVitalSigns";
	public static final String libraryaddress = "org.ecemgroup.sharevitalsigns";
	
	// Definitions for vital signs. can be used for requests 
	public static final int MEASURE_HR = 1;
	public static final int MEASURE_RR = 2;
	public static final int MEASURE_SPO2 = 4;
	public static final int MEASURE_TEMP = 8;
	public static final int MEASURE_BPSYS = 16;
	public static final int MEASURE_BPDIA = 32;
	public static final int MEASURE_BP = MEASURE_BPSYS | MEASURE_BPDIA;
	public static final int MEASURE_PO = MEASURE_HR | MEASURE_SPO2;

	//Definitions for vital sign  names
	public static final String N_HR = "HR";
	public static final String N_RR = "RR";
	public static final String N_SPO2= "SPO2";
	public static final String N_TEMP = "TEMP";
	public static final String N_BP = "BP";
	public static final String N_PO = "PO";
	public static final String N_BPSYS = "BPSYS";
	public static final String N_BPDIA= "BPDIA";
	
	//Definitions for vital sign extras field names
	public static final String V_HR = libraryclassname+"Value_HR";
	public static final String V_RR = libraryclassname+"Value_RR";
	public static final String V_SPO2= libraryclassname+"Value_SPO2";
	public static final String V_TEMP = libraryclassname+"Value_TEMP";
	public static final String V_BP = libraryclassname+"Value_BP";
	public static final String V_PO = libraryclassname+"Value_PO";
	public static final String V_BPSYS = libraryclassname+"Value_BPSYS";
	public static final String V_BPDIA= libraryclassname+"Value_BPDIA";
	public static final String C_HR = libraryclassname+"Confidence_HR";
	public static final String C_RR = libraryclassname+"Confidence_RR";
	public static final String C_SPO2= libraryclassname+"Confidence_SPO2";
	public static final String C_TEMP = libraryclassname+"Confidence_TEMP";
	public static final String C_BP = libraryclassname+"Confidence_BP";
	public static final String C_BPSYS = libraryclassname+"Confidence_BPSYS";
	public static final String C_BPDIA= libraryclassname+"Confidence_BPDIA";
	public static final String C_PO= libraryclassname+"Confidence_PO";

	public static final String[] V_NAMELIST={V_HR,V_RR,V_SPO2,V_TEMP,V_BPSYS,V_BPDIA};
	public static final String[] C_NAMELIST={C_HR,C_RR,C_SPO2,C_TEMP,C_BPSYS,C_BPDIA};
	public static final String[] N_NAMELIST={N_HR,N_RR,N_SPO2,N_TEMP,N_BPSYS,N_BPDIA};


	//Vital Signs are stored here for Receivers and Providers
	public  ShareVitalSignsData svsData=new ShareVitalSignsData();
	
	//Saveguard Intent here  
	public Intent ShareVitalSignsIntent;
	
	
	// 
	private int VitalSignsProvided;
	

	//init method for a provider
	public ShareVitalSigns (int provider) {
		VitalSignsProvided=provider;
	}

	//init method for receiver (no provider is known)
	public ShareVitalSigns () {

	}

	//%%%%%%%%%%%%%%%%RECEIVER StUFF%%%%%%%%%%%%%%%%%%%%%%%%

	public ShareVitalSignsResultReceiver VitalSignReceiver;
	private int ShareVitalSignsRequested; //value that is being send



	/**
	 * Interface for SVS results
	 * 
	 * 
	 */
	public interface ShareVitalSignsResultReceiver {


		public void onResult(float[] vital, int[] confidence);



	}


	/**
	 * This receiver method opens VitalSign Intent and requests a vital sign measurement externally using the Intent.
	 * 
	 * 
	 * @param resultReceiver
	 * @param measureCode int of Vital Sign Code
	 * 
	 * @return Intent   intent to be launched within activity
	 */
	public  Intent measureVitalSigns(ShareVitalSignsResultReceiver resultreceiver,int measureCode) {

		VitalSignReceiver = resultreceiver; //
		ShareVitalSignsRequested = measureCode; //
		
		//TODO open more specific intent targeted to vital sign requested with measure code
		Intent launchMeasure = new Intent(libraryaddress+".MEASURE");
		launchMeasure.putExtra(libraryclassname+"Measure", measureCode);
	
		return launchMeasure;

	}


	/**
	 * This receiver method reads the values returned from Intent and compares if requested and returned codes match. 
	 * It further checks if the requested vital signs have been measured and if stores them in the object 
	 * 
	 * 
	 * @param resultReceiver
	 * @param measureCode int of Vital Sign Code
	 * 
	 * @return boolean
	 */
	//@Override
	public boolean getResults(int requestCode, int resultCode, Intent data) {

		if (requestCode == ShareVitalSignsRequested) { //requestCode matches code send
			if (resultCode == Activity.RESULT_OK) { //a measurement was performed

				// get number and indexes of requested vital signs
				ShareVitalSignsHandler received = initHandler(requestCode);

				int[] vitalsignsindex =received.getVitalsignsindex();
				int vitalsignsnb=received.getVitalsignsnb();	

				float[] val=new float[vitalsignsnb] ;
				int[] confidence=new int[vitalsignsnb];
				String[] VitalSign =new String[vitalsignsnb];

				for (int i=0; i<vitalsignsnb; i++) {
					val[i]=-3;
					confidence[i]=-3;

					int vsindex=vitalsignsindex[i];
					VitalSign[i]=V_NAMELIST[vsindex];
					String ConfVitalSign=C_NAMELIST[vsindex];


					if (data.hasExtra(VitalSign[i])) { //correct measurement was performed

						val[i] = data.getExtras().getFloat(VitalSign[i]);
						svsData.setResultVIndex((int) Math.pow(2,(double)vsindex), val[i]);
						if (data.hasExtra(ConfVitalSign)) {
							confidence[i] = data.getExtras().getInt(ConfVitalSign);

						} else {
							confidence[i] =-1; //confidence was not avail
						}
						svsData.setResultCIndex((int) Math.pow(2,(double)vsindex), confidence[i]);
					} else {
						return false;
					}

				}

				if (VitalSignReceiver != null) {

					VitalSignReceiver.onResult(val, confidence);
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * 
	 * 
	 * 
	 */
	private class ShareVitalSignsHandler {
		
		private int[] vitalsignsindex =new int[Integer.SIZE]; //position of vs in table
		private int vitalsignsnb=0; //total number of signals requested
		

		public int[] getVitalsignsindex() {
			return vitalsignsindex;
		}
		public void setVitalsignsindex(int[] vitalsignsindex) {
			this.vitalsignsindex = vitalsignsindex;
		}
		public int getVitalsignsnb() {
			return vitalsignsnb;
		}
		public void setVitalsignsnb(int vitalsignsnb) {
			this.vitalsignsnb = vitalsignsnb;
		}
	}

	/**
	
	 * 
	 * @param requestCode
	 * 
	 * @return ShareVitalsRequested
	 */
	private ShareVitalSignsHandler  initHandler(int requestCode ) {
		int counter=0;
		int idx =0;
		int temp=requestCode;

		int[] vitalsignsindex =new int[Integer.SIZE];
		while (temp>0){
			int mask=1;
			if ( (temp & mask) > 0){
				vitalsignsindex[idx]=counter;
				idx+=1;
			}
			temp >>>= 1;
			counter+=1;
		}
		ShareVitalSignsHandler received = new ShareVitalSignsHandler();
		received.setVitalsignsindex(vitalsignsindex);
		received.setVitalsignsnb(idx);
		return received;
	}




	//%%%%%%%%%%%%%%%%%%%%%%COMMON FUNCTIONS%%%%%%%%%%%%%%%%%%%%%%%

	/**
	 * This class is used to store vital sign data. 
	 * can be used to create an intent to send data or save data obtained through intent
	 * 
	 */
	public class ShareVitalSignsData {

		private float[] Data_V=new float[Integer.SIZE];
		private int[] Data_C=new int[Integer.SIZE];


		/**
		 * provides vital sign value stored in the result class
		 * 
		 * 
		 * @param measureCode int of Vital Sign Code
		 * 
		 * @return vitalsign  intent to be launched within activity
		 */
		public float getResultVIndex(int measureCode) {
			return Data_V[convertCodeToIndex(measureCode)];
		}

		public void setResultVIndex(int measureCode, float value) {
			this.Data_V[convertCodeToIndex(measureCode)] = value;
		}
		public int getResultCIndex(int measureCode) {
			return Data_C[convertCodeToIndex(measureCode)];
		}
		public void setResultCIndex(int measureCode, int value) {
			this.Data_C[convertCodeToIndex(measureCode)] = value;
		}

		/**
		 * Transforms SVS code into table index
		 * 
		 * @param measureCode int of Vital Sign Code
		 * 
		 * @return index   index where requested vital sign is stored
		 */
		private int convertCodeToIndex(int measureCode){
			int index= Math.getExponent((double)measureCode); //(int)
			
			return index;
		}

		public ShareVitalSignsData() {
			Data_V[0]=-1;
			Data_C[0]=-1;
		}
	} 

	//%%%%%%%%%%%%%%%%%%%%%%PROVIDER METHODS%%%%%%%%%%%%%%%%%%%%%%%
	
	/**
	 * This is a SVS provider method that reads the values returned from Intent and compares if requested and returned codes match. 
	 * It further checks if the requested vital signs have been measured and if stores them in the object 
	 * 
	 * 
	 * @param resultReceiver
	 * @param measureCode int of Vital Sign Code
	 * 
	 * @return int 1 OK, negative if sign not avail, 0 if wrong intent
	 */
	public  int chkIntent( ){
		
			int providerCode=VitalSignsProvided;
			if (ShareVitalSignsIntent.hasExtra(libraryclassname+"Measure")) {
				String str = ShareVitalSignsIntent.getExtras().getString(libraryclassname+"Measure");
				int requestCode = 0;
				if (str != null && !str.isEmpty()) {
					requestCode = Integer.parseInt(str);
				}else {
					requestCode = ShareVitalSignsIntent.getExtras().getInt(libraryclassname + "Measure");
				}
	
				if ((requestCode & providerCode)>=requestCode){ 
					return 1; //system does provide requested signal(s)
				}
				else if ((requestCode & providerCode)>0){
					return -1; //only some signals available
	
				} else {
					return -2; //signal(s) not available
				}
			} 
		
		return 0; //wrong Intent

	}
	
	/**
	 * This is a SVS provider method checks if any vital signs  have been requested and
	 * returns the code if a request is valid
	 * 
	 * 
	 * @return int  0 wrong intent, else code of vs
	 */
	
	
	public  int getVitalSignsRequested( ){

		if (ShareVitalSignsIntent.hasExtra(libraryclassname+"Measure")) {
			String str = ShareVitalSignsIntent.getExtras().getString(libraryclassname+"Measure");
			int requestCode = 0;
			if (str != null && !str.isEmpty()) {
				requestCode = Integer.parseInt(str);
			}else {
				requestCode = ShareVitalSignsIntent.getExtras().getInt(libraryclassname + "Measure");
			}
			return requestCode;
		} 

		return 0; //not a SVS Intent

	}

	/**
	 * This provider method stores a value and confidence for a given vital sign to the data class
	 * 
	 * @param measureCode int of Vital Sign Code
	 * @param value  Vital Sign value
	 * @param confidence Vital Sign Confidence
	 * 
	 */
	public void saveVitalSign(int measureCode,float value, int confidence) {

		if (svsData != null) {
			svsData.setResultVIndex(measureCode, value);
			svsData.setResultCIndex(measureCode, confidence);
		}
	}

	/**
	 * This provider method creates an intent for the requested vital signs 
	 * with data stored in data class 
	 * 
	 * 
	 * @return Intent
	 */
	public Intent prepareIntent() {
		int providerCode=VitalSignsProvided;
		Intent dataI = new Intent();
		ShareVitalSignsHandler requested =initHandler(providerCode);
		int[] vitalsignsindex =requested.getVitalsignsindex();
		int vitalsignsnb=requested.getVitalsignsnb();	

		if (svsData != null) {

			for (int i=0; i<vitalsignsnb; i++) {

				int vsindex=vitalsignsindex[i];
				int index=(int) Math.pow(2,(double)vsindex);
				float Vval =svsData.getResultVIndex(index);
				int Cval =svsData.getResultCIndex(index);
				dataI.putExtra(V_NAMELIST[vsindex], Vval);
				dataI.putExtra(C_NAMELIST[vsindex], Cval);
				// Add ODK return fields
				Bundle odk_responses = new Bundle();
				odk_responses.putString(V_NAMELIST[vsindex], String.valueOf(Vval));
				odk_responses.putString(C_NAMELIST[vsindex], String.valueOf(Cval));
				//dataI.putExtra("odk_intent_bundle",odk_responses);
				dataI.putExtra("odk_intent_data", String.valueOf(Vval));
			}
		}
		return dataI;
	}
}

