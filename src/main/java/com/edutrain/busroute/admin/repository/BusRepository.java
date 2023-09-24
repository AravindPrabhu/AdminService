package com.edutrain.busroute.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.edutrain.busroute.admin.model.BusData;
import com.edutrain.busroute.admin.model.BusRoute;

@Repository
public interface BusRepository extends MongoRepository<BusData,String> {

}
