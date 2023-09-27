package com.edutrain.busroute.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutrain.busroute.admin.model.BusData;
import com.edutrain.busroute.admin.model.BusRoute;
import com.edutrain.busroute.admin.repository.BusRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RestController
@RequestMapping("/busroutes")
public class BusRouteAdminController {

	@Autowired
	BusRoute busRoute;

	@Autowired
	BusData busData;

	@Autowired
	private final BusRepository busRepository;

	public BusRouteAdminController(BusRepository busRepository) {
		this.busRepository = busRepository;
	}

	@GetMapping("/getallbusroutes")
	public List<String> getBusRoutes() {

		List<BusRoute> BusRouteList = new ArrayList<BusRoute>();
		List<String> stringRouteList = new ArrayList<String>();

		busRepository.findAll().forEach((busData) -> {
			BusRouteList.add(busData.getBusRoute());
		});

		BusRouteList.forEach((busRoute) -> {
			stringRouteList.add("BusNo: " + busRoute.getBusNo() + ",Source: " + busRoute.getSource() + ", Destination: "
					+ busRoute.getDestination() + " ,Price: " + busRoute.getPrice());
		});

		return stringRouteList;

	}

	@PostMapping("/addbusroute")
	public String addBusRoute(@RequestBody BusRoute busRoute) {

		String busNumber = busRoute.getBusNo();
		System.out.println("BusNumber in addbusroute is " + busNumber);

		// BusData busData= new BusData();
		busData.setBusNo(busNumber);
		busData.setBusRoute(busRoute);

		try {
			BusData retValue = busRepository.save(busData);

			if (retValue != null) {
				return "BusRoute Added successfully";
			} else {
				return "There is an error in adding BusRoute";
			}
		} catch (Exception e) {

			return "There is an error in adding BusRoute";
		}

	}

	@GetMapping("/getbusroute/{BusNo}")
	public String getBusRoute(@PathVariable String BusNo) {

		Optional<BusData> busDataRetValue = busRepository.findById(BusNo);

		if (busDataRetValue.isPresent()) {

			busData = busDataRetValue.get();
			busRoute = busData.getBusRoute();
			String BusRouteStr = "BusNo: " + busRoute.getBusNo() + ",Source: " + busRoute.getSource()
					+ ", Destination: " + busRoute.getDestination() + " ,Price: " + busRoute.getPrice();
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonString;
			try {
				jsonString = ow.writeValueAsString(busRoute);
				return jsonString;
			} catch (JsonProcessingException e) {
				
				e.printStackTrace();
				return "Exception occured";
			}	

		} else {

			return "Route Not found";
		}

	}

	@DeleteMapping("/deleteroute/{BusNo}")
	public String deleteBusRoute(@PathVariable String BusNo) {
		try {
			busRepository.deleteById(BusNo);
			return "Route Deleted successfully";
		} catch (Exception e) {
			return "Error while deletion";
		}

	}

	@PutMapping("/updateeroute")
	public String updateBusRoute(@RequestBody BusRoute busRoute) {

		String busNumber = busRoute.getBusNo();
		System.out.println("BusNumber in addbusroute is " + busNumber);
		String RetValue = getBusRoute(busNumber);

		if (RetValue.equalsIgnoreCase("Route Not found")) {
			return "Route Not found,Please enter valid route";
		} else {

			// BusData busData= new BusData();
			busData.setBusNo(busNumber);
			busData.setBusRoute(busRoute);

			try {
				BusData retValue = busRepository.save(busData);

				if (retValue != null) {
					return "BusRoute Updated successfully";
				} else {
					return "There is an error in updating BusRoute";
				}
			} catch (Exception e) {

				return "There is an error in updating  BusRoute";
			}
		}

	}

}
