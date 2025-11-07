package com.weather.dashboard.controller;

import com.weather.dashboard.model.City;
import com.weather.dashboard.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return weatherService.getAllCities();
    }

    @PostMapping("/city/add")
public City addCity(@RequestParam String cityName) {
    return weatherService.addCity(cityName);
}

    @PutMapping("/city/{id}/update")
    public City updateWeather(@PathVariable Long id) {
        return weatherService.updateWeather(id);
    }

    @DeleteMapping("/city/{id}")
    public void deleteCity(@PathVariable Long id) {
        weatherService.deleteCity(id);
    }

    @GetMapping("/forecast/{cityId}")
    public List<String> getForecast(@PathVariable Long cityId) {
        return weatherService.get7DayForecast(cityId);
    }

    @GetMapping("/health")
    public String health() {
        return "Weather Dashboard Backend is running!";
    }
}
