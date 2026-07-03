package cl.SalmonesAustral.Mortalidad.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class JaulaClient {

    private final WebClient webClient;

    // Le decimos que lea la variable JAULAS_SERVICE_URL, o que use localhost por defecto si estás en tu PC
    public JaulaClient(WebClient.Builder builder, 
                       @Value("${jaulas.service.url:http://localhost:8081/api/v1/jaulas}") String jaulasUrl) {
        this.webClient = builder.baseUrl(jaulasUrl).build();
    }

    public boolean existeJaula(int jaulaId) {
        try {
            System.out.println("🔎 Buscando si existe la jaula ID " + jaulaId + " en el microservicio de Jaulas...");
            
            webClient.get()
                    .uri("/{id}", jaulaId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
                    
            System.out.println("✅ ¡Jaula " + jaulaId + " encontrada exitosamente!");
            return true;
            
        } catch (WebClientResponseException.NotFound e) {
            System.out.println("🟡 La jaula " + jaulaId + " no existe (Error 404).");
            return false;
        } catch (Exception e) {
            System.err.println("🔴 Error de conexión con el microservicio de Jaulas: " + e.getMessage());
            return false;
        }
    }
}