package com.example.demo.flux;

public interface TobyEventListener {

	void onEvent(TobyEvent event);
	
	void onError(Throwable e);
	
	void onComplete();
}
