package org.example.dao;

import org.example.model.Address;

import java.util.List;

public interface AddressDAO {

    List<Address> getAddresses();

    void saveAddress(Address address);

    Address getAddress(int id);

    void deleteAddress(int id);

    void updateAddress(Address address);

    Address findAddressByZipCode(String zipCode);
}