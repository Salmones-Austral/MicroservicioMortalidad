package cl.SalmonesAustral.Mortalidad.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AlertaClient {

    private final WebClient webClient;

    public AlertaClient(WebClient.Builder builder) {
        // Apuntamos al microservicio de Alertas (Puerto 8083)
        this.webClient = builder.baseUrl("http://localhost:8083/api/v1/alertas").build();
    }

    public void notificarAlerta(Integer mortalidadId, int jaulaId, double promedio) {
        try {
            System.out.println("📡 Comunicando con microservicio de Alertas...");
            
            // Usamos post() y mandamos los parámetros por query
            webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/generar")
                            .queryParam("mortalidadId", mortalidadId)
                            .queryParam("jaulaId", jaulaId)
                            .queryParam("porcentaje", promedio)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Esperamos que se envíe
                    
            System.out.println("✅ Notificación de alerta enviada exitosamente.");
        } catch (Exception e) {
            System.err.println("🔴 ERROR al comunicar con MS Alertas: " + e.getMessage());
        }
    }
}