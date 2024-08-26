package br.ufscar.dc.dsw;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.ILocadoraDAO;
import br.ufscar.dc.dsw.dao.ILocacaoDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Locadora;

@SpringBootApplication(scanBasePackages = "br.ufscar.dc.dsw")
public class T2Dsw1Application {

	public static void main(String[] args) {
		SpringApplication.run(T2Dsw1Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(IClienteDAO clienteDAO, ILocadoraDAO locadoraDAO, ILocacaoDAO locacaoDAO, BCryptPasswordEncoder encoder) {
		return (args) -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-DD");

			//Criando Usuários
			Cliente c1 = new Cliente();
			c1.setUsername("eugenio@email.com");
			c1.setPassword(encoder.encode("user"));
			c1.setRole("ROLE_CLIENTE");
			c1.setCpf("35247297890");
			c1.setNome("Eugênio Akinori Kisi Nishimiya");
			c1.setTelefone("17996174868");
			c1.setSexo("M");
			c1.setDataNascimento(new Date(sdf.parse("2002-09-26").getTime()));
			c1.setIsAdmin(false);
			clienteDAO.save(c1);
			
			Cliente c2 = new Cliente();
			c2.setUsername("fulana@email.com");
			c2.setPassword(encoder.encode("user"));
			c2.setRole("ROLE_CLIENTE");
			c2.setCpf("12345678910");
			c2.setNome("Fulana da Silva");
			c2.setTelefone("11111111111");
			c2.setSexo("F");
			c2.setDataNascimento(new Date(sdf.parse("2003-03-03").getTime()));
			c2.setIsAdmin(true);
			clienteDAO.save(c2);
			
			Cliente c3 = new Cliente();
			c3.setUsername("admin@admin.com");
			c3.setPassword(encoder.encode("admin"));
			c3.setRole("ROLE_ADMIN");
			c3.setCpf("99999999999");
			c3.setNome("Admin");
			c3.setTelefone("99999999999");
			c3.setSexo("M");
			c3.setDataNascimento(new Date(sdf.parse("2001-01-01").getTime()));
			c3.setIsAdmin(true);
			clienteDAO.save(c3);
			
			//Criando Locadoras
			Locadora l1 = new Locadora();
			l1.setUsername("locadora1@email.com");
			l1.setPassword(encoder.encode("locadora"));
			l1.setRole("ROLE_LOCADORA");
			l1.setCnpj("11111111000110");
			l1.setNome("Locadora A");
			l1.setCidade("São Carlos");
			l1.setIsAdmin(false);
			locadoraDAO.save(l1);

			Locadora l2 = new Locadora();
			l2.setUsername("locadora2@email.com");
			l2.setPassword(encoder.encode("locadora"));
			l2.setRole("ROLE_LOCADORA");
			l2.setCnpj("22222222000110");
			l2.setNome("Locadora B");
			l2.setCidade("Bauru");
			l2.setIsAdmin(false);
			locadoraDAO.save(l2);

			Locadora l3 = new Locadora();
			l3.setUsername("locadora3@email.com");
			l3.setPassword(encoder.encode("locadora"));
			l3.setRole("ROLE_LOCADORA");
			l3.setCnpj("33333333000110");
			l3.setNome("Locadora C");
			l3.setCidade("São José do Rio Preto");
			l3.setIsAdmin(false);
			locadoraDAO.save(l3);


			Locadora l4 = new Locadora();
			l4.setUsername("locadora4@email.com");
			l4.setPassword(encoder.encode("locadora"));
			l4.setRole("ROLE_LOCADORA");
			l4.setCnpj("44444444000110");
			l4.setNome("Locadora D");
			l4.setCidade("São Carlos");
			l4.setIsAdmin(false);
			locadoraDAO.save(l4);


			Locadora l5 = new Locadora();
			l5.setUsername("locadora5@email.com");
			l5.setPassword(encoder.encode("locadora"));
			l5.setRole("ROLE_LOCADORA");
			l5.setCnpj("55555555000110");
			l5.setNome("Locadora E");
			l5.setCidade("São Carlos");
			l5.setIsAdmin(false);
			locadoraDAO.save(l5);

			Locadora l6 = new Locadora();
			l6.setUsername("locadora6@email.com");
			l6.setPassword(encoder.encode("locadora"));
			l6.setRole("ROLE_LOCADORA");
			l6.setCnpj("66666666000111");
			l6.setNome("Locadora F");
			l6.setCidade("Bauru");
			l6.setIsAdmin(false);
			locadoraDAO.save(l6);

			//Criando Locacoes Existentes
			Locacao L1 = new Locacao();
			L1.setCliente(c1);
			L1.setLocadora(l1);
			L1.setDia(new Date(sdf.parse("2024-08-18").getTime()));
			L1.setHorario(Time.valueOf("18:00:00"));
			locacaoDAO.save(L1);

			Locacao L2 = new Locacao();
			L2.setCliente(c1);
			L2.setLocadora(l2);
			L2.setDia(new Date(sdf.parse("2024-08-18").getTime()));
			L2.setHorario(Time.valueOf("14:00:00"));
			locacaoDAO.save(L2);

			Locacao L3 = new Locacao();
			L3.setCliente(c3);;
			L3.setLocadora(l1);
			L3.setDia(new Date(sdf.parse("2023-12-30").getTime()));
			L3.setHorario(Time.valueOf("15:00:00"));
			locacaoDAO.save(L3);
		};
	}

}
