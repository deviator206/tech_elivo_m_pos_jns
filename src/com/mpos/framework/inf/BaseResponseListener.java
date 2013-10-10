/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.inf;

import android.content.Context;

import com.mpos.framework.common.OperationalResult;

/**
 * Description- GridView Fragment.
 * 

 */
public interface BaseResponseListener {

	/**
	 * This is method is always called called by this Base Activity helper
	 * class. it is necessary to write switch case in this method to map web
	 * service request to match with particular response when it is required to
	 * have simultaneously web service request response over network.
	 * 
	 * @param opResult
	 */
	public abstract void executePostAction(OperationalResult opResult);
	
	/**
	 * 
	 * @param opResult
	 */
	public abstract void errorReceived(OperationalResult opResult);
	
	public abstract Context getContext();

}
