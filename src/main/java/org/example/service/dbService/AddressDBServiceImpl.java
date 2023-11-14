package org.example.service.dbService;

import jakarta.transaction.Transactional;
import org.example.dao.AddressDAO;
import org.example.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class AddressDBServiceImpl implements AddressDBService {

        @Autowired
        private AddressDAO addressDAO;

        @Override
        @Transactional
        public List<Address> getAddresses() {
            return addressDAO.getAddresses();
        }

        @Override
        @Transactional
        public void saveAddress(Address address) {
            addressDAO.saveAddress(address);
        }

        @Override
        @Transactional
        public Address getAddress(int id) {
            return addressDAO.getAddress(id);
        }

        @Override
        @Transactional
        public void deleteAddress(int id) {
            addressDAO.deleteAddress(id);
        }

        @Override
        @Transactional
        public Address getAddressByZipCode(String zipCode) {
            return addressDAO.findAddressByZipCode(zipCode);
        }

        @Override
        @Transactional
        public void updateAddress(Address address) {
            addressDAO.updateAddress(address);
        }
    }


