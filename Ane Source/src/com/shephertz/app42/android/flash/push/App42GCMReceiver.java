package com.shephertz.app42.android.flash.push;

import android.content.Context;

/**
 * @author Vishnu Garg
 *
 */
public class App42GCMReceiver extends GCMBroadcastReceiver{
	@Override
	protected String getGCMIntentServiceClassName(Context context) { 
		return "com.shephertz.app42.android.flash.push.App42GCMService"; 
	} 
}
