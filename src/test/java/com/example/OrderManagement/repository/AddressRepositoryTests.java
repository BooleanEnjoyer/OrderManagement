package com.example.OrderManagement.repository;

import com.example.OrderManagement.entity.Address;
import com.example.OrderManagement.repositories.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.NoSuchElementException;

@DataMongoTest
@TestPropertySource(locations="classpath:application-test.properties")
@ExtendWith(MockitoExtension.class)
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    public void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    public void givenValidAddress_whenSaveAddress_thenShouldSaveAddressInDatabase(){

        //Arrange
        Address address = Address.builder().
                city("Zielona Góra").
                zipCode("65-419").
                street("Generała Józefa Sowińskiego").
                houseNumber(38).build();
        //Act
        Address savedAddress = addressRepository.save(address);
        //Assert
        Assertions.assertThat(savedAddress).isNotNull();
        Assertions.assertThat(savedAddress.getId()).isNotNull();
        Assertions.assertThat(savedAddress.getCity()).isEqualTo("Zielona Góra");
    }

    @Test
    public void givenValidAddressFields_whenFindAddressByFields_thenShouldFindAddressInDatabaseByFields(){

        //Arrange
        Address address = Address.builder().
                id("333767c43016e11a9ba2c333").
                city("Wrocław").
                zipCode("55-419").
                street("Grunwaldzka").
                houseNumber(11).build();
        addressRepository.save(address);

        //Act
        Address foundAddress = addressRepository.findAddressByFields("Grunwaldzka",11,"Wrocław","55-419").orElseThrow();
        //Assert
        Assertions.assertThat(foundAddress.getId()).isNotNull();
        Assertions.assertThat(foundAddress.getCity()).isEqualTo("Wrocław");
        Assertions.assertThat(foundAddress.getHouseNumber()).isEqualTo(11);
        Assertions.assertThat(foundAddress.getZipCode()).isEqualTo("55-419");
        Assertions.assertThat(foundAddress.getStreet()).isEqualTo("Grunwaldzka");
    }


    @Test
    public void givenNotExistingAddressFields_whenFindAddressByFields_thenShouldThrowNoSuchElementException(){

        //Arrange
        Address address = Address.builder().
                id("333767c43016e11a9ba2c333").
                city("Warszawa").
                zipCode("65-419").
                street("Generała Józefa Sowińskiego").
                houseNumber(38).build();

        addressRepository.save(address);

        final String street = "Malowana";
        final int houseNumber = 1;
        final String city = "Toruń";
        final String zipCode = "22-333";
        //Act
        //Assert
        Assertions.assertThatThrownBy(() -> addressRepository.findAddressByFields(street, houseNumber, city, zipCode).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
    }
}
