package com.billingboss.bbcontacts.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import com.billingboss.bbcontacts.BBContactsDBAdapter;
import com.billingboss.bbcontacts.CustomerActivity;
import com.billingboss.bbcontacts.SettingsActivity;

import com.billingboss.bbcontacts.test.Utility;

public class SettingsTest extends ActivityInstrumentationTestCase2<SettingsActivity> {
	
	public SettingsTest() {
		super("com.billingboss.bbcontacts", SettingsActivity.class);
	} // end of SettingsActiviy Test constructor definition	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
	} // end of setUp() method definition	
	
    @SmallTest
    public void testPreconditions() {
		Instrumentation instrumentation = getInstrumentation();

		SettingsActivity mActivity = (SettingsActivity) Utility.startActivity(
				this,
				SettingsActivity.class.getName(), 
				instrumentation
		);
		
		EditText username = 
			(EditText) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.username
			);
    	
		EditText password = 
			(EditText) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.password
			);

		Button save = 
			(Button) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.save
			);

        assertNotNull("username should not be null", username);
        assertNotNull("password should not be null", password);
        assertNotNull("save should not be null", save);
        
        assertTrue("username should be above password",
                username.getBottom() < password.getTop());
/*        assertTrue("password should be above save",
                password.getBottom() < save.getTop());
*/        assertTrue("username should have focus",
        		username.hasFocus());

		mActivity.finish();
    }
	
    public void testCustomerMenu() {
		Instrumentation instrumentation = getInstrumentation();		  
		SettingsActivity mActivity = (SettingsActivity) Utility.startActivity(
					this,
					SettingsActivity.class.getName(), 
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
    
	public void testInitialSettings() {
		Instrumentation instrumentation = getInstrumentation();

		SettingsActivity mActivity = (SettingsActivity) Utility.startActivity(
				this,
				SettingsActivity.class.getName(), 
				instrumentation
		);

		BBContactsDBAdapter mDbHelper = new BBContactsDBAdapter(mActivity);
		mDbHelper.open();
		// Get settings cursor for the first row
		Cursor c = mDbHelper.fetchSetting(1);

		String db_username = "";
		String db_password = "";

		if (c != null) {
			try {
				// if cursor has rows, already moved to first
				mActivity.startManagingCursor(c);
				db_username = c.getString(c.getColumnIndex(BBContactsDBAdapter.SETTINGS_USERNAME));            	
				db_password = c.getString(c.getColumnIndex(BBContactsDBAdapter.SETTINGS_PASSWORD));
			}
			finally {
				c.close();
			}
		}		

		EditText  username =
			(EditText) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.username
			);

		assertTrue(username.getText().toString().equals(db_username));

		EditText  password =
			(EditText) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.password
			);

		assertTrue(password.getText().toString().equals(db_password));
		mDbHelper.close();
		mActivity.finish();
	}
	
	public void testUpdateSettings() {
		Instrumentation instrumentation = getInstrumentation();

		SettingsActivity mActivity = (SettingsActivity) Utility.startActivity(
				this,
				SettingsActivity.class.getName(), 
				instrumentation
		);
		
		final EditText  username =
			(EditText) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.username
			);
		
		final EditText password = 
			(EditText) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.password
			);
		
		final Button save = 
			(Button) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.save
			);
		
		assertTrue("username should have focus",
        		username.hasFocus());		
		
	    this.sendRepeatedKeys(getTextLength(username), KeyEvent.KEYCODE_DEL);
		this.sendKeys(KeyEvent.KEYCODE_DEL);
		this.sendKeys( KeyEvent.KEYCODE_A );
		this.sendKeys( KeyEvent.KEYCODE_DPAD_DOWN );
		
		assertTrue("password should have focus",
        		password.hasFocus());		

		sendRepeatedKeys(getTextLength(password), KeyEvent.KEYCODE_DEL);	    
		sendKeys( KeyEvent.KEYCODE_B );
		
		sendKeys( KeyEvent.KEYCODE_DPAD_DOWN );
		
		assertTrue("save should have focus",
        		save.hasFocus());		
		
        sendKeys( KeyEvent.KEYCODE_DPAD_CENTER );		
		
		BBContactsDBAdapter mDbHelper = new BBContactsDBAdapter(mActivity);
		mDbHelper.open();
		// Get settings cursor for the first row
		Cursor c = mDbHelper.fetchSetting(1);

		String dbUsername = "";
		String dbPassword = "";

		if (c != null) {
			try {
				// if cursor has rows, already moved to first
				mActivity.startManagingCursor(c);
				dbUsername = c.getString(c.getColumnIndex(BBContactsDBAdapter.SETTINGS_USERNAME));            	
				dbPassword = c.getString(c.getColumnIndex(BBContactsDBAdapter.SETTINGS_PASSWORD));
			}
			finally {
				c.close();
			}
		}	
		
		assertTrue(String.format("Saved Db username %s does not equal a", dbUsername), dbUsername.equals("a"));
		assertTrue(String.format("Save Db password %s does not equal b", dbPassword), dbPassword.equals("b"));		
		
		mDbHelper.close();
		mActivity.finish();
	}
	
	private int getTextLength(EditText edittext) {
		return edittext.getText().length();
	}
}
	
	



