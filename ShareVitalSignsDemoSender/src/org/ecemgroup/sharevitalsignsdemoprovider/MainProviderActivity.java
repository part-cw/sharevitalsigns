package org.ecemgroup.sharevitalsignsdemoprovider;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ecemgroup.sharevitalsigns.library.ShareVitalSigns;
import org.ecemgroup.sharevitalsignsdemoprovider.R;


public class MainProviderActivity extends Activity implements OnClickListener {

	
	private Activity mActivity;
	
	//init ShareVitalSigns Result
	public ShareVitalSigns mSharevitals = new ShareVitalSigns(ShareVitalSigns.MEASURE_PO|ShareVitalSigns.MEASURE_RR|ShareVitalSigns.MEASURE_TEMP);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActivity = this;

		//init buttons
		Button heartrateButton = (Button) this.findViewById(R.id.button_get_heartrate);
		heartrateButton.setOnClickListener(this);
		Button respiratoryrateButton = (Button) this.findViewById(R.id.button_get_respiratoryrate);
		respiratoryrateButton.setOnClickListener(this);
		Button temperatureButton = (Button) this.findViewById(R.id.button_get_temperature);
		temperatureButton.setOnClickListener(this);
		Button oxygensaturationButton = (Button) this.findViewById(R.id.button_get_oxygensaturation);
		oxygensaturationButton.setOnClickListener(this);
		Button pulseoximetryButton = (Button) this.findViewById(R.id.button_get_pulseoximetry);
		pulseoximetryButton.setOnClickListener(this);
		
		//get intent and check if one existed
		  mSharevitals.ShareVitalSignsIntent = getIntent();

	        int check = mSharevitals.chkIntent();
	        if (check < 0) {
	            Toast.makeText(
	                    this,
	                    String.format("This app does not support the requested Vital Sign: %d", -check),
	                    Toast.LENGTH_SHORT).show();
	            setResult(RESULT_CANCELED);

	            finish();
	        } else if (check > 0) {
	            Toast.makeText(this,
	                    String.format("Requested Vital Sign that will be provided: %d", check),
	                    Toast.LENGTH_SHORT).show();
	        } else { // is 0, so this app run outside of intent

	        }
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		return true;
	}
	
	//define what to do when results are obtained within an intent request
	public void setResult() {

        Intent data = mSharevitals.prepareIntent();

        setResult(RESULT_OK, data);

        finish();
    }


	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.button_get_heartrate	: 

			try {
			     	 if (mSharevitals.chkIntent() > 0) { //check if this is an Intent
					 
			     		 //read fields
					 EditText etf = (EditText)mActivity.findViewById(R.id.editText_heartrateField);
					 EditText etc = (EditText)mActivity.findViewById(R.id.editText_heartrateConfidence);
						
					 try {
					        int hr=Integer.parseInt(etf.getText().toString()); 
					        int hrc=Integer.parseInt(etc.getText().toString()); 
					        mSharevitals.saveVitalSign(ShareVitalSigns.MEASURE_HR, hr, hrc); //store values 
	                  
	                    setResult(); //submit and close
					    }
					    catch (NumberFormatException e) {
					    	Toast.makeText(
				                    this,
				                    String.format("Please provide a numeric value and confidence to send back."),
				                    Toast.LENGTH_SHORT).show();
					    }
	                   
	                } else {
	                	Toast.makeText(
	    	                    this,
	    	                    String.format("Nothing to do. App not started as Intent."),
	    	                    Toast.LENGTH_SHORT).show();
	                }
				 
			
			} catch (Exception e) {
				Toast.makeText(
	                    this,
	                    String.format("Button error."),
	                    Toast.LENGTH_SHORT).show();
			  }

		break;
		
		case R.id.button_get_oxygensaturation: 
			try {
		     	 if (mSharevitals.chkIntent() > 0) { //check if this is an Intent
				 
		     		 //read fields
				 EditText etf = (EditText)mActivity.findViewById(R.id.editText_oxygensaturationField);
				 EditText etc = (EditText)mActivity.findViewById(R.id.editText_oxygensaturationConfidence);
					
				 try {
				        int os=Integer.parseInt(etf.getText().toString()); 
				        int osc=Integer.parseInt(etc.getText().toString()); 
				        mSharevitals.saveVitalSign(ShareVitalSigns.MEASURE_SPO2, os, osc); //store values 
                 
                   setResult(); //submit and close
				    }
				    catch (NumberFormatException e) {
				    	Toast.makeText(
			                    this,
			                    String.format("Please provide a numeric value and confidence to send back."),
			                    Toast.LENGTH_SHORT).show();
				    }
                  
               } else {
               	Toast.makeText(
   	                    this,
   	                    String.format("Nothing to do. App not started as Intent."),
   	                    Toast.LENGTH_SHORT).show();
               }
			 
		
		} catch (Exception e) {
			Toast.makeText(
                   this,
                   String.format("Button error."),
                   Toast.LENGTH_SHORT).show();
		  }

		break;
		case R.id.button_get_respiratoryrate: 
			try {
		     	 if (mSharevitals.chkIntent() > 0) { //check if this is an Intent
				 
		     		 //read fields
				 EditText etf = (EditText)mActivity.findViewById(R.id.editText_respiratoryrateField);
				 EditText etc = (EditText)mActivity.findViewById(R.id.editText_respiratoryrateConfidence);
					
				 try {
				        int rr=Integer.parseInt(etf.getText().toString()); 
				        int rrc=Integer.parseInt(etc.getText().toString()); 
				        mSharevitals.saveVitalSign(ShareVitalSigns.MEASURE_RR, rr, rrc); //store values 
                 
                   setResult(); //submit and close
				    }
				    catch (NumberFormatException e) {
				    	Toast.makeText(
			                    this,
			                    String.format("Please provide a numeric value and confidence to send back."),
			                    Toast.LENGTH_SHORT).show();
				    }
                  
               } else {
               	Toast.makeText(
   	                    this,
   	                    String.format("Nothing to do. App not started as Intent."),
   	                    Toast.LENGTH_SHORT).show();
               }
			 
		
		} catch (Exception e) {
			Toast.makeText(
                   this,
                   String.format("Button error."),
                   Toast.LENGTH_SHORT).show();
		  }

		break;
		case R.id.button_get_temperature: 
			try {
		     	 if (mSharevitals.chkIntent() > 0) { //check if this is an Intent
				 
		     		 //read fields
				 EditText etf = (EditText)mActivity.findViewById(R.id.editText_temperatureField);
				 EditText etc = (EditText)mActivity.findViewById(R.id.editText_temperatureConfidence);
					
				 try {
				        int tp=Integer.parseInt(etf.getText().toString()); 
				        int tpc=Integer.parseInt(etc.getText().toString()); 
				        mSharevitals.saveVitalSign(ShareVitalSigns.MEASURE_TEMP, tp, tpc); //store values 
                 
                   setResult(); //submit and close
				    }
				    catch (NumberFormatException e) {
				    	Toast.makeText(
			                    this,
			                    String.format("Please provide a numeric value and confidence to send back."),
			                    Toast.LENGTH_SHORT).show();
				    }
                  
               } else {
               	Toast.makeText(
   	                    this,
   	                    String.format("Nothing to do. App not started as Intent."),
   	                    Toast.LENGTH_SHORT).show();
               }
			 
		
		} catch (Exception e) {
			Toast.makeText(
                   this,
                   String.format("Button error."),
                   Toast.LENGTH_SHORT).show();
		  }

		break;
		case R.id.button_get_pulseoximetry: 
			try {
		     	 if (mSharevitals.chkIntent() > 0) { //check if this is an Intent
				 
		     		 //read fields
				 EditText etf = (EditText)mActivity.findViewById(R.id.editText_heartrateField);
				 EditText etc = (EditText)mActivity.findViewById(R.id.editText_heartrateConfidence);
				 EditText etsf = (EditText)mActivity.findViewById(R.id.editText_oxygensaturationField);
				 EditText etsc = (EditText)mActivity.findViewById(R.id.editText_oxygensaturationConfidence);
					
				 try {
				        int hr=Integer.parseInt(etf.getText().toString()); 
				        int hrc=Integer.parseInt(etc.getText().toString()); 
				        mSharevitals.saveVitalSign(ShareVitalSigns.MEASURE_HR, hr, hrc); //store values 
				        int sp=Integer.parseInt(etsf.getText().toString()); 
				        int spc=Integer.parseInt(etsc.getText().toString()); 
				        mSharevitals.saveVitalSign(ShareVitalSigns.MEASURE_SPO2, sp, spc); //store values 
                   setResult(); //submit and close
				    }
				    catch (NumberFormatException e) {
				    	Toast.makeText(
			                    this,
			                    String.format("Please provide a numeric value and confidence to send back."),
			                    Toast.LENGTH_SHORT).show();
				    }
                  
               } else {
               	Toast.makeText(
   	                    this,
   	                    String.format("Nothing to do. App not started as Intent."),
   	                    Toast.LENGTH_SHORT).show();
               }
			 
		
		} catch (Exception e) {
			Toast.makeText(
                   this,
                   String.format("Button error."),
                   Toast.LENGTH_SHORT).show();
		  }

		break;
	}
	}

/*
 * (non-Javadoc)
 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
 */




}
