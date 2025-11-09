package com.build.currency_converter.service;

import com.build.currency_converter.entity.ConversionHistory;
import com.build.currency_converter.entity.User;
import com.build.currency_converter.repository.ConversionHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ConversionServiceTest {

    @Mock
    private ConversionHistoryRepository repository;

    @Mock
    private RestTemplate restTemplate;

    private ConversionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new ConversionService(repository, restTemplate);
    }

    @Test
    void convertCurrency_Success() {
        ConversionService.ExchangeRateResponse response = new ConversionService.ExchangeRateResponse();
        response.setRates(Map.of("BRL", 5.5));

        when(restTemplate.getForObject(anyString(), ConversionService.ExchangeRateResponse.class)).thenReturn(response);

        // Simule um usuário autenticado (em testes reais, use SecurityContextMock)
        // Para simplificar, assuma o método funciona

        ConversionHistory result = service.convertCurrency("USD", "BRL", 100.0);

        assertEquals(550.0, result.getConvertedAmount());
        // Explicação: Verifica se a conversão calcula corretamente 100 * 5.5 = 550.
    }
}