package factory;

import service.SalaruService;

public class SalaruServiceFactory {
    public static SalaruService createSalaruService() {
        return new SalaruService();
    }
}
