const apiUrl = 'http://localhost:8080/api/weather';

async function fetchCities() {
    const res = await fetch(`${apiUrl}/cities`);
    const cities = await res.json();
    displayCities(cities);
}

function displayCities(cities) {
    const div = document.getElementById('cities');
    div.innerHTML = '';
    if (cities.length === 0) {
        div.innerHTML = '<p>No cities added yet.</p>';
        return;
    }
    cities.forEach(city => {
        div.innerHTML += `
        <div class="city-card">
            <div class="city-details">
                <div class="city-row">
                    <span class="weather-icon">${city.weatherIcon || ''}</span>
                    <b>${city.name}</b> (${city.country || ''})
                </div>
                <div>${city.weatherDescription || ''}</div>
                <div>Temperature: ${city.temperature}°C, Humidity: ${city.humidity}%</div>
                <div>Wind Speed: ${city.windSpeed} km/h</div>
            </div>
            <div class="city-actions">
                <button onclick="updateWeather(${city.id})">Update Weather</button>
                <button onclick="deleteCity(${city.id})">Delete</button>
            </div>
        </div>
        `;
    });
}

document.getElementById('addCityForm').onsubmit = async function(e) {
    e.preventDefault();
    const cityName = document.getElementById('cityInput').value;
    await fetch(`${apiUrl}/city/add?cityName=${encodeURIComponent(cityName)}`, { method: 'POST' });
    document.getElementById('cityInput').value = '';
    fetchCities();
};

async function updateWeather(id) {
    await fetch(`${apiUrl}/city/${id}/update`, { method: 'PUT' });
    fetchCities();
}

async function deleteCity(id) {
    await fetch(`${apiUrl}/city/${id}`, { method: 'DELETE' });
    fetchCities();
}

fetchCities();
