package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.Endpoint;

import java.util.List;
import java.util.Optional;

public interface EndpointService {
    List<Endpoint> findAllEndpoints();

    Endpoint findEndpointByEndpointText(String endpoint_text);

    Optional<Endpoint> add(Endpoint endpoint);

    List<Endpoint> getAll();

    Optional<Endpoint> getById(Long id);

    Optional<Endpoint> update(Long id, Endpoint endpoint);

    Optional<Endpoint> delete(Long id);
}
