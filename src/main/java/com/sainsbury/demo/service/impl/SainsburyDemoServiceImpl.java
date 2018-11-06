package com.sainsbury.demo.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sainsbury.demo.common.*;
import com.sainsbury.demo.exception.SainsburyDemoServiceException;
import com.sainsbury.demo.exception.SainsburyGroceryServiceException;
import com.sainsbury.demo.service.SainsburyDemoService;
import com.sainsbury.demo.service.SainsburyGroceryService;
import com.sainsbury.demo.util.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service ("sainsburyDemoService")
public class SainsburyDemoServiceImpl implements SainsburyDemoService {

    private static final Logger logger = LogManager.getLogger(SainsburyDemoServiceImpl.class.getName());

    @Autowired
    private SainsburyGroceryService sainsburyGroceryService;

    public String scrapeGroceryProducts() throws SainsburyDemoServiceException {
        try {
            List<Product> groceryProducts = sainsburyGroceryService.getGroceryProducts();

            SainsburyDemoResponse response = getResponse(groceryProducts);

            return getJSONText(response);

        } catch (SainsburyGroceryServiceException e) {
            logger.error("Failed to retrieve products list", e);
            throw new SainsburyDemoServiceException(
                    Constants.ERRO_CODE_GENERIC_SERVICE_ERROR,
                    Constants.ERRO_CODE_GENERIC_SERVICE_ERROR_MSG);
        }  catch (Exception e) {
            logger.error("Failed to construct demo response", e);
            throw new SainsburyDemoServiceException(
                    Constants.ERRO_CODE_FAILED_TO_CONSTRUCT_RESPONSE,
                    Constants.ERRO_CODE_FAILED_TO_CONSTRUCT_RESPONSE_MSG);
        }
    }

    private String getJSONText(SainsburyDemoResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        StringWriter sw = new StringWriter();
        mapper.writeValue(sw, response);
        return sw.toString();
    }

    private SainsburyDemoResponse getResponse(List<Product> groceryProducts) {
        SainsburyDemoResponse response = new SainsburyDemoResponse();
        response.setProducts(groceryProducts);

        // calculate total
        Amount totalAmount = Amount.ZERO;
        Amount vatAmount = new Amount(BigDecimal.ZERO,true);
        for (Product product : groceryProducts) {
            // calculate vat
            Amount itemVatAmount = AmountUtil.percentage(product.getPrice(), Constants.VAT_PERCENTAGE);
            vatAmount.add(itemVatAmount);
            totalAmount.add(product.getPrice());
        }

        // add it to Total
        Total total = new Total();
        total.setTotal(totalAmount);
        total.setVatAmount(vatAmount);
        response.setTotal(total);

        return response;
    }
}
