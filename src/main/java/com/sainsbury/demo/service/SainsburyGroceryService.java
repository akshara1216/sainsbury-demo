package com.sainsbury.demo.service;

import com.sainsbury.demo.common.Product;
import com.sainsbury.demo.exception.SainsburyGroceryServiceException;

import java.util.List;

public interface SainsburyGroceryService {

    List<Product> getGroceryProducts() throws SainsburyGroceryServiceException;
}
