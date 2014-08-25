package org.ecemgroup.sharevitalsignsdemoreceiver;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.ecemgroup.sharevitalsigns.library.ShareVitalSigns;
import org.ecemgroup.sharevitalsigns.library.ShareVitalSigns.ShareVitalSignsResultReceiver;
import org.ecemgroup.sharevitalsignsdemoreceiver.R;


public class MainReceiverActivity extends Activity implements OnClickListener {

	
	private Activity mActivity;
	
	//init ShareVitalSigns Result
	ShareVitalSigns sharevitals = new ShareVitalSigns(0); 

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
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		return true;
	}


	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.button_get_heartrate	: 

			try {
			startActivityForResult(sharevitals.measureVitalSigns(new ShareVitalSignsResultReceiver() {
				@Override
				public void onResult(float[] vital, int[] confidence) {

					EditText et = (EditText)mActivity.findViewById(R.id.editText_heartrateField);
					et.setText(String.valueOf((int)vital[0]));

					ImageButton bt = (ImageButton)mActivity.findViewById(R.id.img_heartrateConfidence);
					if (confidence[0]> 50){
						bt.setImageResource(R.drawable.conf_ok);
						bt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

					} else {
						bt.setImageResource(R.drawable.conf_bad);
						bt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
					} 
				}
				
			},ShareVitalSigns.MEASURE_HR),ShareVitalSigns.MEASURE_HR);
			} catch (Exception e) {
				Toast.makeText(
	                    this,
	                    String.format("No App providing HR found on device."),
	                    Toast.LENGTH_SHORT).show();
			  }

		break;
		
		case R.id.button_get_oxygensaturation: 
			try {
			startActivityForResult(sharevitals.measureVitalSigns(new ShareVitalSignsResultReceiver() {
				@Override
				public void onResult(float[] vital, int[] confidence) {

					EditText et = (EditText)mActivity.findViewById(R.id.editText_oxygensaturationField);
					et.setText(String.valueOf((int)vital[0]));

					ImageButton bt = (ImageButton)mActivity.findViewById(R.id.img_oxygensaturationConfidence);
					if (confidence[0]> 50){
						bt.setImageResource(R.drawable.conf_ok);
						bt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

					} else {
						bt.setImageResource(R.drawable.conf_bad);
						bt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
					} 
		
				}
			},ShareVitalSigns.MEASURE_SPO2),ShareVitalSigns.MEASURE_SPO2);
		} catch (Exception e) {
			Toast.makeText(
                    this,
                    String.format("No App providing SpO2 found on device."),
                    Toast.LENGTH_SHORT).show();
		  }

		break;
		case R.id.button_get_respiratoryrate: 
			try {
			startActivityForResult(sharevitals.measureVitalSigns(new ShareVitalSignsResultReceiver() {
				@Override
				public void onResult(float[] vital, int[] confidence) {

					EditText et = (EditText)mActivity.findViewById(R.id.editText_respiratoryrateField);
					et.setText(String.valueOf((int)vital[0]));

					ImageButton bt = (ImageButton)mActivity.findViewById(R.id.img_respiratoryrateConfidence);
					if (confidence[0]> 50){
						bt.setImageResource(R.drawable.conf_ok);
						bt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

					} else {
						bt.setImageResource(R.drawable.conf_bad);
						bt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
					} 
		
				}
			},ShareVitalSigns.MEASURE_RR),ShareVitalSigns.MEASURE_RR);
	} catch (Exception e) {
		Toast.makeText(
                this,
                String.format("No App providing RR found on device."),
                Toast.LENGTH_SHORT).show();
	  }

		break;
		case R.id.button_get_temperature: 
			try {
			startActivityForResult(sharevitals.measureVitalSigns(new ShareVitalSignsResultReceiver() {
				@Override
				public void onResult(float[] vital, int[] confidence) {

					EditText et = (EditText)mActivity.findViewById(R.id.editText_temperatureField);
					et.setText(String.valueOf((int)vital[0]));

					ImageButton bt = (ImageButton)mActivity.findViewById(R.id.img_temperatureConfidence);
					if (confidence[0]> 50){
						bt.setImageResource(R.drawable.conf_ok);
						bt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

					} else {
						bt.setImageResource(R.drawable.conf_bad);
						bt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
					} 
		
				}
			},ShareVitalSigns.MEASURE_TEMP),ShareVitalSigns.MEASURE_TEMP);
} catch (Exception e) {
	Toast.makeText(
            this,
            String.format("No App providing Temperature found on device."),
            Toast.LENGTH_SHORT).show();
  }

		break;
		case R.id.button_get_pulseoximetry: 
try {
			startActivityForResult(sharevitals.measureVitalSigns(new ShareVitalSignsResultReceiver() {
				@Override
				public void onResult(float[] vital, int[] confidence) {

					EditText et = (EditText)mActivity.findViewById(R.id.editText_heartrateField);
					et.setText(String.valueOf((int)vital[0]));

					ImageButton bt = (ImageButton)mActivity.findViewById(R.id.img_heartrateConfidence);
					if (confidence[0]> 50){
						bt.setImageResource(R.drawable.conf_ok);
						bt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

					} else {
						bt.setImageResource(R.drawable.conf_bad);
						bt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
					} 
					 et = (EditText)mActivity.findViewById(R.id.editText_oxygensaturationField);
					et.setText(String.valueOf((int)vital[1]));

					bt = (ImageButton)mActivity.findViewById(R.id.img_oxygensaturationConfidence);
					if (confidence[1]> 50){
						bt.setImageResource(R.drawable.conf_ok);
						bt.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.DARKEN);

					} else {
						bt.setImageResource(R.drawable.conf_bad);
						bt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.DARKEN);
					} 
		
				}
			},ShareVitalSigns.MEASURE_PO),ShareVitalSigns.MEASURE_PO);
		} catch (Exception e) {
			Toast.makeText(
                    this,
                    String.format("No App providing pulse oximetry found on device."),
                    Toast.LENGTH_SHORT).show();
		  }

		break;
	}
	}

/*
 * (non-Javadoc)
 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
 */

	@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * To check and process the received data in the Intent, execute 
		 * getResults(int requestCode, int resultCode, Intent data) it will return "true" if 
		 * the requested vital sign was obtained, "false" otherwise
		 */
			if (sharevitals.getResults(requestCode, resultCode,  data)){ //process Intent data
				
					//Do something here if necessary
			
			} else {
				Toast.makeText(
	                    this,
	                    String.format("WARNING: No valid vital sign obtained"),
	                    Toast.LENGTH_SHORT).show();
			}
		}



}
