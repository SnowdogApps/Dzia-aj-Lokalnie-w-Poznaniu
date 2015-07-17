package pl.snowdog.dzialajlokalnie.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.util.FileChooserUtil;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_issue_image)
public class AddIssueImageFragment extends AddIssueBaseFragment {
    public static final int PICK_FROM_FILE = 1231;
    public static final int TAKE_PICTURE = 1234;

    private String mTmpGalleryPicturePath;

    @ViewById(R.id.ivPreview)
    ImageView ivPreview;

    @ViewById(R.id.fab)
    FloatingActionsMenu fab;

    private Uri mFileUri;

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            CreateNewObjectEvent.Builder builder = new CreateNewObjectEvent.Builder()
                    .image("abc")
                    .type(CreateNewObjectEvent.Type.image);

            EventBus.getDefault().post(builder.build());
            //((AddBaseActivity)getActivity()).goToNextPage();
        }
    }

    @Click(R.id.fab_new_photo)
    void onTakePhotoClicked() {
        fab.collapse();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        //File image = new File(Environment.getExternalStorageDirectory(),  AppHelpers.getRandomBigInt(999999) + ".jpg");
        File photo = new File(Environment.getExternalStorageDirectory(),  fname);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        mFileUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Click(R.id.fab_gallery)
    void onGalleryButtonClicked() {
        fab.collapse();
        startActivityForResult(
                Intent.createChooser(
                        new Intent(Intent.ACTION_GET_CONTENT)
                                .setType("image/*"), "Choose an image"),
                PICK_FROM_FILE);

    }

    @OnActivityResult(TAKE_PICTURE)
    void handleTakePictureResult(int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }
        Picasso.with(getActivity()).load(mFileUri).error(
                R.drawable.ic_editor_insert_emoticon).into(ivPreview);
        //handleGalleryResult(data);
    }

    @OnActivityResult(PICK_FROM_FILE)
    void handleGalleryResult(int resultCode, Intent data)
    {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }
        Uri selectedImage = data.getData();
        mTmpGalleryPicturePath = FileChooserUtil.getPath(getActivity(), selectedImage);
        if(mTmpGalleryPicturePath!=null)
            Picasso.with(getActivity()).load(selectedImage).error(
                    R.drawable.ic_editor_insert_emoticon).into(ivPreview);
        else
        {
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(selectedImage);
                ivPreview.setImageBitmap(BitmapFactory.decodeStream(is));
                mTmpGalleryPicturePath = selectedImage.getPath();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    boolean validateInput() {
        return true;
    }

    @AfterViews
    void afterViews() {

    }
}
