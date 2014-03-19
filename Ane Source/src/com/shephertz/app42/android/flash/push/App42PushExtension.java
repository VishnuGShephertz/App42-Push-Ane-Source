package com.shephertz.app42.android.flash.push;
import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;
/**
 * @author Vishnu Garg
 *
 */
public class App42PushExtension implements FREExtension {
	public static FREContext context;
	
	@Override
	public FREContext createContext(String extId) {
		Log.d("app42Push", "App42PushExtension.App42PushExtension extId: " + extId);
		return context = new App42PushExtensionContext();
	}

	@Override
	public void dispose() {
		Log.d("app42Push", "App42PushExtension.dispose");
		context = null;
	}

	@Override
	public void initialize() {
		Log.d("app42Push", "App42PushExtension.initialize");
	}
}
