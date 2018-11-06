package com.sainsbury.demo.service;


import com.sainsbury.demo.common.Amount;
import com.sainsbury.demo.common.Product;
import com.sainsbury.demo.service.impl.SainsburyDemoServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SainsburyDemoServiceTest {

    private SainsburyDemoService sainsburyDemoService;

    @Mock
    private SainsburyGroceryService sainsburyGroceryService;

    @Before
    public void setUp() {
        sainsburyDemoService = new SainsburyDemoServiceImpl();

        Whitebox.setInternalState(sainsburyDemoService, sainsburyGroceryService);

    }

    @Test
    public void testWithSuccessfulExecution() throws Exception {
        JSONAssert.assertEquals("[]", "[]", JSONCompareMode.STRICT);
        List<Product> sampleProducts = getSampleProducts();
        Mockito.when(sainsburyGroceryService.getGroceryProducts()).thenReturn(sampleProducts);

        // execute the service
        String actualJson = sainsburyDemoService.scrapeGroceryProducts();

        // read website response from resources path
        String expectedJson = FileUtils.readFileToString(new File("src/test/resources/expected_json_response_1.json"));

        JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }

    @Test
    public void testWithNullDescription() throws Exception {
        JSONAssert.assertEquals("[]", "[]", JSONCompareMode.STRICT);
        List<Product> sampleProducts = getSampleProducts();
        sampleProducts.get(0).setDescription(null);

        Mockito.when(sainsburyGroceryService.getGroceryProducts()).thenReturn(sampleProducts);

        // execute the service
        String actualJson = sainsburyDemoService.scrapeGroceryProducts();

        // read website response from resources path
        String expectedJson = FileUtils.readFileToString(new File("src/test/resources/expected_json_response_2.json"));

        //JSONAssert.assertEquals(expectedJson, actualJson, JSONCompareMode.STRICT);
    }


    private List<Product> getSampleProducts() {
        List<Product> products = new ArrayList<Product>();

        Product product1 = new Product();
        product1.setTitle("product-1");
        product1.setPrice(new Amount(new BigDecimal(21.25), true));
        product1.setCalories(200);
        product1.setDescription("product-1 desc");
        products.add(product1);

        Product product2 = new Product();
        product2.setTitle("product-2");
        product2.setPrice(new Amount(new BigDecimal(43.5785), true));
        product2.setCalories(300);
        product2.setDescription("product-2 desc");
        products.add(product2);

        return products;
    }
}
