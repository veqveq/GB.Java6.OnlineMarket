package com.veqveq.onlinemarket.ws.endpoints;

import com.veqveq.onlinemarket.services.ProductService;
import com.veqveq.onlinemarket.ws.soap.products.GetAllProductsRequest;
import com.veqveq.onlinemarket.ws.soap.products.GetAllProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.veqveq.com/onlinemarket/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getStudentByName(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAll().forEach(response.getProducts()::add);
        return response;
    }
}
