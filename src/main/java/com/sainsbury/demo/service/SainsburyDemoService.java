package com.sainsbury.demo.service;

import com.sainsbury.demo.exception.SainsburyDemoServiceException;

public interface SainsburyDemoService {

    String scrapeGroceryProducts() throws SainsburyDemoServiceException;
}
