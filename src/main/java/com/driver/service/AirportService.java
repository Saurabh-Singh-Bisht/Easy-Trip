package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
    private AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport) {
        airportRepository.add(airport);
    }

    public String getLargestAirportName() {
        String ans = airportRepository.getLargestAirportName();
        return ans;
    }

    public void addPassenger(Passenger passenger) {
        airportRepository.add(passenger);
    }

    public void addFlight(Flight flight) {
        airportRepository.add(flight);
    }

    public double getShortestTravelBetweenCities(City fromCity, City toCity) {
        return airportRepository.getShortestTravelBetweenCities(fromCity, toCity);
    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        return airportRepository.bookTicket(flightId, passengerId);
    }

    public String cancelTicket(Integer flightId, Integer passengerId) {
        return airportRepository.cancelTicket(flightId, passengerId);
    }

    public int countOfBookings(Integer passengerId) {
        return airportRepository.countOfBookings(passengerId);
    }

    public String getAirportName(Integer flightId) {
        return airportRepository.getAirportName(flightId);
    }

    public int calculateFair(Integer flightId) {
        return airportRepository.getFair(flightId);
    }
}
