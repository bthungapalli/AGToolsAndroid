package com.agtools.agtools.Constants;

/**
 * Created by HOME on 1/9/2018.
 */
public class ApiConstants {
   // public static String API="https://vserver.amicuswa.com/agtools/";
     public static String API="https://my.ag.tools/agtools/";
    public static String Login_URL = API.concat("login");

    public static String ForgotPassword_URL = API.concat("forgotPassword");
    public static String Signup_URL = API.concat("registration");
    public static String Weather_URL = API.concat("weather/");
    public static String GetFuelPrices_URL = API.concat("widgets/oilprice?type=fuel");
    public static String GetGasPrices_URL = API.concat("widgets/oilprice?type=gas");
    public static String GetVolume_URL = API.concat("widgets/volume?commodity=");
    public static String Locactions_Shippingprice_URL = API.concat("widgets/LocationOrgin/sp/");
    public static String ShippingPriceGet_URL = API.concat("widgets/shippingPrice?type=");
    public static String ExchangesGet_URL = API.concat("exchange");
    public static String TerminalPrice_URL = API.concat("widgets/terminalPrice?type=");
    public static String Terminal_location_URL = API.concat("widgets/LocationOrgin/tp/");
    public static String Weather_alert_URL = API.concat("widgets/weatherAlert");
    public static String ConsumerPriceIndex_URL = API.concat("widgets/allcpi");
    public static String JuiceGet_URL = API.concat("widgets/juiceVolume/2/");
    public static String FrrezeGet_URL = API.concat("widgets/juiceVolume/3/");
    public static String LiveWeather_URL = API.concat("weather/compare/");
    public static String Email_verification_URL = API.concat("emailValidation?emailId=");
    public static String ChangePassword_URL = API.concat("updatePassword");
    public static String States_URL = API.concat("fetchCityStates/1/");
    public static String Cities_URL = API.concat("fetchCityStates/2/");
    public static String HolidaysPrice_URL = API.concat("holidays/");
    public static String Fav_Url =API.concat("userPreferences");
    public static String CHECK_USER =API.concat("checkUser?sessionId=");

}
