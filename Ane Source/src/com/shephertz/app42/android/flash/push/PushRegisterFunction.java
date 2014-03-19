package com.shephertz.app42.android.flash.push;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * @author Vishnu Garg
 *
 */
public class PushRegisterFunction implements FREFunction{
	
	private ServiceContext serviceContext;
	/*
	 * This function allows to register device for PushNotification service
	 */
	public void registerOnGCM(Context context,String projectNo) {
		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);
		final String deviceId = GCMRegistrar.getRegistrationId(context);
		if (deviceId.equals("")) {
			try {
				String[] str={projectNo};
				GCMRegistrar.register(context, str);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			try {
				//Code for flash call for store device token
				FREContext freContext = App42PushExtension.context;
				if (freContext != null) {
					freContext.dispatchStatusEventAsync(deviceId,ServiceConstants.Register);
				}
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
	}
	public void setProjectNo(String projectNo) {
		App42GCMService.projectNo = projectNo;
		serviceContext.saveProjectNo(projectNo);
	}
	@Override
	public FREObject call(FREContext context, FREObject[] arg1) {
		try {
			Context appContext = context.getActivity().getApplicationContext();
			serviceContext=ServiceContext.instance(appContext);
			String projectNo=arg1[0].getAsString();
			setProjectNo(projectNo);
			if(haveNetworkConnection(appContext))
			registerOnGCM(appContext,projectNo);
			else
			context.dispatchStatusEventAsync(ServiceConstants.ConnectionMsg,ServiceConstants.Message);
		} catch (Throwable t) {
			Log.e("app42Push", "Error in getting last Message.", t);
		}
		return null;
	}

	/*
	 * This method is used to check availability of network connection in
	 * android device uses CONNECTIVITY_SERVICE of android device to get desired
	 * network internet connection
	 * 
	 * @return status of availability of internet connection in true or false
	 * manner
	 */
	public static boolean haveNetworkConnection(Context context) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] netInfo = cm.getAllNetworkInfo();
			for (NetworkInfo ni : netInfo) {
				if (ni.getTypeName().equalsIgnoreCase("WIFI"))
					if (ni.isConnected())
						haveConnectedWifi = true;
				if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
					if (ni.isConnected())
						haveConnectedMobile = true;
			}

		} catch (Exception e) {

		}
		return haveConnectedWifi || haveConnectedMobile;
	}
}
