package ink.alf.village.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ink.alf.village.R;
import ink.alf.village.base.BaseActivity;
import ink.alf.village.base.MyGlideEngine;
import ink.alf.village.bean.ActivitiBean;
import ink.alf.village.common.CatagoryType;
import ink.alf.village.common.GifSizeFilter;
import ink.alf.village.presenter.PublishPresenter;
import ink.alf.village.ui.ImageAddAdapter;
import ink.alf.village.utils.DialogUtils;
import ink.alf.village.utils.FileUtils;
import ink.alf.village.utils.SharedPreferencesHelper;
import ink.alf.village.utils.ToastUtils;
import ink.alf.village.view.IPublishView;
import ink.alf.village.widget.ActionSheet;
import ink.alf.village.widget.GridSpacingItemDecoration;

/**
 * @author 13793
 */
public class PublishActivity extends BaseActivity implements IPublishView {


    private static final String TAG = "PublishActivity";

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.et_publish_content)
    EditText etPublishContent;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_choose_label_layout)
    RelativeLayout rlChooseLabelLayout;
    @BindView(R.id.tv_label)
    TextView tvLabel;

    private static final int MAX_LENGTH = 10;
    private static final int REQUEST_CODE_CHOOSE = 1000;

    private ImageAddAdapter imageAddAdapter;
    private PublishPresenter publishPresenter;

    private ActivitiBean pushActiviti = new ActivitiBean();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        publishPresenter = new PublishPresenter(this, this);
        tvPublish.setOnClickListener(v -> pushActiviti());
        tvCancel.setOnClickListener(v -> this.finish());
        rlChooseLabelLayout.setOnClickListener(v -> chooseCatagory());
        etPublishContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int length = s.length();
                if (length >= MAX_LENGTH) {
                    tvPublish.setClickable(true);
                    tvPublish.setTextColor(getResources().getColor(R.color.colorDarkBlack));
                } else {
                    tvPublish.setClickable(false);
                    tvPublish.setTextColor(getResources().getColor(R.color.colorGray));
                }
            }
        });
        //adapter
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
        imageAddAdapter = new ImageAddAdapter(this);
        recyclerView.setAdapter(imageAddAdapter);
        imageAddAdapter.setOnItemClickListener(new ImageAddAdapter.OnItemClickListener() {
            @Override
            public void choosePicture() {

                int length = imageAddAdapter.getImageLists().size();
                if (length < 9) {
                    Matisse.from(PublishActivity.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .capture(true)
                            .captureStrategy(new CaptureStrategy(true, "ink.alf.village.fileprovider"))
                            .maxSelectable(9)
                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .theme(R.style.Matisse_Zhihu)
                            .imageEngine(new MyGlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                } else {
                    ToastUtils.showToast(PublishActivity.this, "最多选择9张图片");
                }

            }

            @Override
            public void zoomOut(Uri imageLocalUri) {

            }

            @Override
            public void deletePickture(int position) {
                imageAddAdapter.deleteData(position);
            }
        });
    }


    /**
     * 发布活动
     */
    private void pushActiviti() {

        String content = etPublishContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast(this, "请输入发布的内容");
            return;
        }
        pushActiviti.setContent(content);
        if (StringUtils.isEmpty(pushActiviti.getCatagory())) {
            ToastUtils.showToast(this, "请选择标签");
            return;
        }

        SharedPreferencesHelper preferencesHelper = new SharedPreferencesHelper(this);
        String address = (String) preferencesHelper.getValueForKey("address", "西安");
        pushActiviti.setAddress(address);
        List<Uri> uris = imageAddAdapter.getImageLists();
        //开始请求,创建activiti
        DialogUtils.getInstance(this).show();
        if (uris.size() == 0) {
            publishPresenter.createActiviti(getToken(), pushActiviti);
        } else {
            List<String> absimgPath = new ArrayList<>();
            for (int i = 0; i < uris.size(); i++) {
                absimgPath.add(FileUtils.getRealFilePath(this, uris.get(i)));
            }
            publishPresenter.uploadImage(absimgPath);
        }
    }


    private ActionSheet actionSheet;

    /**
     * 选择便签分类
     */
    private void chooseCatagory() {
        actionSheet = new ActionSheet.DialogBuilder(this)
                .addSheet("找人", v -> {
                    tvLabel.setText("找人");
                    pushActiviti.setCatagory("找人");
                    pushActiviti.setSalt(CatagoryType.PERSON.getCatagory());
                    actionSheet.dismiss();

                })
                .addSheet("找车", v -> {
                    tvLabel.setText("找车");
                    pushActiviti.setCatagory("找车");
                    pushActiviti.setSalt(CatagoryType.VEHICLE.getCatagory());
                    actionSheet.dismiss();
                })
                .addSheet("问事", v -> {
                    tvLabel.setText("问事");
                    pushActiviti.setCatagory("问事");
                    pushActiviti.setSalt(CatagoryType.THING.getCatagory());
                    actionSheet.dismiss();
                })
                .addCancelListener(v -> actionSheet.dismiss())
                .create();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK && null != data) {
            data.getExtras();
            List<Uri> mSelected = Matisse.obtainResult(data);
            Log.d("Matisse", "mSelected: " + mSelected);
            imageAddAdapter.addDatas(mSelected);

        }
    }

    @Override
    public void uploadImageSuccess(String imagesPath) {
        Log.d(TAG, "uploadImageSuccess: " + imagesPath);
        pushActiviti.setContentImages(imagesPath);
        publishPresenter.createActiviti(getToken(), pushActiviti);
    }

    @Override
    public void uploadImageFailure(String message, int errorCode) {
        DialogUtils.getInstance(this).dismiss();
        ToastUtils.showToast(this, message);
    }

    @Override
    public void insertActivitiSuccess() {
        DialogUtils.getInstance(this).dismiss();
        ToastUtils.showToast(this, "发布成功");
        this.finish();
    }

    @Override
    public void insertActivitiFailure(String message, int code) {
        DialogUtils.getInstance(this).dismiss();
        ToastUtils.showToast(this, message);
    }
}
