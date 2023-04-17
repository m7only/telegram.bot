package m7.only.groupworkbot.services.impl;

import m7.only.groupworkbot.entity.Endpoint;
import m7.only.groupworkbot.repository.EndpointRepository;
import m7.only.groupworkbot.services.EndpointService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     *
     * @return лист эндпоинтов
     */
    @Override
    public List<Endpoint> findAllEndpoints() {
        return endpointRepository.findAll();
    }

    /**
     * Поиск эндпоинта по его текстовому представлению, например "/pray"
     *
     * @param endpointText текстовое представление эндпоинта
     * @return Endpoint эндпоинт, или null, если не найден
     */
    @Override
    public Endpoint findEndpointByEndpointText(String endpointText) {
        return endpointRepository.findByEndpointText(endpointText).orElse(null);
    }

    /**
     * Сохранение нового эндпоинта через API. Проходит проверка, что текст эндпоинта начинается с "/" и состоит из одного слова
     *
     * @param endpoint сохраняемый эндпоинт
     * @return {@code Optional<Endpoint>} если сохранен, или {@code Optional.empty()}, если не пройдена проверка
     */
    @Override
    public Optional<Endpoint> add(Endpoint endpoint) {
        String endpointText = endpoint.getEndpointText();
        return endpointText.startsWith("/") || endpointText.split("\\s").length > 1
                ? Optional.of(endpointRepository.save(endpoint))
                : Optional.empty();
    }

    /**
     * Получение списка всех эндпоинтов для API.
     *
     * @return {@code List<Endpoint>}
     */
    @Override
    public List<Endpoint> getAll() {
        return endpointRepository.findAll();
    }

    /**
     * Получение эндпоинта по id для API.
     *
     * @param id идентификатор эндпоинта
     * @return {@code Optional<Endpoint>} если найден, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Endpoint> getById(Long id) {
        return endpointRepository.findById(id);
    }

    /**
     * Редактирование эндпоинта по id для API. Проходит проверка на существование эндпоинта.
     *
     * @param id       идентификатор эндпоинта
     * @param endpoint тело редактируемого эндпоинта
     * @return {@code Optional<Endpoint>} если изменен, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Endpoint> update(Long id, Endpoint endpoint) {
        return endpointRepository.findById(id).isPresent()
                ? Optional.of(endpointRepository.save(endpoint))
                : Optional.empty();
    }

    /**
     * Удаление эндпоинта по id для API. Проходит проверка на существование эндпоинта.
     *
     * @param id идентификатор эндпоинта
     * @return {@code Optional<Endpoint>} если удален, или {@code Optional.empty()}, если не найден
     */
    @Override
    public Optional<Endpoint> delete(Long id) {
        Optional<Endpoint> optionalEndpoint = endpointRepository.findById(id);
        if (endpointRepository.findById(id).isPresent()) {
            endpointRepository.deleteById(id);
            return optionalEndpoint;
        } else {
            return Optional.empty();
        }
    }
}
