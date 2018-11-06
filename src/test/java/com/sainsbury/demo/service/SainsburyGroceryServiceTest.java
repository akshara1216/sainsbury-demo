package com.sainsbury.demo.service;


import com.sainsbury.demo.common.Constants;
import com.sainsbury.demo.common.Product;
import com.sainsbury.demo.exception.SainsburyGroceryServiceException;
import com.sainsbury.demo.service.impl.SainsburyGroceryServiceImpl;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
@PowerMockIgnore("javax.management.*")
public class SainsburyGroceryServiceTest {

    private SainsburyGroceryService sainsburyGroceryService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void setUp() {
        sainsburyGroceryService = new SainsburyGroceryServiceImpl();

        Whitebox.setInternalState(
                sainsburyGroceryService,
                "sainsburysWebsiteURL",
                "http://localhost:8080/test");
    }

    @Test
    public void verifySuccessfulExecution() throws Exception {
        // read website response from resources path
        String websiteResponse = FileUtils.readFileToString(new File("src/test/resources/website_response.htm"),
                "UTF8");

        // Mock objects and set the response
        Connection connection = Mockito.mock(Connection.class);

        Mockito.when(connection.get()).thenReturn(Jsoup.parse(websiteResponse));
        PowerMockito.mockStatic(Jsoup.class);

        PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);

        // execute the service
        List<Product> products = sainsburyGroceryService.getGroceryProducts();

        // assert
        Assert.assertNotNull("Products List can not be Null", products);
        Assert.assertFalse("Products List can not be empty", products.isEmpty());
        Assert.assertEquals("Mismatch in Products List size!",17, products.size());
    }

    @Test
    public void verifyServiceUnavailableExecution() throws Exception {
        // set the expected error class and message
        expectedEx.expect(SainsburyGroceryServiceException.class);
        expectedEx.expectMessage(Constants.ERRO_CODE_SERVICE_UNAVAILABLE_MSG);

        // Mock objects and set the response
        Connection connection = Mockito.mock(Connection.class);

        Mockito.when(connection.get()).thenThrow(new IOException());
        PowerMockito.mockStatic(Jsoup.class);

        PowerMockito.when(Jsoup.connect(Mockito.anyString())).thenReturn(connection);

        // execute the service
        sainsburyGroceryService.getGroceryProducts();
    }
}
