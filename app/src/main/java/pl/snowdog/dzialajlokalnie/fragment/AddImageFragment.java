package pl.snowdog.dzialajlokalnie.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.R;
import pl.snowdog.dzialajlokalnie.api.DlApi;
import pl.snowdog.dzialajlokalnie.events.CreateNewObjectEvent;
import pl.snowdog.dzialajlokalnie.util.FileChooserUtil;

/**
 * Created by chomi3 on 2015-07-06.
 */
@EFragment(R.layout.fragment_add_image)
public class AddImageFragment extends AddBaseFragment {
    public static final int PICK_FROM_FILE = 1231;
    public static final int TAKE_PICTURE = 1234;
    private static final String TAG = "AddIssueImageFr";

    private String mTmpGalleryPicturePath;

    @ViewById(R.id.ivPreview)
    ImageView ivPreview;

    @ViewById(R.id.fab)
    FloatingActionsMenu fab;

    private Uri mFileUri;

    @FragmentArg
    CreateNewObjectEvent mEditedObject;

    @Click(R.id.btnNext)
    void onNextButtonClicked() {
        if(validateInput()) {
            CreateNewObjectEvent.Builder builder = new CreateNewObjectEvent.Builder()
                    .image(mFileUri == null ? "" : mFileUri.getPath())
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
                                .setType("image/*"), getString(R.string.choose_image_info)),
                PICK_FROM_FILE);

    }

    @OnActivityResult(TAKE_PICTURE)
    void handleTakePictureResult(int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        /* Set bitmap options to scale the image decode target */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = 2;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath(), bmOptions);

        /* Test compress */
        File imageFile = new File(mFileUri.getPath());
        try{
            OutputStream out = null;
            out = new FileOutputStream(imageFile);
            //Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            bitmap.compress(Bitmap.CompressFormat.JPEG,80,out);
            out.flush();
            out.close();
            Log.d(TAG, "imgdbg COMPRESSED FILE");
        }catch(Exception e){
            Log.e(TAG,"imgdbg Error compressing: "+e.toString());
        }

        Picasso.with(getActivity()).load(mFileUri).error(
                R.drawable.ic_editor_insert_emoticon).into(ivPreview);
        btnNext.setText(R.string.next);
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
        if(mTmpGalleryPicturePath!=null) {
            mFileUri = selectedImage;
            Picasso.with(getActivity()).load(selectedImage).error(
                    R.drawable.ic_editor_insert_emoticon).into(ivPreview);

        } else
        {
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(selectedImage);
                ivPreview.setImageBitmap(BitmapFactory.decodeStream(is));
                mTmpGalleryPicturePath = selectedImage.getPath();
                mFileUri = selectedImage;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        btnNext.setText(R.string.next);
    }

    @Override
    boolean validateInput() {
        return true;
    }

    @AfterViews
    void afterViews() {
        if(mFileUri == null) {
            btnNext.setText(R.string.skip);
        }
        //EDIT MODE
        if(mEditedObject != null) {
            Picasso.with(getActivity())
                    .load(String.format(DlApi.PHOTO_THUMB_URL, mEditedObject.getImage()))
                    .error(
                    R.drawable.ic_editor_insert_emoticon).into(ivPreview);
            Log.d(TAG, "edtdbg image: "+mEditedObject.getImage());
        }
    }
}
