package com.benny.libapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.benny.baselib.view.recyclerview.BaseAdapter;
import com.benny.baselib.view.recyclerview.CardSlideListView;
import java.util.ArrayList;
import java.util.List;

public class CardSlideListViewActivity extends AppCompatActivity {


    private CardSlideListView mCardSlideListView1;
    private CardSlideListView mCardSlideListView2;
    private CardSlideListView mCardSlideListView3;
    private CardSlideListView mCardSlideListView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_slide_list_view);
        mCardSlideListView1 = (CardSlideListView) findViewById(R.id.activity_card_slide_list1);
        mCardSlideListView2 = (CardSlideListView) findViewById(R.id.activity_card_slide_list2);
        mCardSlideListView3 = (CardSlideListView) findViewById(R.id.activity_card_slide_list3);
        mCardSlideListView4 = (CardSlideListView) findViewById(R.id.activity_card_slide_list4);

        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add("Item Data " + i);
        }
        mCardSlideListView1.getListView().setAdapter(new MyAdapter1(list,"标题1  "));
        mCardSlideListView2.getListView().setAdapter(new MyAdapter1(list,"标题2  "));
        mCardSlideListView3.getListView().setAdapter(new MyAdapter1(list,"标题3  "));
        mCardSlideListView4.getListView().setAdapter(new MyAdapter1(list,"标题4  "));

    }

    static class MyAdapter1 extends RecyclerView.Adapter<MyViewHolder> {

        private List<String> mData;
        private String mTag;

        public MyAdapter1(List<String> data,String tag) {
            mData = data;
            mTag = tag;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);

            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String s = mData.get(position);
            holder.mTextView.setText(mTag + s);
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }


    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    static class MyAdapter extends BaseAdapter<String> {

        public MyAdapter(Context context, List<String> data) {
            this(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null), data);
        }

        public MyAdapter(View itemView, List<String> data) {
            super(itemView, data);
        }


        @Override
        public void onBindViewData(BaseViewHolder holder, String data) {
            holder.setViewText(android.R.id.text1, data);
        }


    }
}
