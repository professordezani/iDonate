package br.com.idonate.iDonate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class IDonateApplication {

	public static void main(String[] args) {
		SpringApplication.run(IDonateApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("12345678"));
	}

}
