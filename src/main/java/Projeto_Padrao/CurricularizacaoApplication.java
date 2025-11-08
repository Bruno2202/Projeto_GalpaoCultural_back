package Projeto_Padrao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CurricularizacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurricularizacaoApplication.class, args);
	}

}
