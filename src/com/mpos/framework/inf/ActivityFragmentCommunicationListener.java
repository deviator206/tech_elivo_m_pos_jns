package com.mpos.framework.inf;

import com.mpos.master.model.UserRightsModel;

public interface ActivityFragmentCommunicationListener {

	public void userAuthenticationSuccess(UserRightsModel userRights);
	public void userAuthenticationError();
}
