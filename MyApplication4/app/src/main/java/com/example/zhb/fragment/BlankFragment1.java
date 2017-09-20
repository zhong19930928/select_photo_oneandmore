package com.example.zhb.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhb.MainActivity;
import com.example.zhb.SelectImageView;
import com.example.zhb.myapplication.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;

import java.util.List;

import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/9/4 0004.
 * 学习网址：http://p.codekk.com/detail/Android/LuckSiege/PictureSelector
 */

public class BlankFragment1 extends Fragment implements View.OnClickListener{
    private CircleImageView photoUser;
    private MainActivity mianatcivity;
    private Unbinder unbinder;
    private BlankFragment1 blankFragment1;
    private List<LocalMedia> selectList;
    private Button goto_selectImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank4, null);
        photoUser = (CircleImageView) view.findViewById(R.id.photo_user);
        goto_selectImage = view.findViewById(R.id.goto_selectImage);
        goto_selectImage.setOnClickListener(this);
        Glide.with(getActivity()).load("http://pic39.nipic.com/20140226/18071023_164300608000_2.jpg")
                .into(photoUser);
        photoUser.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.photo_user)
    public void onViewClicked() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_user:
                RxPermissions permissions;
                permissions = new RxPermissions(getActivity());
                permissions.request(Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean){
                            PictureSelector.create(getActivity())
                                    .openGallery(PictureMimeType.ofImage())
                                    .maxSelectNum(1)
                                    .enableCrop(true)
                                    .compress(true)
                                    .withAspectRatio(1,1)
                                    .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)
                                    .circleDimmedLayer(true)
                                    .showCropFrame(false)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        }else{
                            Toast.makeText(getActivity(),"读取内存卡权限被拒绝",Toast.LENGTH_LONG).show();
                        }
                    }
//                包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统 sd 卡权限
//                PictureFileUtils.deleteCacheDirFile(MainActivity.this);
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;
            case R.id.goto_selectImage:
                Intent intent = new Intent(getActivity(), SelectImageView.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //到相册页面选取图片之后的回掉
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种 path
                    // 1.media.getPath(); 为原图 path
                    // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
                    // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //adapter.setList(selectList);
                    //adapter.notifyDataSetChanged();
                    //DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    selectList.get(0).getCompressPath();
                    Glide.with(getActivity()).load(selectList.get(0).getCompressPath())
                            .into(photoUser);
//                    //本地文件
//                    File file = new File(Environment.getExternalStorageDirectory(), selectList.get(0).getCompressPath());
//                    //加载图片
//                    Glide.with(this).load(file).into(photoUser);
                    break;
            }
        }
    }


    /**
     *@date2017/9/13
     *@author zhonghuibin
     *@description 通过这个方法为父Activity赋handler，通过这个handler传值完成Activity和fragment之间的交互
     *             首先在activity里面得有下面setHandler这个方法
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mianatcivity = (MainActivity) context;
        mianatcivity.setHandler(mHandler);
    }

    public Handler mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    selectList = PictureSelector.obtainMultipleResult((Intent) msg.obj);
                    // 例如 LocalMedia 里面返回三种 path
                    // 1.media.getPath(); 为原图 path
                    // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
                    // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    //adapter.setList(selectList);
                    //adapter.notifyDataSetChanged();
                    //DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    selectList.get(0).getCompressPath();
//                    Log.i("zhong",selectList.get(0).getCompressPath());
//                    File file = new File(selectList.get(0).getCompressPath());
                    Glide.with(getActivity()).load(selectList.get(0).getCompressPath())
                            .into(photoUser);
//                    //本地文件
//                    File file = new File(Environment.getExternalStorageDirectory(), selectList.get(0).getCompressPath());
//                    //加载图片
//                    Glide.with(this).load(file).into(photoUser);
                    Glide.with(getActivity()).load(selectList.get(0).getCompressPath())
                            .into(photoUser);
                    break;
            }
        };
    };
}
