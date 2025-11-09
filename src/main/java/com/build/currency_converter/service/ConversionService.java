package com.build.currency_converter.service;

import com.build.currency_converter.entity.ConversionHistory;
import com.build.currency_converter.entity.User;
import com.build.currency_converter.repository.ConversionHistoryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service

public class ConversionService {
    private final ConversionHistoryRepository conversionHistoryRepository;
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public ConversionService(ConversionHistoryRepository conversionHistoryRepository, RestTemplate restTemplate) {
        this.conversionHistoryRepository = conversionHistoryRepository;
        this.restTemplate = restTemplate;
    }

    public ConversionHistory convertCurrency(String sourceCurrency, String targetCurrency, Double amount) {
        // Obtém a taxa de câmbio da API
        String url = API_URL + sourceCurrency;
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        if (response == null || response.getRates() == null) {
            throw new IllegalStateException("Erro ao obter taxas de câmbio.");
        }
        Double exchangeRate = response.getRates().get(targetCurrency);
        if (exchangeRate == null) {
            throw new IllegalArgumentException("Moeda de destino não suportada.");
        }

        // Calcula o valor convertido
        Double convertedAmount = amount * exchangeRate;

        // Obtém o usuário autenticado
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Cria e salva o histórico
        ConversionHistory history = new ConversionHistory();
        history.setUser(user);
        history.setSourceCurrency(sourceCurrency);
        history.setTargetCurrency(targetCurrency);
        history.setAmount(amount);
        history.setConvertedAmount(convertedAmount);
        history.setExchangeRate(exchangeRate);
        conversionHistoryRepository.save(history);

        return history;
    }

    public List<ConversionHistory> getUserHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return conversionHistoryRepository.findTop10ByUserOrderByConversionDateDesc(user);
    }

    // Classe auxiliar para deserializar a resposta da API
    public static class ExchangeRateResponse {
        private String base;
        private java.util.Map<String, Double> rates;

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public java.util.Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(java.util.Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}