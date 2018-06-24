package adichauhan.com.trackingapp.app.service;

import adichauhan.com.entities.api.request.UpdateUserLocationRequest;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by adityachauhan on 23/06/18.
 *
 */


public interface ApiService {

    @POST("/v1/client/test/user/candidate/location")
    Observable<Response<Void>> updateUserLocation(@Body UpdateUserLocationRequest updateUserLocationRequest);

}
