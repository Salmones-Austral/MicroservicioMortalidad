package cl.SalmonesAustral.Mortalidad.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class JaulaClient {
    
    private final WebClient webClient;

    public JaulaClient(WebClient.Builder builder) {
        // Apuntamos al microservicio de Jaulas (Puerto 8081)
        this.webClient = builder.baseUrl("http://localhost:8081/api/v1/jaulas").build();
    }

    public boolean existeJaula(int jaulaId) {
        try {
            System.out.println("Buscando jaula ID " + jaulaId + " en puerto 8081...");
            
            webClient.get()
                    .uri("/{id}", jaulaId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                    
            System.out.println("¡Jaula " + jaulaId + " encontrada!");
            return true;
            
        } catch (WebClientResponseException.NotFound e) {
            System.out.println("La jaula " + jaulaId + " no existe (Error 404).");
            return false;
            
        } catch (Exception e) {
            System.err.println("🔴 ERROR DE CONEXIÓN CON MICROSERVICIO JAULAS 🔴: " + e.getMessage());
            return false;
        }
    }
}