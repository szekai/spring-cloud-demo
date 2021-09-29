package com.szekai.orderService.service;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.SERVICE_UNAVAILABLE;
import static io.netty.handler.codec.http.HttpResponseStatus.UNAUTHORIZED;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentWebClientTest {
    private PaymentWebClient paymentWebClient;

    private MockWebServer mockExternalService;
    @BeforeEach
    void setup() throws IOException {
        paymentWebClient = new PaymentWebClient(WebClient.builder(), "http://localhost:8090");
        mockExternalService = new MockWebServer();
        mockExternalService.start(8090);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockExternalService.shutdown();
    }

    @Test
    void givenExternalServiceReturnsError_whenGettingData_thenRetryAndReturnResponse() throws Exception {

        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setBody("stock data"));

        StepVerifier.create(paymentWebClient.getPaymentFromWebClient("admin"))
                .expectNextMatches(response -> response.equals("stock data"))
                .verifyComplete();

        verifyNumberOfGetRequests(4);
    }

    @Test
    void givenExternalServiceReturnsClientError_whenGettingData_thenNoRetry() throws Exception {

        mockExternalService.enqueue(new MockResponse().setResponseCode(UNAUTHORIZED.code()));

        StepVerifier.create(paymentWebClient.getPaymentFromWebClient("admin"))
                .expectError(WebClientResponseException.class)
                .verify();

        verifyNumberOfGetRequests(1);
    }

    @Test
    void givenExternalServiceRetryAttemptsExhausted_whenGettingData_thenRetryAndReturnError() throws Exception {

        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));
        mockExternalService.enqueue(new MockResponse().setResponseCode(SERVICE_UNAVAILABLE.code()));

        StepVerifier.create(paymentWebClient.getPaymentFromWebClient("admin"))
                .expectError(ServiceException.class)
                .verify();

        verifyNumberOfGetRequests(4);
    }

    private void verifyNumberOfGetRequests(int times) throws Exception {
        for (int i = 0; i < times; i++) {
            RecordedRequest recordedRequest = mockExternalService.takeRequest();
            assertThat(recordedRequest.getMethod()).isEqualTo("GET");
            assertThat(recordedRequest.getPath()).isEqualTo("/admin");
        }
    }
}