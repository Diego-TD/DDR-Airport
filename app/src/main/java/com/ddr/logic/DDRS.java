package com.ddr.logic;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DDRS {
    private static DDRS ddrSINGLETON = null;
    private final Context context;
    private List<AirportCityCountries> airportCityCountriesList;
    private List<Reservation> reservationsList;
    private Long userId;
    private final Gson gson = new Gson();
    private final File airportCityCountriesFile;
    private final File userIdFile;

    private DDRS(Context context) {
        this.context = context;
        this.airportCityCountriesFile = new File(context.getFilesDir(), "airportCityCountries.json");
        this.userIdFile = new File(context.getFilesDir(), "userIdFile.json");
        this.airportCityCountriesList = loadAirportCityCountries();
        this.userId = loadUserIdFile();
    }

    public static DDRS getDDRSINGLETON(Context context) {
        if (ddrSINGLETON == null) {
            ddrSINGLETON = new DDRS(context);
        }
        return ddrSINGLETON;
    }

    public void saveData() {
        saveAirportCityCountries();
        saveUserIdFile();
    }

    private List<AirportCityCountries> loadAirportCityCountries() {
        if (airportCityCountriesFile.exists()) {
            Type listType = new TypeToken<List<AirportCityCountries>>() {}.getType();
            try (FileReader reader = new FileReader(this.airportCityCountriesFile)) {
                return gson.fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    private Long loadUserIdFile() {
        if (userIdFile.exists()) {
            Type longType = new TypeToken<Long>() {}.getType();
            try (FileReader reader = new FileReader(this.userIdFile)) {
                return gson.fromJson(reader, longType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void saveAirportCityCountries() {
        try (FileWriter writer = new FileWriter(airportCityCountriesFile)) {
            gson.toJson(airportCityCountriesList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveUserIdFile() {
        try (FileWriter writer = new FileWriter(userIdFile)) {
            gson.toJson(userId, writer);
        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    public List<AirportCityCountries> getAirportCityCountriesList() {
        return airportCityCountriesList;
    }

    public void setAirportCityCountriesList(List<AirportCityCountries> airportCityCountriesList) {
        this.airportCityCountriesList = airportCityCountriesList;
        saveData(); // Save the updated data to the file
    }

    public List<Reservation> getReservationsList() {
        return reservationsList;
    }

    public void setReservationsList(List<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        saveData();
    }
}
