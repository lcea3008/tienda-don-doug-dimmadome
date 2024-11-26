package dimadon.business.tienda_don_doug_dimmadome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TiendaDonDougDimmadomeApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().load();

		// Imprimir las variables para ver si se cargan correctamente
		// System.setProperty("DB_URL", dotenv.get("DB_URL"));
		// System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		// System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		// System.setProperty("RENIEC_API_URL", dotenv.get("RENIEC_API_URL"));
		// System.setProperty("RENIEC_API_TOKEN", dotenv.get("RENIEC_API_TOKEN"));
		System.out.println("DB_URL: " + dotenv.get("DB_URL"));
		System.out.println("DB_USERNAME: " + dotenv.get("DB_USERNAME"));
		System.out.println("DB_PASSWORD: " + dotenv.get("DB_PASSWORD"));
		System.out.println("RENIEC_API_URL: " + dotenv.get("RENIEC_API_URL"));
		System.out.println("RENIEC_API_TOKEN: " + dotenv.get("RENIEC_API_TOKEN"));
		System.out.println("Clave");
		System.out.println(new BCryptPasswordEncoder().encode("edwin1212"));
		SpringApplication.run(TiendaDonDougDimmadomeApplication.class, args);

	}

}
