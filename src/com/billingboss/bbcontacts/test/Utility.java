package com.billingboss.bbcontacts.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.InstrumentationTestCase;

public class Utility {
	
	public static <T> Activity startActivity(InstrumentationTestCase testcase, 
			String activityName, 
			Instrumentation instrumentation) {
	Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(activityName, null, false);		  

	// Start the activity
	Intent intent = new Intent(Intent.ACTION_MAIN);
	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	intent.setClassName(instrumentation.getTargetContext(), activityName);
	instrumentation.startActivitySync(intent);

	// Wait for it to start...
	Activity currentActivity = testcase.getInstrumentation().waitForMonitorWithTimeout(monitor, 5);
	InstrumentationTestCase.assertTrue("current activity is null", currentActivity != null);
	return currentActivity;
}
}
