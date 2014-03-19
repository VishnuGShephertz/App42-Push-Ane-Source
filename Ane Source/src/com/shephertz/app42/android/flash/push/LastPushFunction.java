
package com.shephertz.app42.android.flash.push;
import android.content.Context;
import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
/**
 * @author Vishnu Garg
 *
 */
public class LastPushFunction implements FREFunction {

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		try {
			App42GCMService.resetCount();
			Context appContext = context.getActivity().getApplicationContext();
			String message=ServiceContext.instance(appContext).getLastMessage();
			context.dispatchStatusEventAsync(message,ServiceConstants.Message);
		} catch (Throwable t) {
			Log.e("app42Push", "Error in getting last Message.", t);
		}
		return null;
	}
}
