package com.shephertz.app42.android.flash.push;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * @author Vishnu Garg
 * 
 */
public class ServiceContext {

	private static ServiceContext mInstance = null;
	SharedPreferences sharedPreference;

	private ServiceContext(Context context) {
		sharedPreference = context.getSharedPreferences(
				ServiceConstants.AppName, context.MODE_PRIVATE);
	}

	/*
	 * instance of class
	 */
	public static ServiceContext instance(Context context) {
		if (mInstance == null) {
			mInstance = new ServiceContext(context);
		}
		return mInstance;
	}

	public String getProjectNo() {
		return sharedPreference.getString(ServiceConstants.KeyProjectNo, null);
	}

	public void saveProjectNo(String projectNo) {
		SharedPreferences.Editor prefEditor = sharedPreference.edit();
		prefEditor.putString(ServiceConstants.KeyProjectNo, projectNo);
		prefEditor.commit();
	}

	public String getLastMessage() {
		return sharedPreference.getString(ServiceConstants.KeyLastMessage, "");
	}

	public void saveLastMessage(String projectNo) {
		SharedPreferences.Editor prefEditor = sharedPreference.edit();
		prefEditor.putString(ServiceConstants.KeyLastMessage, projectNo);
		prefEditor.commit();
	}
}
