package com.billingboss.bbcontacts.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.billingboss.bbcontacts.CustomerActivity;
import com.billingboss.bbcontacts.SettingsActivity;
import com.billingboss.bbcontacts.bbcontacts;

import com.billingboss.bbcontacts.test.Utility;

public class BBContactsTest extends ActivityInstrumentationTestCase2<bbcontacts> {

	public BBContactsTest() {
		super("com.billingboss.bbcontacts", bbcontacts.class);
	} // end of BBContacts Test constructor definition

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
	} // end of setUp() method definition

	public void testLogoExists() {
		Instrumentation instrumentation = getInstrumentation();		  
		bbcontacts mActivity = (bbcontacts) Utility.startActivity(
				this,
				bbcontacts.class.getName(), 
				instrumentation
				);
		ImageView  mImage =
			(ImageView) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.logo
			);

		assertTrue(mImage != null);
		mActivity.finish();
	} 

	public void testCustomerMenu() {
		Instrumentation instrumentation = getInstrumentation();		  
		bbcontacts mActivity = (bbcontacts) Utility.startActivity(
				this,
				bbcontacts.class.getName(), 
				instrumentation
				);
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
					} // end of run() method definition
				} // end of anonymous Runnable object instantiation
		); // end of invocation of runOnUiThread

		ActivityMonitor am = instrumentation.addMonitor(CustomerActivity.class.getName(), null, false);		    
		instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);

		assertTrue(instrumentation.invokeMenuActionSync(mActivity, com.billingboss.bbcontacts.R.id.customers, 0));

		Activity a = instrumentation.waitForMonitorWithTimeout(am, 1000);
		assertTrue(instrumentation.checkMonitorHit(am, 1));
		a.finish();
		mActivity.finish();
	}
	
	public void testSettingsMenu() {
		Instrumentation instrumentation = getInstrumentation();		  
		bbcontacts mActivity = (bbcontacts) Utility.startActivity(
				this,
				bbcontacts.class.getName(), 
				instrumentation
				);		  
		mActivity.runOnUiThread(
				new Runnable() {
					public void run() {
					} // end of run() method definition
				} // end of anonymous Runnable object instantiation
		); // end of invocation of runOnUiThread

		ActivityMonitor am = instrumentation.addMonitor(SettingsActivity.class.getName(), null, false);		    
		instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);

		assertTrue(instrumentation.invokeMenuActionSync(mActivity, com.billingboss.bbcontacts.R.id.settings, 0));

		Activity a = instrumentation.waitForMonitorWithTimeout(am, 1000);
		assertTrue(instrumentation.checkMonitorHit(am, 1));
		a.finish();
		mActivity.finish();
	}	
}
