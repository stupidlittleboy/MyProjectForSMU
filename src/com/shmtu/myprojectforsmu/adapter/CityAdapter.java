package com.shmtu.myprojectforsmu.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
public class CityAdapter<T> extends BaseAdapter implements Filterable
{
	private List<T> mObjects;//城市名称  汉字数组
	private List<T> mObjects2;//城市名称  拼音数组
	private final Object mLock = new Object();
	private int mResource;//展示数组适配器内容的View Id
	private int mDropDownResource;//下拉框中内容的Id
	private int mFieldId = 0;//下拉框选项ID
	private boolean mNotifyOnChange = true;
	private Context mContext;//当前上下文对象 - Activity
	private ArrayList<T> mOriginalValues;//原始数组列表
	private ArrayFilter mFilter;//
	private LayoutInflater mInflater;
	
	public CityAdapter(Context context, int textViewResourceId, T[] objects,T[] objects2) 
	{
		init(context, textViewResourceId, 0, Arrays.asList(objects),Arrays.asList(objects2));
	}
	public void add(T object) 
	{
		if (mOriginalValues != null) 
		{
			synchronized (mLock)
			{
				mOriginalValues.add(object);
				if (mNotifyOnChange) notifyDataSetChanged();
			}
		}
		else 
		{
			mObjects.add(object);
			if (mNotifyOnChange) notifyDataSetChanged();
		}
	}
	public void insert(T object, int index) 
	{
		if (mOriginalValues != null)
		{
			synchronized (mLock) 
			{
				mOriginalValues.add(index, object);
				if (mNotifyOnChange) notifyDataSetChanged();
			}
		}
		else
		{
			mObjects.add(index, object);
			if (mNotifyOnChange) notifyDataSetChanged();
		}
	}
	public void remove(T object)
	{
		if (mOriginalValues != null) 
		{
			synchronized (mLock) 
			{
				mOriginalValues.remove(object);
			}
		}
		else
		{
			mObjects.remove(object);
		}
		if (mNotifyOnChange) notifyDataSetChanged();
	}
	public void clear() //从列表中删除所有的信息
	{
		if (mOriginalValues != null) 
		{
			synchronized (mLock) 
			{
				mOriginalValues.clear();
			}
		} 
		else
		{
			mObjects.clear();
		}
		if (mNotifyOnChange) notifyDataSetChanged();
	}
	public void sort(Comparator<? super T> comparator)//根据指定的比较器对适配器中的内容进行排序
	{
		Collections.sort(mObjects, comparator);
		if (mNotifyOnChange) notifyDataSetChanged();        
	}
	@Override
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
		mNotifyOnChange = true;
	}
	//设置自动修改
	public void setNotifyOnChange(boolean notifyOnChange)
	{
		mNotifyOnChange = notifyOnChange;
	}
	//构造器  -- 初始化所有信息
	private void init(Context context, int resource, int textViewResourceId, List<T> objects ,List<T> objects2) 
	{
		mContext = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResource = mDropDownResource = resource;
		mObjects = objects;
		mObjects2 = objects2;
		mFieldId = textViewResourceId;
	}
	//返回数组适配器相关联的上下文对象
	public Context getContext()
	{
		return mContext;
	}
	public int getCount() //返回  城市名称汉字  列表的大小
	{
		return mObjects.size();
	}

	public T getItem(int position)//返回城市名称汉字列表中指定位置的字符串的值
	{
		return mObjects.get(position);
	}
	public int getPosition(T item)//返回 城市名称汉字 列表中指定的 字符串值 的索引
	{
		return mObjects.indexOf(item);
	}
	public long getItemId(int position)//将int型整数以长整型返回
	{
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent)//创建View
	{
		return createViewFromResource(position, convertView, parent, mResource);
	}
	private View createViewFromResource(int position, View convertView, ViewGroup parent,//创建View
			int resource)
	{
		View view;
		TextView text;
		if (convertView == null) //如果当前为空
		{
			view = mInflater.inflate(resource, parent, false);
		} 
		else //如果不为空
		{
			view = convertView;
		}

		try {
			if (mFieldId == 0) //如果当前域为空,假定所有的资源就是一个TextView
			{
				text = (TextView) view;
			}
			else//否则,在界面中找到TextView
			{
				text = (TextView) view.findViewById(mFieldId);
			}
		} 
		catch (ClassCastException e) //异常处理
		{
			throw new IllegalStateException
			(
					"ArrayAdapter requires the resource ID to be a TextView", e
					);
		}
		text.setText(getItem(position).toString());//为Text设值  -返回当前城市名称汉字列表中选中的值 
		return view;
	}
	public void setDropDownViewResource(int resource) //创建下拉视图
	{
		this.mDropDownResource = resource;
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) 
	{
		return createViewFromResource(position, convertView, parent, mDropDownResource);
	}
	public static ArrayAdapter<CharSequence> createFromResource(Context context,//从外部资源中创建新的数组适配器
			int textArrayResId, int textViewResId) 
			{
		CharSequence[] strings = context.getResources().getTextArray(textArrayResId);//创建字符创序列
		return new ArrayAdapter<CharSequence>(context, textViewResId, strings);//返回数组适配器
			}
	public Filter getFilter() //得到过滤器
	{
		if (mFilter == null)//如果为空,创建数组过滤器
		{
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}
	//数组过滤器限制数组适配器以指定的前缀开头,如果跟提供的前缀不匹配,则将其从中删除
	private class ArrayFilter extends Filter 
	{
		@Override
		protected FilterResults performFiltering(CharSequence prefix)//执行过滤
		{
			FilterResults results = new FilterResults();//创建FilterResults对象
			if (mOriginalValues == null) //如果为空
			{
				synchronized (mLock)
				{
					mOriginalValues = new ArrayList<T>(mObjects);
				}
			}
			if (prefix == null || prefix.length() == 0) 
			{
				synchronized (mLock) 
				{
					ArrayList<T> list = new ArrayList<T>(mOriginalValues);
					results.values = list;
					results.count = list.size();
				}
			} 
			else 
			{
				String prefixString = prefix.toString().toLowerCase();//转换成小写
				final ArrayList<T> values = mOriginalValues;
				final int count = values.size();
				final ArrayList<T> newValues = new ArrayList<T>(count);
				for (int i = 0; i < count; i++)
				{
					final T value = values.get(i);
					final String valueText = value.toString().toLowerCase();

					final T value2 = mObjects2.get(i);
					final String valueText2 = value2.toString().toLowerCase();

					//查找拼音 
					if(valueText2.startsWith(prefixString))
					{
						newValues.add(value);
					}//查找汉字       
					else if(valueText.startsWith(prefixString))
					{
						newValues.add(value);
					}
					else
					{      //添加汉字关联
						final String[] words = valueText.split(" ");
						final int wordCount = words.length;
						for (int k = 0; k < wordCount; k++) 
						{
							if (words[k].startsWith(prefixString)) 
							{
								newValues.add(value);
								break;
							}
						}
						//添加拼音关联汉字
						final String[] words2 = valueText2.split(" ");
						final int wordCount2 = words2.length;

						for (int k = 0; k < wordCount2; k++) 
						{
							if (words2[k].startsWith(prefixString))
							{
								newValues.add(value);
								break;
							}
						}  
					}
				}
				results.values = newValues;
				results.count = newValues.size();
			}
			return results;
		}
		@SuppressWarnings("unchecked")
		protected void publishResults(CharSequence constraint, FilterResults results) 
		{    
			mObjects = (List<T>) results.values;
			if (results.count > 0)
			{
				notifyDataSetChanged();
			} else
			{
				notifyDataSetInvalidated();
			}
		}
	}
}