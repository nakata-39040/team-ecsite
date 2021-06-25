package jp.co.internous.wasabi.model.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginSession implements Serializable{

	private static final long serialVersionUID = 8666281809501551306L;
	
	private int userId = 0;
	private int tempUserId = 0;
	private String userName = null;
	private String password = null;
	private boolean isLogin = false;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getTempUserId() {
		return tempUserId;
	}
	
	public void setTempUserId(int tempUserId) {
		this.tempUserId = tempUserId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getLogin() {
		return isLogin;
	}
	
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	

}
