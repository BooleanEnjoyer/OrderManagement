package com.example.OrderManagement.services;

import com.example.OrderManagement.entity.Address;
import com.example.OrderManagement.repositories.AddressRepository;
import com.example.OrderManagement.response.AddingAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private static final String SUCCESSFUL_ADDRESS_ADDING_MESSAGE = "Successfully added your address";

    public Optional<Address> findAddressByFields(Address address){

        String street = address.getStreet();
        int houseNumber = address.getHouseNumber();
        String city = address.getCity();
        String zipCode = address.getZipCode();
        return addressRepository.findAddressByFields(street, houseNumber, city, zipCode);
    }

    public AddingAddressResponse addAddress(Address addedAddress){

        AddingAddressResponse addingAddressResponse = new AddingAddressResponse();
        try{
            Address fetchedAddress = findAddressByFields(addedAddress).orElseThrow();
            addingAddressResponse.setAddress(fetchedAddress);
            addingAddressResponse.setMessage(SUCCESSFUL_ADDRESS_ADDING_MESSAGE);
            return addingAddressResponse;
        }
        catch (NoSuchElementException e){
                Address address = addressRepository.save(addedAddress);
                addingAddressResponse.setAddress(address);
                addingAddressResponse.setMessage(SUCCESSFUL_ADDRESS_ADDING_MESSAGE);
                return addingAddressResponse;
        }
    }
}
