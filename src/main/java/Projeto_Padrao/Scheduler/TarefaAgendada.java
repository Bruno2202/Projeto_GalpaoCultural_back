package Projeto_Padrao.Scheduler;
import com.google.api.client.util.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;

@Component
public class TarefaAgendada {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${API_URL}")
    private String apiUrl;

    @Scheduled(fixedRate = 300000)
    public void executarTarefa() {
        try {
            restTemplate.getForObject(apiUrl + "/health", String.class);
            System.out.println("Ping enviado com sucesso para o servidor - " + LocalTime.now());
        } catch (Exception e) {
            System.err.println("Falha ao enviar ping: " + e.getMessage());
        }
    }
}
