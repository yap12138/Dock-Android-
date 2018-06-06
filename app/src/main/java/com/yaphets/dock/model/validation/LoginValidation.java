package com.yaphets.dock.model.validation;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yaphets.dock.database.dao.MySqlDAO;
import com.yaphets.dock.model.Accessible;
import com.yaphets.dock.model.UserInfo;

public class LoginValidation implements Validator {
	private static final String TAG = "LoginValidation";

	/**
	 * validation email and password, return code which identified query result
	 * @param item	UserInfo instance
	 * @return
	 * code -1: no such user.
	 * code -2: incorrect password.
	 * code 1: pass validation.
	 */
	@Override
	public Result isValid(Accessible item) {
		if (! (item instanceof UserInfo)) {
			throw new IllegalArgumentException("accessible不是UserInfo实例对象");
		}
		UserInfo info = (UserInfo) item;
		
		Connection con = null;
		PreparedStatement ps = null;
		Result rst = new Result();

		try {
			con = MySqlDAO.getConnection();
			String sql = "SELECT password FROM user WHERE email=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, info.getEmail());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String passwd = rs.getString(1);
				if (passwd.equals(info.getPassword())) {
					rst.setCode(1);
				} else {
					rst.setCode(-2);	//incorrect password
				}
			} else {
				rst.setCode(-1);	//no such user
			}
			return rst;
		} catch (SQLException e) {
			Log.e(TAG, "isValid: ", e);
			rst.setCode(e.getErrorCode());
			rst.setMessage(e.getMessage());
			return rst;
		} finally {
			MySqlDAO.release(con, ps);
		}
	}
}
