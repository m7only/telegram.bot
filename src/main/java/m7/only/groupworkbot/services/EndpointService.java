package m7.only.groupworkbot.services;

import m7.only.groupworkbot.entity.Endpoint;

import java.util.List;

public interface EndpointService {
    List<Endpoint> findAllEndpoints();

    Endpoint findEndpointByEndpointText(String endpoint_text);
}
