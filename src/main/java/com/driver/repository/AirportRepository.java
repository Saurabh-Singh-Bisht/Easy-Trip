package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    private Map<String, Airport> airportMap = new HashMap<>();
    private Map<Integer, Passenger> passengerMap = new HashMap<>();
    private Map<Integer, Flight> flightMap = new HashMap<>();
    private Map<Integer, List<Integer>> bookTicketMap = new HashMap<>();
    public void add(Airport airport) {
        airportMap.put(airport.getAirportName(), airport);
    }

    public void add(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId(), passenger);
    }
    public void add(Flight flight) {
        flightMap.put(flight.getFlightId(), flight);
    }
    public String getLargestAirportName() {
        int maxTerminals =0;
        String ans = "";
        for (Airport airport: airportMap.values()){
            if (airport.getNoOfTerminals() > maxTerminals){
                maxTerminals = airport.getNoOfTerminals();
                ans = airport.getAirportName();
            } else if (airport.getNoOfTerminals() == maxTerminals) {
                if(airport.getAirportName().compareTo(ans) < 0)
                    ans = airport.getAirportName();
            }
        }
        return ans;
    }

    public double getShortestTravelBetweenCities(City fromCity, City toCity) {
        double ans = Double.MAX_VALUE;
        for (Flight flight: flightMap.values()){
            if (flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)){
                ans = Math.min(ans, flight.getDuration());
            }
        }
        if(ans == Double.MAX_VALUE)
            return -1;
        return ans;
    }

    public String bookTicket(Integer flightId, Integer passengerId) {
        Flight flight = flightMap.get(flightId);
        int passengersTillNow = bookTicketMap.get(flightId).size();
        if(flight.getMaxCapacity() == passengersTillNow)
            return "FAILURE";
        if(bookTicketMap.get(flightId).contains(passengerId))
            return "FAILURE";
        List<Integer> passengerList = bookTicketMap.get(flightId);
        passengerList.add(passengerId);
        bookTicketMap.put(flightId, passengerList);
        return "SUCCESS";
    }

    public String cancelTicket(Integer flightId, Integer passengerId) {
        if(flightMap.containsKey(flightId) == false)
            return "FAILURE";
        List<Integer> passangersList = bookTicketMap.get(flightId);
        if(passangersList.contains(passengerId))
            return "FAILURE";
        passangersList.remove(passengerId);
        bookTicketMap.put(flightId, passangersList);
        return "SUCCESS";
    }

    public int countOfBookings(Integer passengerId) {
        int count =0;
        for (Integer flightId: bookTicketMap.keySet()){
            List<Integer> passengers = bookTicketMap.get(flightId);
            for (Integer passenger: passengers){
                if(passenger == passengerId){
                    count++;
                }
            }
        }
        return count;
    }

    public String getAirportName(Integer flightId) {
        Flight flight = flightMap.get(flightId);
        City takeOfStation = flight.getFromCity();
        for (Airport airport: airportMap.values()){
            if (airport.getCity().equals(takeOfStation)){
                return airport.getAirportName();
            }
        }
        return null;
    }

    public int getFair(Integer flightId) {
        int noOfPeopleBooked =bookTicketMap.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
    }

    public int getTotalPeoples(Date date, String airportName) {
        List<Integer> flightIds = new ArrayList<>();
        for (Flight flight: flightMap.values()){
            if(flight.getFlightDate().equals(date) && (flight.getFromCity().equals(airportName)
            || flight.getToCity().equals(airportName))){
                flightIds.add(flight.getFlightId());
            }
        }
        int count =0;
        for (Integer flight: flightIds){
            if(bookTicketMap.containsKey(flight)){
                count++;
            }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int noOfPeopleAlreadyBooked = bookTicketMap.get(flightId).size();
        int priceOfFlight = 3000 + noOfPeopleAlreadyBooked*50;
        return priceOfFlight;
    }
}
