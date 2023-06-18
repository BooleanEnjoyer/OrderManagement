package com.example.OrderManagement.repositories;

import com.example.OrderManagement.entity.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    @Query("{'street': ?0, 'houseNumber': ?1,'city': ?2,'zipCode': ?3 }")
    Optional<Address> findAddressByFields(String street, int houseNumber, String city, String zipCode);
}
