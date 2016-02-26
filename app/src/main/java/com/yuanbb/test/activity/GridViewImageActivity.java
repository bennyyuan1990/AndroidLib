package com.yuanbb.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BitmapCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.yuanbb.common.adapter.MyBaseAdapter;
import com.yuanbb.common.util.WindowUtil;
import com.yuanbb.test.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewImageActivity extends AppCompatActivity {


    private MyBaseAdapter<ImageItem> mBaseAdapter;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private int mImageWidth = 200;
    private GridView mImagesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_image);
        mImagesGridView = (GridView) findViewById(R.id.gv_Images);
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());


        List<ImageItem> images = new ArrayList<>();
        images.add(new ImageItem("", "http://img0.imgtn.bdimg.com/it/u=2036929782,145787142&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img5.imgtn.bdimg.com/it/u=3844523721,2997701838&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img2.imgtn.bdimg.com/it/u=870003458,2254865796&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img5.imgtn.bdimg.com/it/u=1016634327,487630916&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img1.imgtn.bdimg.com/it/u=844249374,3055622731&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img5.imgtn.bdimg.com/it/u=1041224328,287994887&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img3.imgtn.bdimg.com/it/u=650689986,3198793017&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img5.imgtn.bdimg.com/it/u=1681227401,3595009042&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img3.imgtn.bdimg.com/it/u=1694685162,3193449523&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img4.imgtn.bdimg.com/it/u=767878911,4001085035&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img1.imgtn.bdimg.com/it/u=836469338,1677054953&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img0.imgtn.bdimg.com/it/u=3809090361,444531651&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img1.imgtn.bdimg.com/it/u=3378186213,3717695534&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img5.imgtn.bdimg.com/it/u=1504374515,2611400940&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img3.imgtn.bdimg.com/it/u=4138480875,3271224934&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://img5.imgtn.bdimg.com/it/u=3302980088,4157165137&fm=23&gp=0.jpg"));
        images.add(new ImageItem("", "http://e.hiphotos.baidu.com/image/h%3D360/sign=85c7a9d07fec54e75eec1c18893a9bfd/314e251f95cad1c8037ed8c97b3e6709c83d5112.jpg"));
        images.add(new ImageItem("", "http://c.hiphotos.baidu.com/image/h%3D360/sign=3a6367dc229759ee555066cd82fa434e/0dd7912397dda1449fad6f63b6b7d0a20df486be.jpg"));
        images.add(new ImageItem("", "http://d.hiphotos.baidu.com/image/h%3D360/sign=e0a211de5eafa40f23c6c8db9b65038c/562c11dfa9ec8a13f075f10cf303918fa1ecc0eb.jpg"));
        images.add(new ImageItem("", "http://a.hiphotos.baidu.com/image/h%3D360/sign=4f6888e673c6a7efa626ae20cdfaafe9/f9dcd100baa1cd11daf25f19bc12c8fcc3ce2d46.jpg"));
        images.add(new ImageItem("", "http://e.hiphotos.baidu.com/image/h%3D360/sign=ea96ce4c0e7b020813c939e752d8f25f/14ce36d3d539b600be63e95eed50352ac75cb7ae.jpg"));
        images.add(new ImageItem("", "http://d.hiphotos.baidu.com/image/h%3D360/sign=bb25ef64bf315c605c956de9bdb0cbe6/a5c27d1ed21b0ef4400edb2fdec451da80cb3ed8.jpg"));
        images.add(new ImageItem("", "http://a.hiphotos.baidu.com/image/h%3D360/sign=01bfc8e6f303738dc14a0a24831ab073/08f790529822720eb44d55bc7ecb0a46f31fabf1.jpg"));
        images.add(new ImageItem("", "http://e.hiphotos.baidu.com/image/h%3D360/sign=634e643dcafcc3ceabc0cf35a244d6b7/cefc1e178a82b9016ece2b2c718da9773912ef4a.jpg"));
        images.add(new ImageItem("", "http://d.hiphotos.baidu.com/image/h%3D360/sign=bb47be042ff5e0fef1188f076c6034e5/d788d43f8794a4c2364656360cf41bd5ad6e3941.jpg"));
        images.add(new ImageItem("", "http://e.hiphotos.baidu.com/image/h%3D360/sign=3cc4dee167380cd7f91ea4eb9145ad14/ca1349540923dd54daf48639d309b3de9d8248df.jpg"));
        images.add(new ImageItem("", "http://c.hiphotos.baidu.com/image/h%3D360/sign=92e85d37347adab422d01d45bbd5b36b/f31fbe096b63f6240191d71c8544ebf81b4ca3e5.jpg"));
        images.add(new ImageItem("", "http://a.hiphotos.baidu.com/image/h%3D360/sign=b3acbae333d12f2ed105a8667fc2d5ff/94cad1c8a786c917b77e890dcc3d70cf3bc75781.jpg"));
        images.add(new ImageItem("", "http://a.hiphotos.baidu.com/image/h%3D360/sign=d965e890d443ad4bb92e40c6b2025a89/03087bf40ad162d9f285772914dfa9ec8a13cd9f.jpg"));
        images.add(new ImageItem("", "http://b.hiphotos.baidu.com/image/h%3D360/sign=4966caee48086e0675a8394d320a7b5a/023b5bb5c9ea15cec72cb6d6b2003af33b87b22b.jpg"));
        images.add(new ImageItem("", "http://e.hiphotos.baidu.com/image/h%3D360/sign=af15bbb1ccea15ce5eeee60f86003a25/9c16fdfaaf51f3de18a16c5091eef01f3a2979f7.jpg"));
        images.add(new ImageItem("", "http://b.hiphotos.baidu.com/image/h%3D360/sign=c71a230b8594a4c21523e12d3ef51bac/a8773912b31bb051e98b712c347adab44aede003.jpg"));
        images.add(new ImageItem("", "http://e.hiphotos.baidu.com/image/h%3D360/sign=ec1c315097cad1c8cfbbfa214f3f67c4/83025aafa40f4bfb27bfbf2b014f78f0f7361865.jpg"));
        images.add(new ImageItem("", "http://b.hiphotos.baidu.com/image/h%3D360/sign=0c8b434a00087bf462ec51efc2d3575e/37d3d539b6003af32b99a061372ac65c1038b68b.jpg"));
        images.add(new ImageItem("", "http://c.hiphotos.baidu.com/image/h%3D360/sign=6f2b2fb14510b912a0c1f0f8f3fdfcb5/42a98226cffc1e1728b8e43b4890f603738de9b1.jpg"));
        images.add(new ImageItem("", "http://a.hiphotos.baidu.com/image/h%3D360/sign=2ecf8457f4246b60640eb472dbf91a35/b90e7bec54e736d1bfe8560c99504fc2d562693f.jpg"));
        images.add(new ImageItem("", "http://g.hiphotos.baidu.com/image/h%3D360/sign=afa7d2e49e82d158a4825fb7b00b19d5/0824ab18972bd4074b5487f779899e510fb3092c.jpg"));
        images.add(new ImageItem("", "http://d.hiphotos.baidu.com/image/h%3D360/sign=e48311768882b90122adc535438ca97e/4e4a20a4462309f7bb2d9743700e0cf3d7cad624.jpg"));
        images.add(new ImageItem("", "http://a.hiphotos.baidu.com/image/h%3D360/sign=91c865b6ab014c08063b2ea33a7a025b/359b033b5bb5c9ea6d22ebfcd739b6003bf3b3d8.jpg"));

        mImageWidth = WindowUtil.getWindowWidth(this) / 3;


        mBaseAdapter = new MyBaseAdapter<ImageItem>(this, images, R.layout.item_image) {
            @Override
            public void setViewValue(ImageItem obj, ViewHodler viewHodler) {
                ImageLoader.ImageListener listener = ImageLoader.getImageListener((ImageView) viewHodler.getView(R.id.iv_image), R.mipmap.default_image, R.mipmap.error_image);//参数分别为要显示的图片控件，默认显示的图片（用于图片未下载完时显示），下载图片失败时显示的图片
                mImageLoader.get(obj.mPath, listener, mImageWidth, mImageWidth);//开始请求网络图片
            }
        };


        mImagesGridView.setAdapter(mBaseAdapter);

    }


    private class ImageItem {
        public ImageItem() {
        }

        public ImageItem(String title, String path) {
            mTitle = title;
            mPath = path;
        }

        private String mTitle;
        private String mPath;
    }


}
