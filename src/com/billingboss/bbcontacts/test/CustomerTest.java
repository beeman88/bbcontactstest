package com.billingboss.bbcontacts.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;

import com.billingboss.bbcontacts.BBContactsDBAdapter;
import com.billingboss.bbcontacts.CustomerActivity;

import com.billingboss.bbcontacts.test.Utility;

public class CustomerTest extends ActivityInstrumentationTestCase2<CustomerActivity> {

	public CustomerTest() {
		super("com.billingboss.bbcontacts", CustomerActivity.class);
	} // end of CustomerActivity Test constructor definition
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
	} // end of setUp() method definition
	
    @SmallTest
    public void testPreconditions() {
		Instrumentation instrumentation = getInstrumentation();

		CustomerActivity mActivity = (CustomerActivity) Utility.startActivity(
				this,
				CustomerActivity.class.getName(), 
				instrumentation
		);
		
		ListView list = 
			(ListView) mActivity.getListView();
    	
		Button getCust = 
			(Button) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.button_get_cust
			);

		Button setCust = 
			(Button) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.button_set_cust
			);

        assertNotNull("list should not be null", list);
        assertNotNull("get Customer button should not be null", getCust);
        assertNotNull("save Customer button should not be null", setCust);
        
        assertTrue(String.format("Get Customers button right edge %d should be < Save Customers button left edge %d", getCust.getRight(), setCust.getLeft()),
                getCust.getRight() < setCust.getLeft());
        
		mActivity.finish();
    }	

    public void testGetCustomer() {
    	
		Instrumentation instrumentation = getInstrumentation();

		// clear existing customers from database
		CustomerActivity mActivity = (CustomerActivity) Utility.startActivity(
				this,
				CustomerActivity.class.getName(), 
				instrumentation
		);
		
		BBContactsDBAdapter mDbHelper = new BBContactsDBAdapter(mActivity);
		mDbHelper.open();
		
		// reset database
		mDbHelper.deleteCustomers();
		mDbHelper.deleteContacts();
		
		mActivity.finish();
		
		mActivity = (CustomerActivity) Utility.startActivity(
				this,
				CustomerActivity.class.getName(), 
				instrumentation
		);
		
		Button getCust = 
			(Button) mActivity.findViewById(
					com.billingboss.bbcontacts.R.id.button_get_cust
			);
		
		this.sendKeys( KeyEvent.KEYCODE_DPAD_DOWN );
		
		assertTrue("Get Customer button should have focus",
        		getCust.hasFocus());
		
        sendKeys( KeyEvent.KEYCODE_DPAD_CENTER );		
		
		ListView list = 
			(ListView) mActivity.getListView();
		
		assertTrue(String.format("list has %d elements", list.getCount()), 
				list.getCount() > 0);
		
		mDbHelper.close();		
		mActivity.finish();		
    	
    }



}
