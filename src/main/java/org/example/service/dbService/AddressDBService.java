package org.example.service.dbService;

import org.example.model.Address;

import java.util.List;

public interface AddressDBService {


        List<Address> getAddresses();

        void saveAddress(Address address);

        Address getAddress(int id);

        void deleteAddress(int id);

        Address getAddressByZipCode(String zipCode);

        void updateAddress(Address address);

}
