package jp.co.internous.wasabi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jp.co.internous.wasabi.model.domain.MstUser;
import jp.co.internous.wasabi.model.form.UserForm;
import jp.co.internous.wasabi.model.mapper.MstUserMapper;
import jp.co.internous.wasabi.model.mapper.TblCartMapper;
import jp.co.internous.wasabi.model.session.LoginSession;

@RestController
@RequestMapping("/wasabi/auth")
public class AuthController {
	
	@Autowired
	private LoginSession loginSession;
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private TblCartMapper CartMapper;
	
	private Gson gson = new Gson();
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody UserForm form) {
		MstUser users = userMapper.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		if(users != null) {
			
			CartMapper.updateUserId(users.getId(), loginSession.getTempUserId());
			
			loginSession.setUserId(users.getId());
			loginSession.setTempUserId(0);
			loginSession.setUserName(users.getUserName());
			loginSession.setPassword(users.getPassword());
			loginSession.setLogin(true);
		} else {
			loginSession.setUserId(0);
			loginSession.setUserName(null);
			loginSession.setPassword(null);
			loginSession.setLogin(false);

		}
		return gson.toJson(users);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(Model model) {
		loginSession.setUserId(0);
		loginSession.setTempUserId(0);
		loginSession.setUserName(null);
		loginSession.setPassword(null);
		loginSession.setLogin(false);
		
		return "";
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword(@RequestBody UserForm form) {
		String message = "パスワードが再設定されました。";
		String newPassword = form.getNewPassword();
		String newPasswordConfirm = form.getNewPasswordConfirm();
		
		MstUser user = userMapper.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		if (user == null) {
			return "現在のパスワードが正しくありません。";
		}
		
		if (user.getPassword().equals(newPassword)) {
			return "現在のパスワードと同一文字列が入力されました。";
		}
		
		if (!newPassword.equals(newPasswordConfirm)) {
			return "新パスワードと確認用パスワードが一致しません。";
		}
		
		MstUser m = new MstUser();
		m.setPassword(form.getNewPassword());
		m.setUserName(user.getUserName());
		userMapper.update(m);
		loginSession.setPassword(form.getNewPassword());
		
		
		return message;
		
	}

}
