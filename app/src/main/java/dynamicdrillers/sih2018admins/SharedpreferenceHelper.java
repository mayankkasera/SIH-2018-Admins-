package dynamicdrillers.sih2018admins;

import android.content.Context;
import android.content.SharedPreferences;

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
        editor.putString("state_name",StateName);
        editor.putString("password",Password);
        editor.putString("email",Email);



        editor.commit();
        editor.apply();
    }

    public  void setDistrictAdminData(String Name,String Image,String Mobileno,String Gender,
                                      String Type,String StateName,String DistrictName,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("gender",Gender);
        editor.putString("type",Type);
        editor.putString("state_name",StateName);
        editor.putString("district_name",DistrictName);
        editor.putString("password",Password);
        editor.putString("email",Email);


        editor.commit();
        editor.apply();
    }

    public  void setSubRegionAdminData(String Name,String Image,String Mobileno,String Gender,
                                       String Type,String StateName,String DistrictName,String SubRegionName,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("gender",Gender);
        editor.putString("type",Type);
        editor.putString("state_name",StateName);
        editor.putString("district_name",DistrictName);
        editor.putString("subregion_name",SubRegionName);
        editor.putString("password",Password);
        editor.putString("email",Email);


        editor.commit();
        editor.apply();
    }

    public  void setAuthorityAdminData(String Name,String Image,String Mobileno,String Type,
                                       String StateName,String DistrictName,String SubRegionName,String Password,String Email)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SharedprefenceName,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",Name);
        editor.putString("image",Image);
        editor.putString("mobileno",Mobileno);
        editor.putString("type",Type);
        editor.putString("state_name",StateName);
        editor.putString("district_name",DistrictName);
        editor.putString("subregion_name",SubRegionName);
        editor.putString("password",Password);
        editor.putString("email",Email);

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

}
