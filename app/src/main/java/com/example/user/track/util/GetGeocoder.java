package com.example.user.track.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GetGeocoder {

    Context context;
    double lat;
    double lng;
    Geocoder geocoder;
    List<Address> address;
    String place="";
    public GetGeocoder(Context context) {
        this.context = context;
       geocoder = new Geocoder(context,Locale.getDefault());
    }
public String getAddress(String latitude,String longitude){

    if(latitude!=null&&longitude!=null) {
        try {
            lat=Double.parseDouble(latitude);
            lng=Double.parseDouble(longitude);
            address = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       place = address.get(0).getAddressLine(0);

     //  place += "," + address.get(0).getSubLocality();
      //  place += "," + address.get(0).getLocality();
      //  place+=","+address.get(0).getSubAdminArea();
    }
return  place;
}


    public void getLatLongFromPlace(String place) {
        try {



            address = geocoder.getFromLocationName(place, 5);

            if (address == null) {

            } else {
                Address location = address.get(0);
                lat = location.getLatitude();
                lng = location.getLongitude();



            }

        } catch (Exception e) {
            e.printStackTrace();


        }

    }


    public double getLat(){

        return  lat;
    }

    public  double getLng(){

        return  lng;
    }
   /* public String getPlace(){

        return place;
    }
*/

}
