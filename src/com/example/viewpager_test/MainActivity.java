package com.example.viewpager_test;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private ViewPager mPager;//ҳ������
	private List<View> listViews; // Tabҳ���б�
	private ImageView cursor;// ����ͼƬ
	private TextView t1, t2, t3;// ҳ��ͷ��
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		
		InitViewPager();
		InitTextView();
		InitImageView();
		
		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}
	
	 private void InitTextView() {
         t1 = (TextView) findViewById(R.id.text1);
         t2 = (TextView) findViewById(R.id.text2);
         t3 = (TextView) findViewById(R.id.text3);
 
         t1.setOnClickListener(new MyOnClickListener(0));
         t2.setOnClickListener(new MyOnClickListener(1));
         t3.setOnClickListener(new MyOnClickListener(2));
     }
	 
	 private void InitImageView() {
         cursor = (ImageView) findViewById(R.id.cursor);
         bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.imageviewbg)
                 .getWidth();// ��ȡͼƬ���
         DisplayMetrics dm = new DisplayMetrics();
         getWindowManager().getDefaultDisplay().getMetrics(dm);
         int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
         offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
         Matrix matrix = new Matrix();
         matrix.postTranslate(offset, 0);
         cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
         
     }
	 
	 private void InitViewPager() {
         mPager = (ViewPager) findViewById(R.id.vPager);
         listViews = new ArrayList<View>();
         LayoutInflater mInflater = getLayoutInflater();
         listViews.add(mInflater.inflate(R.layout.tab_test, null));
         listViews.add(mInflater.inflate(R.layout.tab_test2, null));
         listViews.add(mInflater.inflate(R.layout.tab_test3, null));
         mPager.setAdapter(new MyPagerAdapter(listViews));
         mPager.setCurrentItem(0);
         mPager.setOnPageChangeListener(new MyOnPageChangeListener());
     }
	 
	 public class MyOnClickListener implements View.OnClickListener {
         private int index = 0;
 
         public MyOnClickListener(int i) {
             index = i;
         }
 
         @Override
         public void onClick(View v) {
             mPager.setCurrentItem(index);
         }
     };

     public class MyOnPageChangeListener implements OnPageChangeListener {
 
         int one;// ҳ��1 -> ҳ��2 ƫ����
         int two;// ҳ��1 -> ҳ��3 ƫ����
         
         @Override
         public void onPageSelected(int arg0) {
             Animation animation = null;
             one = offset * 2 + bmpW;
             two = one * 2;
             switch (arg0) {
             case 0:
                 if (currIndex == 1) {
                     animation = new TranslateAnimation(one, 0, 0, 0);
                 } else if (currIndex == 2) {
                     animation = new TranslateAnimation(two, 0, 0, 0);
                 }
                 break;
             case 1:
                 if (currIndex == 0) {
                     animation = new TranslateAnimation(offset, one, 0, 0);
                 } else if (currIndex == 2) {
                     animation = new TranslateAnimation(two, one, 0, 0);
                 }
                 break;
             case 2:
                 if (currIndex == 0) {
                     animation = new TranslateAnimation(offset, two, 0, 0);
                 } else if (currIndex == 1) {
                     animation = new TranslateAnimation(one, two, 0, 0);
                 }
                 break;
             }
             currIndex = arg0;
             animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
             animation.setDuration(300);
             cursor.startAnimation(animation);
           
         }
 
         @Override
         public void onPageScrolled(int arg0, float arg1, int arg2) {
         }
 
         @Override
         public void onPageScrollStateChanged(int arg0) {
         }
     }
     
     public class MyPagerAdapter extends PagerAdapter {
         public List<View> mListViews;
 
         public MyPagerAdapter(List<View> mListViews) {
             this.mListViews = mListViews;
         }
 
         @Override
         public void destroyItem(View arg0, int arg1, Object arg2) {
             ((ViewPager) arg0).removeView(mListViews.get(arg1));
         }
 
         @Override
         public void finishUpdate(View arg0) {
         }
 
         @Override
         public int getCount() {
             return mListViews.size();
         }
 
         @Override
         public Object instantiateItem(View arg0, int arg1) {
             ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
             return mListViews.get(arg1);
         }
 
         @Override
         public boolean isViewFromObject(View arg0, Object arg1) {
             return arg0 == (arg1);
         }
 
         @Override
         public void restoreState(Parcelable arg0, ClassLoader arg1) {
         }
 
         @Override
         public Parcelable saveState() {
             return null;
         }
 
         @Override
         public void startUpdate(View arg0) {
         }
     }
     
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
