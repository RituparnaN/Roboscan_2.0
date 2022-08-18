package com.quantumdataengines.app.dao.usermanagement;

import java.util.List;
import com.quantumdataengines.app.config.checkermanagement.MakerCheckerStatus;
import com.quantumdataengines.app.model.DomainUser;
import com.quantumdataengines.app.model.TempUser;
import com.quantumdataengines.app.model.User;

public interface UserManagementDAO {

	public User getUserByUsername(String username);
	public List<User> getVerifiedUserList();
	TempUser createTempUser(TempUser tempUser);
	boolean isPendingUser(String userName, MakerCheckerStatus pending);
	List<TempUser> getTempUserList();
	boolean isUserPresent(String username, String currentUserName, String activeRole, String ipAddress);
	String assignRole(String username, List<String> roleList, User currentUser, String activeRole, String ipAddress);
}
