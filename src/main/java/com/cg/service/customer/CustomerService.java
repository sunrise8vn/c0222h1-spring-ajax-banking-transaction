package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.service.IGeneralService;

public interface CustomerService extends IGeneralService<Customer> {

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);
}
