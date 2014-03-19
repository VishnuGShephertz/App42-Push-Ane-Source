package com.shephertz.app42.android.flash.push;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
/**
 * @author Vishnu Garg
 *
 */
public class App42PushExtensionContext extends FREContext {

	public App42PushExtensionContext() {
		Log.d("app42", "App42PushExtensionContext");
	}
	
	@Override
	public void dispose() {
		Log.d("app42", "App42PushExtensionContext.dispose");
		App42PushExtension.context = null;
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Log.d("app42", "App42PushExtensionContext.getFunctions");
		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put(ServiceConstants.Register, new PushRegisterFunction());
		functionMap.put(ServiceConstants.LastMessage, new LastPushFunction());
		functionMap.put(ServiceConstants.Reset, new ResetFunction());
		return functionMap;	
	}

}
