package com.onlineshop.theshop.typesense;

import com.onlineshop.theshop.configproperties.ConfigProperties;
import com.onlineshop.theshop.service.ProductService;
import com.onlineshop.theshop.shop.model.store.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    ProductService productService;

    @Autowired
    ConfigProperties configProperties;

    public void addCollection(){
        if(collectionExists())
            delete("/collections/products");
        Collection collection = new Collection("products","temp");
        collection.addField("name","string");
        collection.addField("productid","string", true);
        collection.addField("temp","int32");
        collection.addField("tags","string");
        postJSON("/collections", collection, Collection.class);
    }

    public void addAllProducts(){
        List<Product> products = productService.getAllProducts();
        String testProducts = "";
            for (Product product: products) {
                testProducts +="{\"name\": \""+product.getName()+"\" ,\"productid\": \""+product.getId()+"\" ,\"temp\": 1,\"tags\": \""+product.getTags().stream().collect(Collectors.joining(","))+"\"}\n";
            }
        postJSON("/collections/products/documents/import?action=create", testProducts, String.class);
    }

    public void deleteAllProducts(){
        delete("/collections/products/documents?filter_by=temp:>=0");
    }

    public void addProduct(Product product){
        String testProduct = "{\"name\": \""+product.getName()+"\" ,\"productid\": \""+product.getId()+"\" ,\"temp\": 1,\"tags\": \""+product.getTags().stream().collect(Collectors.joining(","))+"\"}";
        postJSON("/collections/products/documents", testProduct, String.class);

    }

    public void updateProduct(Product product){
        String testProduct = "{\"name\": \""+product.getName()+"\" ,\"productid\": \""+product.getId()+"\" ,\"temp\": 1,\"tags\": \""+product.getTags().stream().collect(Collectors.joining(","))+"\"}";
        postJSON("/collections/products/documents/import?action=upsert", testProduct, String.class);
    }

    public void deleteProduct(UUID productId){
        delete("/collections/products/documents?filter_by=productid:="+ productId);
    }

    public List<Product> search(String search){
        ResponseEntity<SearchResponse> response =
                getJSON("/collections/products/documents/search?q="+search+"&query_by=name,tags&sort_by=temp:asc",
                        SearchResponse.class);
        List<Product> results = new ArrayList<>();
        for (Entry entry : Objects.requireNonNull(response.getBody()).hits) {
            results.add(productService.getProductById(UUID.fromString(entry.document.productid)));
        }
        return results;
    }

    public boolean collectionExists() {
        try {
        ResponseEntity<Collection> response = communicate("/collections/products", HttpMethod.GET, false,null, Collection.class);
        return Objects.requireNonNull(response.getBody()).name.equals("products");
        }catch (HttpClientErrorException e){
            return false;
        }
    }

    public <R, H> ResponseEntity<R> communicate(String path, HttpMethod method, boolean isJSON, H content, Class<R> tClass){
        HttpHeaders httpHeaders = new HttpHeaders();
        if (isJSON)
            httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("X-TYPESENSE-API-KEY", configProperties.getTypesense().getApi().getKey());
        if (content != null) {
            HttpEntity<H> entity = new HttpEntity<>(content, httpHeaders);
            return send(path, method, entity, tClass);
        } else {
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
            return send(path, method, entity, tClass);
        }
    }

    private <R, H> ResponseEntity<R> send(String path, HttpMethod method, HttpEntity<H> entity, Class<R> tClass){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                configProperties.getTypesense().getUrl() + path,
                method,
                entity,
                tClass
        );
    }

    private <R, H> ResponseEntity<R> postJSON(String path, H content, Class<R> tClass){
        return communicate(path, HttpMethod.POST, true, content, tClass);
    }

    private <R> ResponseEntity<R> getJSON(String path, Class<R> tClass){
        return communicate(path, HttpMethod.GET, true, null, tClass);
    }

    private ResponseEntity<String> delete(String path ){
        return communicate(path, HttpMethod.DELETE, false, null, String.class);
    }
}
