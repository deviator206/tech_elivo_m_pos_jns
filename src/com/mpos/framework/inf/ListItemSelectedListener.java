/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.framework.inf;

import com.mpos.master.model.BaseModel;

/**
 * Description-
 * 

 */

public interface ListItemSelectedListener {

	/**
	 * This is method is always called called by this GridViewFragment class.
	 * @param kind
	 * @param model
	 */
	public abstract void gridItemSelected(int kind, BaseModel model);

}
