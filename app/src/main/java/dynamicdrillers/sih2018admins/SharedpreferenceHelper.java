package dynamicdrillers.sih2018admins;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Happy-Singh on 3/16/2018.
 */

public class SharedpreferenceHelper {

    private static Context mCtx;
    private static SharedpreferenceHelper mInstance;
    public static final String SharedprefenceName = "USER_DATA";

    private SharedpreferenceHelper(Context context) {
        this.mCtx = context;
    }

    public static synchronized SharedpreferenceHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedpreferenceHelper(context);
        }
        return mInstance;
    }

    public  void  logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public  void setAdminData(String Name,String Image,String Mobileno,String Gender,String Type,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("gender",Gender);
        editor.putString("type",Type);
        editor.putString("password",Password);
        editor.putString("email",Email);


        editor.commit();
        editor.apply();
    }

    public  void setStateAdminData(String Name,String Image,String Mobileno,String Gender,String Type,String StateName,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("gender",Gender);
        editor.putString("type",Type);
        editor.putString("state",StateName);
        editor.putString("password",Password);
        editor.putString("email",Email);



        editor.commit();
        editor.apply();
    }

    public  void setDistrictAdminData(String Name,String Image,String Mobileno,String Gender,
                                      String Type,String StateName,String DistrictName,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        Toast.makeText(SharedpreferenceHelper.mCtx, "yes", Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("gender",Gender);
        editor.putString("type",Type);
        editor.putString("state",StateName);
        editor.putString("district",DistrictName);
        editor.putString("password",Password);
        editor.putString("email",Email);


        editor.commit();
        editor.apply();
    }

    public  void setRegionAdminData(String Name,String Image,String Mobileno,String Gender,
                                       String Type,String StateName,String DistrictName,String RegionName,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("gender",Gender);
        editor.putString("type",Type);
        editor.putString("state",StateName);
        editor.putString("district",DistrictName);
        editor.putString("region",RegionName);
        editor.putString("password",Password);
        editor.putString("email",Email);


        editor.commit();
        editor.apply();
    }

    public  void setAuthorityAdminData(String Name,String Image,String Mobileno,String Type,
                                       String StateName,String DistrictName,String RegionName,String Password,String Email, String Authority)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("type",Type);
        editor.putString("state",StateName);
        editor.putString("district",DistrictName);
        editor.putString("region",RegionName);
        editor.putString("password",Password);
        editor.putString("email",Email);
        editor.putString("name",Authority);

        editor.commit();
        editor.apply();
    }

    public String getName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return    sharedPreferences.getString("name",null);

    }

    public String getType()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return    sharedPreferences.getString("type",null);

    }
    public String getDistrict()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);
        return    sharedPreferences.getString("district",null);

    }

}
