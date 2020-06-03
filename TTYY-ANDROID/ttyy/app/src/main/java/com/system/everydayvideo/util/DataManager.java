/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.system.everydayvideo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.system.everydayvideo.App;
import com.system.everydayvideo.Bean.LoginBean;

/**数据工具类
 * @author Lemon
 */
public class DataManager {
	private final String TAG = "DataManager";

	private Context context;
	private DataManager(Context context) {
		this.context = context;
	}

	private static DataManager instance;
	public static DataManager getInstance() {
		if (instance == null) {
			synchronized (DataManager.class) {
				if (instance == null) {
					instance = new DataManager(App.getInstance());
				}
			}
		}
		return instance;
	}

	//用户 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	private String PATH_Persion = "PATH_Persion";

	public final String KEY_Persion = "KEY_Persion";
	public final String KEY_Persion_ID = "KEY_Persion_ID";
	public final String KEY_Persion_NAME = "KEY_Persion_NAME";
	public final String KEY_Persion_PHONE = "KEY_Persion_PHONE";

	public final String KEY_CURRENT_Persion_ID = "KEY_CURRENT_Persion_ID";
	public final String KEY_LAST_Persion_ID = "KEY_LAST_Persion_ID";


	/**判断是否为当前用户
	 * @param //context
	 * @param PersionId
	 * @return
	 */
	public boolean isCurrentPersion(long PersionId) {
		return PersionId > 0 && PersionId == getCurrentPersionId();
	}

	/**获取当前用户id
	 * @param //context
	 * @return
	 */
	public long getCurrentPersionId() {
		LoginBean.Login Persion = getCurrentPersion();
		return Persion == null ? 0 : Persion.getId();
	}

	/**获取当前用户的手机号
	 * @param //context
	 * @return
	 */
	public String getCurrentPersionPhone() {
		LoginBean.Login Persion = getCurrentPersion();
		return Persion == null ? "" : Persion.getMobile();
	}
	/**获取当前用户
	 * @param //context
	 * @return
	 */
	public LoginBean.Login getCurrentPersion() {
		SharedPreferences sdf = context.getSharedPreferences(PATH_Persion, Context.MODE_PRIVATE);
		return sdf == null ? null : getPersion(sdf.getLong(KEY_CURRENT_Persion_ID, 0));
	}


	/**获取最后一次登录的用户的手机号
	 * @param //context
	 * @return
	 */
	public String getLastPersionPhone() {
		LoginBean.Login Persion = getLastPersion();
		return Persion == null ? "" : Persion.getMobile();
	}

	/**获取最后一次登录的用户
	 * @param //context
	 * @return
	 */
	public LoginBean.Login getLastPersion() {
		SharedPreferences sdf = context.getSharedPreferences(PATH_Persion, Context.MODE_PRIVATE);
		return sdf == null ? null : getPersion(sdf.getLong(KEY_LAST_Persion_ID, 0));
	}

	/**获取用户
	 * @param //context
	 * @param PersionId
	 * @return
	 */
	public LoginBean.Login getPersion(long PersionId) {
		SharedPreferences sdf = context.getSharedPreferences(PATH_Persion, Context.MODE_PRIVATE);
		if (sdf == null) {
			Log.e(TAG, "get sdf == null >>  return;");
			return null;
		}
		Log.i(TAG, "getPersion  PersionId = " + PersionId);
		return JSON.parseObject(sdf.getString(StringUtil.getTrimedString(PersionId), null), LoginBean.Login.class);
	}


	/**保存当前用户,只在登录或注销时调用
	 * @param //context
	 * @param Persion  Persion == null >> Persion = new Persion();
	 */
	public void saveCurrentPersion(LoginBean.Login Persion) {
		SharedPreferences sdf = context.getSharedPreferences(PATH_Persion, Context.MODE_PRIVATE);
		if (sdf == null) {
			Log.e(TAG, "savePersion sdf == null  >> return;");
			return;
		}
		if (Persion == null) {
			Log.w(TAG, "savePersion  Persion == null >>  Persion = new Persion();");
			Persion = new LoginBean.Login();
		}
		SharedPreferences.Editor editor = sdf.edit();
		editor.remove(KEY_LAST_Persion_ID).putLong(KEY_LAST_Persion_ID, getCurrentPersionId());
		editor.remove(KEY_CURRENT_Persion_ID).putLong(KEY_CURRENT_Persion_ID, Persion.getId());
		editor.commit();

		savePersion(sdf, Persion);
	}

	/**保存用户
	 * @param //context
	 * @param Persion
	 */
	public void savePersion(LoginBean.Login Persion) {
		savePersion(context.getSharedPreferences(PATH_Persion, Context.MODE_PRIVATE), Persion);
	}
	/**保存用户
	 * @param //context
	 * @param sdf
	 * @param Persion
	 */
	public void savePersion(SharedPreferences sdf, LoginBean.Login Persion) {
		if (sdf == null || Persion == null) {
			Log.e(TAG, "savePersion sdf == null || Persion == null >> return;");
			return;
		}
		String key = StringUtil.getTrimedString(Persion.getId());
		Log.i(TAG, "savePersion  key = Persion.getId() = " + Persion.getId());
		sdf.edit().remove(key).putString(key, JSON.toJSONString(Persion)).commit();
	}

	/**删除用户
	 * @param //context
	 * @param sdf
	 */
	public void removePersion(SharedPreferences sdf, long PersionId) {
		if (sdf == null) {
			Log.e(TAG, "removePersion sdf == null  >> return;");
			return;
		}
		sdf.edit().remove(StringUtil.getTrimedString(PersionId)).commit();
	}

	/**设置当前用户手机号
	 * @param //context
	 * @param phone
	 */
	public void setCurrentPersionPhone(String phone) {
		LoginBean.Login Persion = getCurrentPersion();
		if (Persion == null) {
			Persion = new LoginBean.Login();
		}
		Persion.setMobile(phone);
		savePersion(Persion);
	}

	/**设置当前用户姓名
	 * @param //context
	 * @param name
	 */
	public void setCurrentPersionName(String name) {
		LoginBean.Login Persion = getCurrentPersion();
		if (Persion == null) {
			Persion = new LoginBean.Login();
		}
		Persion.setNickname(name);
		savePersion(Persion);
	}

	//用户 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>




}
