package com.shephertz.app42.android.flash.push;
import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
/**
 * @author Vishnu Garg
 *
 */
public class ResetFunction implements FREFunction {

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		Log.d("app42", "Reser function.call");
		try {
			App42GCMService.resetCount();
		} catch (Throwable e) {
			Log.e("app42", "Error sending unregister intent.", e);
		}
		return null;
	}
}
