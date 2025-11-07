package com.weather.dashboard.service;

import com.weather.dashboard.model.City;
import com.weather.dashboard.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class WeatherService {

    @Autowired
    private CityRepository cityRepository;

    private Random random = new Random();

    private String[][] weatherData = {
        {"Delhi", "Sunny", "☀️", "32", "45"},
        {"Mumbai", "Rainy", "🌧️", "28", "80"},
        {"Bangalore", "Partly Cloudy", "⛅", "26", "65"},
        {"Kolkata", "Cloudy", "☁️", "30", "70"},
        {"Chennai", "Hot & Humid", "🌡️", "35", "85"},
        {"Pune", "Pleasant", "🌤️", "24", "55"},
        {"Hyderabad", "Clear", "✨", "29", "60"},
        {"Jaipur", "Sunny", "☀️", "33", "40"}
    };

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City addCity(String cityName) {
        Optional<City> existing = cityRepository.findByName(cityName);
        if (existing.isPresent()) {
            return existing.get();
        }

        City city = new City();
        city.setName(cityName);
        
        String[] weatherInfo = getSimulatedWeather(cityName);
        city.setWeatherDescription(weatherInfo[0]);
        city.setWeatherIcon(weatherInfo[1]);
        city.setTemperature(Double.parseDouble(weatherInfo[2]));
        city.setHumidity(Double.parseDouble(weatherInfo[3]));
        city.setWindSpeed(Math.random() * 10 + 1);
        city.setCountry("India");

        return cityRepository.save(city);
    }

    public City updateWeather(Long id) {
        City city = getCityById(id);
        if (city != null) {
            String[] weatherInfo = getSimulatedWeather(city.getName());
            city.setWeatherDescription(weatherInfo[0]);
            city.setWeatherIcon(weatherInfo[1]);
            city.setTemperature(Double.parseDouble(weatherInfo[2]));
            city.setHumidity(Double.parseDouble(weatherInfo[3]));
            city.setWindSpeed(Math.random() * 10 + 1);
            return cityRepository.save(city);
        }
        return null;
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    private String[] getSimulatedWeather(String cityName) {
        for (String[] data : weatherData) {
            if (data[0].equalsIgnoreCase(cityName)) {
                return new String[]{data[1], data[2], data[3], data[4]};
            }
        }
        return new String[]{"Partly Cloudy", "⛅", "25", "60"};
    }

    public List<String> get7DayForecast(Long cityId) {
        List<String> forecast = new java.util.ArrayList<>();
        String[] conditions = {"Sunny", "Cloudy", "Rainy", "Partly Cloudy", "Clear", "Hot", "Pleasant"};
        for (int i = 0; i < 7; i++) {
            forecast.add((i + 1) + " Day: " + conditions[i] + " - " + (25 + random.nextInt(10)) + "°C");
        }
        return forecast;
    }
}
