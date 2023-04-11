package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.repository.EndpointRepository;
import m7.only.groupworkbot.services.EndpointService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Сервисный класс для работы с сущностью {@link Endpoint}
 */
@Service
public class EndpointServiceImpl implements EndpointService {
    private final EndpointRepository endpointRepository;
    private final List<Endpoint> endpoints;

    public EndpointServiceImpl(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
        this.endpoints = endpointRepository.findAll();
    }

    @Override
    public List<Endpoint> findAllEndpoints() {
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public Endpoint findEndpointByEndpointText(String endpoint_text) {
        return endpointRepository.findByEndpointText(endpoint_text).orElse(null);
    }
}
