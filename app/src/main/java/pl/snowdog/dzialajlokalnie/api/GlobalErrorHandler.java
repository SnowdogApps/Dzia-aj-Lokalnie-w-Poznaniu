package pl.snowdog.dzialajlokalnie.api;

import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.snowdog.dzialajlokalnie.events.NetworkErrorEvent;
import pl.snowdog.dzialajlokalnie.model.ApiErrorEvent;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by bartek on 09.07.15.
 */
public class GlobalErrorHandler implements ErrorHandler {



    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause == null) {
            return cause;
        }

        switch(cause.getKind()) {
            case NETWORK:
                EventBus.getDefault().post(new NetworkErrorEvent());
            default:
                Response r = cause.getResponse();
                if(r == null) {
                    return cause;
                }

                EventBus.getDefault().post(new ApiErrorEvent(r.getStatus(), r.getReason(), r.getUrl()));
        }

        return cause;
    }
}
