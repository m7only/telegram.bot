package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.repository.EndpointRepository;
import m7.only.groupworkbot.services.EndpointService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для работы с сущностью {@link Endpoint}
 */
@Service
public class EndpointServiceImpl implements EndpointService {
    private final EndpointRepository endpointRepository;

    public EndpointServiceImpl(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    /**
     * Получение всех энпоинтов.
     * @return лист эндпоинтов
     */
    @Override
    public List<Endpoint> findAllEndpoints() {
        return endpointRepository.findAll();
    }

    /**
     * Поиск эндпоинта по его текстовому представлению, например "/pray"
     * @param endpointText текстовое представление эндпоинта
     * @return Endpoint эндпоинт, или null, если не найден
     */
    @Override
    public Endpoint findEndpointByEndpointText(String endpointText) {
        return endpointRepository.findByEndpointText(endpointText).orElse(null);
    }
}
