package com.cse5236.screenaddict;

import com.cse5236.screenaddict.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;

public class SplashScreen extends Activity {
	protected Boolean isActive = true;
	protected int splashTimeout = 5000, timeIncrement = 100, sleepTime = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		Thread splashThread = new Thread() {
			@Override
	        public void run() {
	            try {
	                while(isActive && (splashTimeout > 0)) {
	                    sleep(sleepTime);
	                    if(isActive) splashTimeout -= timeIncrement;
	                }
	            } catch(InterruptedException e) {
	                // do nothing
	            	System.out.println(e.getMessage());
	            } finally {
	                finish();
	                // TODO: start next activity (presumably a login screen)
	                startActivity(new Intent("com.cse5236.screenaddict.Home"));
	            }
	        }
	    };
	    splashThread.start();
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isActive = false;
        }
        return true;
    }

}
