package com.cloudstime.myviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class MyViewPager extends LinearLayout{
    int imgId[] ;    //={R.drawable.viewpager_img01,R.drawable.viewpager_img02,R.drawable.viewpager_img03};
    int dotId[]={R.id.dot1,R.id.dot2,R.id.dot3};
    long start;
    Context context;
    ViewPager viewPager;
    List<View> viewList;
    ImageView dot[]=new ImageView[3];
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            handler.sendEmptyMessageDelayed(0, 5000);

        }
    };
    private OnMyViewPagerListenner myViewpagerListenner=null;

    public MyViewPager(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        imgId=new int[3] ;
        TypedArray typeArry=context.obtainStyledAttributes(attrs,R.styleable.MyViewPager);
        imgId[0]=typeArry.getResourceId(R.styleable.MyViewPager_src_img1, 1);
        imgId[1]=typeArry.getResourceId(R.styleable.MyViewPager_src_img2, 1);
        imgId[2]=typeArry.getResourceId(R.styleable.MyViewPager_src_img3, 1);
        initView();
}

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.myviewpager,this);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        dot[0]=(ImageView)findViewById(R.id.dot1);
        dot[1]=(ImageView)findViewById(R.id.dot2);
        dot[2]=(ImageView)findViewById(R.id.dot3);
        initImg();
        //定时，每隔3秒发送一次消息
     /*   Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },2000);*/

        Log.v("Listview", String.valueOf(viewList.size()));
        viewPager.setAdapter(new ViewPagerAdapter(viewList, context));
        viewPager.setCurrentItem(viewList.size() * 300);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int k = i % viewList.size();
                for (int j = 0; j < imgId.length; j++) {
                    if (k == j) {
                        dot[j].setBackgroundResource(R.drawable.dot_focus);
                    }else {
                        dot[j].setBackgroundResource(R.drawable.dot_unfocus);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        handler.sendEmptyMessageDelayed(0, 5000);
    }

    private void initImg() {
        viewList = new ArrayList<View>();
        for (int i=0;i<imgId.length;i++){
            dot[i]=(ImageView)findViewById(dotId[i]);   //??????????????Id

            ImageView imageView=new ImageView(context);    //?è??ViewPager±??°????
            imageView.setBackgroundResource(imgId[i]);
            viewList.add(imageView);
        }
    }
    public class ViewPagerAdapter extends PagerAdapter {
        List<View> viewList;
        Context context;
        ViewGroup parent;

        public ViewPagerAdapter(List<View> viewList, Context context) {
            this.viewList = viewList;
            this.context = context;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final int index=position%viewList.size();
            parent = (ViewGroup) viewList.get(index).getParent();
            if (parent != null) {
                parent.removeView(viewList.get(index));
            }
            container.addView(viewList.get(index));
             ImageView imageView = (ImageView) viewList.get(index);
          /*  imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   switch (index){
                       case 0:
                           Log.v("index",index+"");
                           if (myViewpagerListenner!=null) {
                               myViewpagerListenner.onImgClick01();
                           }
                           break;
                       case 1:
                           Log.v("index",index+"");

                           if (myViewpagerListenner!=null) {
                               myViewpagerListenner.onImgClick02();
                           }
                           break;
                       case 2:
                           Log.v("index",index+"");

                           if (myViewpagerListenner!=null) {
                               myViewpagerListenner.onImgClick03();
                           }
                           break;
                       default:
                           break;
                   }
                }
            });*/

            imageView.setOnTouchListener(new View.OnTouchListener() {
                float firstX,lastX;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            firstX = event.getX();
                            start=System.currentTimeMillis();
                            handler.removeMessages(0);
                            Log.v("action_down", "DOWN");
                            break;
                        case MotionEvent.ACTION_UP:
                            lastX = event.getX();
                            long internal = System.currentTimeMillis() - start;
                            if (internal<500) {
                                switch (index){
                                    case 0:
                                        Log.v("index",index+"");
                                        if (myViewpagerListenner!=null) {
                                            myViewpagerListenner.onImgClick01();
                                        }
                                        break;
                                    case 1:
                                        Log.v("index",index+"");

                                        if (myViewpagerListenner!=null) {
                                            myViewpagerListenner.onImgClick02();
                                        }
                                        break;
                                    case 2:
                                        Log.v("index",index+"");

                                        if (myViewpagerListenner!=null) {
                                            myViewpagerListenner.onImgClick03();
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                Toast.makeText(context,"action_up excected!", Toast.LENGTH_SHORT).show();
                            }else {
                                handler.removeMessages(0);
                                handler.sendEmptyMessageDelayed(0,5000);
                            }
                            Log.v("internal ",internal+"");
                            break;
                        case MotionEvent.ACTION_MOVE:

                            if (Math.abs(lastX-firstX)>viewPager.getWidth()/9){
                                handler.removeMessages(0);
                                handler.sendEmptyMessageDelayed(0, 5000);
                            }
                            break;
                        default:
                            handler.removeMessages(0);
                            handler.sendEmptyMessageDelayed(0,5000);
                            break;
                    }
                    return true;
                }
            });
            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o;
        }
    }

    public void setOnMyViewPagerListenner(OnMyViewPagerListenner mOnMyViewPagerListenner){
        myViewpagerListenner=mOnMyViewPagerListenner;
    }
    interface OnMyViewPagerListenner{
        void onImgClick01();
        void onImgClick02();
        void onImgClick03();
    }
}
