package com.shmtu.myprojectforsmu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shmtu.myprojectforsmu.complany.ComplanyManaFragment;
import com.shmtu.myprojectforsmu.customer.CustomerInfoFragment;
import com.shmtu.myprojectforsmu.setting.SettingFragment;
import com.shmtu.myprojectforsmu.task.TaskManaFragment;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	/**
	 * 用于展示消息的Fragment
	 */
	private CustomerInfoFragment customerInfoFragment;

	/**
	 * 用于展示联系人的Fragment
	 */
	private ComplanyManaFragment complanyManaFragment;

	/**
	 * 用于展示动态的Fragment
	 */
	private TaskManaFragment taskManaFragment;

	/**
	 * 用于展示设置的Fragment
	 */
	private SettingFragment settingFragment;

	/**
	 * 消息界面布局
	 */
	private View taskLayout;

	/**
	 * 联系人界面布局
	 */
	private View contactsLayout;

	/**
	 * 动态界面布局
	 */
	private View newsLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView messageImage;

	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView contactsImage;

	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	private ImageView newsImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	private ImageView settingImage;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView messageText;

	/**
	 * 在Tab布局上显示联系人标题的控件
	 */
	private TextView contactsText;

	/**
	 * 在Tab布局上显示动态标题的控件
	 */
	private TextView newsText;

	/**
	 * 在Tab布局上显示设置标题的控件
	 */
	private TextView settingText;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*SharePreferenceUtil spu = new SharePreferenceUtil(this, "login");
		String userName = spu.loadStringSharedPreference("userName");
		String passWord = spu.loadStringSharedPreference("passWord");
		Toast.makeText(MainActivity.this, userName + "\n" + passWord, Toast.LENGTH_SHORT).show();*/
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		taskLayout = findViewById(R.id.task_layout);
		contactsLayout = findViewById(R.id.customer_layout);
		newsLayout = findViewById(R.id.company_layout);
		settingLayout = findViewById(R.id.setting_layout);
		messageImage = (ImageView) findViewById(R.id.task_image);
		contactsImage = (ImageView) findViewById(R.id.customer_image);
		newsImage = (ImageView) findViewById(R.id.company_image);
		settingImage = (ImageView) findViewById(R.id.setting_image);
		messageText = (TextView) findViewById(R.id.task_text);
		contactsText = (TextView) findViewById(R.id.customer_text);
		newsText = (TextView) findViewById(R.id.company_text);
		settingText = (TextView) findViewById(R.id.setting_text);
		
		taskLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.task_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.customer_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.company_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.setting_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.message_selected);
			messageText.setTextColor(Color.WHITE);
			if (taskManaFragment == null) {
				// 如果taskManaFragment为空，则创建一个并添加到界面上
				taskManaFragment = new TaskManaFragment();
				transaction.add(R.id.content, taskManaFragment);
			} else {
				// 如果taskManaFragment不为空，则直接将它显示出来
				taskManaFragment.onResume();
				transaction.show(taskManaFragment);
			}
			break;
			
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			contactsImage.setImageResource(R.drawable.contacts_selected);
			contactsText.setTextColor(Color.WHITE);
			if (customerInfoFragment == null) {
				// 如果customerInfoFragment为空，则创建一个并添加到界面上
				customerInfoFragment = new CustomerInfoFragment();
				transaction.add(R.id.content, customerInfoFragment);
				transaction.show(customerInfoFragment);
			} else {
				// 如果customerInfoFragment不为空，则直接将它显示出来
				transaction.show(customerInfoFragment);
			}
			
			break;
		case 2:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			newsImage.setImageResource(R.drawable.news_selected);
			newsText.setTextColor(Color.WHITE);
			if (complanyManaFragment == null) {
				// 如果complanyManaFragment为空，则创建一个并添加到界面上
				complanyManaFragment = new ComplanyManaFragment();
				transaction.add(R.id.content, complanyManaFragment);
			} else {
				// 如果complanyManaFragment不为空，则直接将它显示出来
				transaction.show(complanyManaFragment);
			}
			break;
		
		case 3:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			settingImage.setImageResource(R.drawable.setting_selected);
			settingText.setTextColor(Color.WHITE);
			if (settingFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				settingFragment = new SettingFragment();
				transaction.add(R.id.content, settingFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(settingFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		messageImage.setImageResource(R.drawable.message_unselected);
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.contacts_unselected);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		newsImage.setImageResource(R.drawable.news_unselected);
		newsText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.setting_unselected);
		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (customerInfoFragment != null) {
			transaction.hide(customerInfoFragment);
		}
		if (complanyManaFragment != null) {
			transaction.hide(complanyManaFragment);
		}
		if (taskManaFragment != null) {
			transaction.hide(taskManaFragment);
		}
		if (settingFragment != null) {
			transaction.hide(settingFragment);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
