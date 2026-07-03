package cl.SalmonesAustral.Mortalidad.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AlertaClient {

    private final WebClient webClient;

    public AlertaClient(WebClient.Builder builder, 
                        @Value("${alertas.service.url:http://localhost:8083/api/v1/alertas}") String alertasUrl) {
        this.webClient = builder.baseUrl(alertasUrl).build();
    }

    public void notificarAlerta(Integer intMortalidadId, int jaulaId, double porcentaje) {
        try {
            // Convertimos el Integer a Long porque Alertas espera un Long en el RequestParam
            Long mortalidadId = intMortalidadId.longValue();

            System.out.println("Enviando petición a Alertas para Jaula " + jaulaId + " con porcentaje " + porcentaje);

            webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/generar")
                            .queryParam("mortalidadId", mortalidadId)
                            .queryParam("jaulaId", jaulaId)
                            .queryParam("porcentaje", porcentaje)
                            .build())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block(); // Esperamos que Alertas confirme la recepción

            System.out.println("✅ ¡Alerta generada y procesada correctamente!");
        } catch (Exception e) {
            System.err.println("🔴 Falló la comunicación con el microservicio de Alertas: " + e.getMessage());
        }
    }
}