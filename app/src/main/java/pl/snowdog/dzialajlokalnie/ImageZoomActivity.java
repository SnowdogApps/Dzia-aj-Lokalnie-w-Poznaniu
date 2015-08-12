package pl.snowdog.dzialajlokalnie;

import android.graphics.Bitmap;
import android.view.View;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by chomi3 on 2015-08-12.
 */
@EActivity(R.layout.activity_image_zoom)
public class ImageZoomActivity extends BaseActivity {
    @ViewById(R.id.image)
    ImageViewTouch imageViewTouch;

    @Extra
    Bitmap imageBitmap;

    @Override
    protected void afterView() {
        imageViewTouch.setImageBitmap(imageBitmap);
    }
}
