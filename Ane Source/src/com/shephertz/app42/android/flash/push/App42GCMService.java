package com.shephertz.app42.android.flash.push;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.adobe.fre.FREContext;

/**
 * @author Vishnu Garg
 *
 */
public class App42GCMService extends GCMBaseIntentService {

	public static int msgCount = 0;
	public static String notificationMessage = "";
	static String projectNo = "<Your Project No>";

	@Override
	protected void onError(Context arg0, String arg1) {
		Log.i(TAG, "Device registered: regId = " + arg1);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String jsonmessage = intent.getExtras().getString("message");
		JSONObject jsonMessage=null;
		try {
			jsonMessage = getMessageJson(jsonmessage);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {if(jsonMessage!=null){
			String message=getMessage(jsonMessage);
			displayMessage(context, message);
			ServiceContext.instance(context).saveLastMessage(message);
			generateNotification(context, jsonMessage);
			FREContext freContext = App42PushExtension.context;
			if (freContext != null) {
				freContext.dispatchStatusEventAsync(message, ServiceConstants.Message);
			}
		}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private JSONObject getMessageJson(String message) throws JSONException{
		JSONObject json;
		try {
			return new JSONObject(message);
		} catch (Throwable e1) {
			 json=new JSONObject();
			json.put("message", message);
			return json;
		}
	}
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = "" + total + "Message deleted ";
		displayMessage(context, message);
		try {
			generateNotification(context,  new JSONObject(message));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		FREContext freContext = App42PushExtension.context;
		if (freContext != null) {
			freContext.dispatchStatusEventAsync(registrationId,ServiceConstants.Register);
		}
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.i(TAG, "onUnregistered " + arg1);
	
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	private void generateNotification(Context context, JSONObject message) throws Exception{
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent notificationIntent = new Intent(context, 
				Class.forName(context.getPackageName() + ".AppEntry"));
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		    Notification notification = new Notification.Builder(context)
				.setContentTitle(getTitle(message,context))
				.setContentText(getMessage(message)).
				 setContentIntent(intent)
				//.setSmallIcon(freContext.getResourceId("drawable.notify"))
				 .setSmallIcon(android.R.drawable.ic_dialog_info)
				.setWhen(when)
				.setNumber(++msgCount)
				.setLargeIcon(getBitmapFromURL(getImageUrl(message)))
				.setLights(Color.YELLOW, 1, 2)
				.setAutoCancel(true).getNotification();

		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);
	}

	private String getMessage(JSONObject msgJson){
		try{
			return msgJson.getString("message");
		}
		catch(Throwable t){
			return "Message";
		}
	
	}
	private String getTitle(JSONObject msgJson,Context context){
		try{
			return msgJson.getString(ServiceConstants.Title);
		}
		catch(Throwable t){
			ApplicationInfo ai;
			try {
				ai = context.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
				Bundle aBundle=ai.metaData;
				return aBundle.getString("push_title");
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "App42PushSample";
			}
		
			
		}
	
	}
	
	private String getImageUrl(JSONObject msgJson){
		try{
			return msgJson.getString(ServiceConstants.ImageUrl);
		}
		catch(Throwable t){
			return "";
		}
	}
	
	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void resetCount(){
		msgCount=0;
	}
	

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(ServiceConstants.DisplayMessageAction);
		intent.putExtra(ServiceConstants.NotificationMessage, message);
		context.sendBroadcast(intent);
	}

}
