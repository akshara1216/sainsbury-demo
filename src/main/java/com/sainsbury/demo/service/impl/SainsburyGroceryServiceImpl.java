package com.sainsbury.demo.service.impl;

import com.sainsbury.demo.common.Amount;
import com.sainsbury.demo.common.Constants;
import com.sainsbury.demo.common.Product;
import com.sainsbury.demo.exception.SainsburyGroceryServiceException;
import com.sainsbury.demo.service.SainsburyGroceryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.SchemaOutputResolver;

@Service ("sainsburyGroceryService")
public class SainsburyGroceryServiceImpl implements SainsburyGroceryService {

    private static final Logger logger = LogManager.getLogger(SainsburyGroceryServiceImpl.class.getName());


    @Value( "${sainsburys_website_url}" )
    private String sainsburysWebsiteURL;

    public List<Product> getGroceryProducts() throws SainsburyGroceryServiceException {
        List<Product> products = new ArrayList<Product>();

        try {
            logger.debug("sainsburysWebsiteURL: " + sainsburysWebsiteURL);

            Document groceriesDocument = getGroceriesDocument(sainsburysWebsiteURL);
         /*   System.out.println(groceriesDocument);*/

            products = getProductsList(groceriesDocument);
          /*  System.out.println(products);*/

        } catch (SainsburyGroceryServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new SainsburyGroceryServiceException(
                    Constants.ERRO_CODE_GENERIC_SERVICE_ERROR,
                    Constants.ERRO_CODE_GENERIC_SERVICE_ERROR_MSG);
        }
        return products;
    }

    private Document getGroceriesDocument(String url) throws SainsburyGroceryServiceException {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception e) {
            throw new SainsburyGroceryServiceException(
                    Constants.ERRO_CODE_SERVICE_UNAVAILABLE,
                    Constants.ERRO_CODE_SERVICE_UNAVAILABLE_MSG);
        }
    }

    private List<Product> getProductsList(Document listPageDoc) throws Exception{
        List<Product> productsList = new ArrayList<Product>();

        if(listPageDoc != null){
            Elements productElements = listPageDoc.select("div#productLister li.gridItem");

            for (Element productElement: productElements) {
                Product product = new Product();
                product.setTitle(getProductTitle(productElement));
                product.setPrice(getProductUnitPrice(productElement));
                String productDetailsPageUrl = productElement.select("a").first().attr("abs:href");
                parseDescription(productDetailsPageUrl,product);
                productsList.add(product);
            }
        }
        return productsList;
    }

    private void parseDescription(String URI,Product product) throws Exception
    {
        Document productDetailsPageDoc = this.getGroceriesDocument(URI);

        if(productDetailsPageDoc != null){
            product.setCalories(this.getCalories(productDetailsPageDoc));
            product.setDescription(this.getDescription(productDetailsPageDoc));
        }
    }


    private String getProductTitle(Element product) throws Exception{
        String title = "";
        try{
            title = product.select("a").first().text();
            if(title != null){
                title = title.trim();
            }
        }catch (Exception e) {
            throw new SainsburyGroceryServiceException(
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_TITLE,
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_TITLE_MSG);
        }
        return title;
    }


    private Amount getProductUnitPrice(Element product) throws Exception{
        BigDecimal unitPrice = null;
        try{
            String unitPriceStr = product.select("p.pricePerUnit").first()
                    .html().replaceAll("<abbr.*", "").substring(1);

            unitPrice = new BigDecimal(unitPriceStr.trim());

        }catch (Exception e) {
            e.printStackTrace();
            throw new SainsburyGroceryServiceException(
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_PRICE,
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_PRICE_MSG);
        }
        return new Amount(unitPrice, true);
    }

    private Integer getCalories(Document productDetailsPageDoc) throws Exception {
        Integer calories = null;
        try{
            List<Element> trElements =  productDetailsPageDoc.select("table.nutritionTable tbody tr");
            if(trElements != null && trElements.size() > 0){
                String caloriesStr = trElements.get(1).select("td").first().html().replaceAll("kcal", "");
                if(caloriesStr != null){
                    calories = new Integer(caloriesStr.trim());
                }
            }
        }catch (Exception e) {
            throw new SainsburyGroceryServiceException(
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_CALORIES,
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_CALORIES_MSG);
        }
        return calories;
    }


    private String getDescription(Document productDetailsPageDoc) throws Exception {
        String description = "";
        try{

            Elements elements = productDetailsPageDoc.select("div.productText p");
             if(null != elements)
             {
                 Element element = elements.first();
                 if(null != element)
                 {
                     description = element.text();
                 }
             }

        }catch (Exception e) {
            throw new SainsburyGroceryServiceException(
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_DESC,
                    Constants.ERRO_CODE_FAILED_TO_RETRIEVE_DESC_MSG);
        }
        return description;
    }
}
