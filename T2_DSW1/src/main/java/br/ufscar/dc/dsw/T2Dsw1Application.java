package br.ufscar.dc.dsw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.dao.ILocadoraDAO;
import br.ufscar.dc.dsw.domain.Locadora;

@SpringBootApplication(scanBasePackages = "br.ufscar.dc.dsw")
public class T2Dsw1Application {

	public static void main(String[] args) {
		SpringApplication.run(T2Dsw1Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(ILocadoraDAO locadoraDAO) {
		return (args) -> {


		};
	}

}
