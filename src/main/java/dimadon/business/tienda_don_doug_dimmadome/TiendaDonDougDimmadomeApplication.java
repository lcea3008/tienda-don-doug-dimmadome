package dimadon.business.tienda_don_doug_dimmadome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class TiendaDonDougDimmadomeApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().load();

		// Imprimir las variables para ver si se cargan correctamente
		System.out.println("DB_URL: " + dotenv.get("DB_URL"));
		System.out.println("DB_USERNAME: " + dotenv.get("DB_USERNAME"));
		System.out.println("DB_PASSWORD: " + dotenv.get("DB_PASSWORD"));
		System.out.println("RENIEC_API_URL: " + dotenv.get("RENIEC_API_URL"));
		System.out.println("RENIEC_API_TOKEN: " + dotenv.get("RENIEC_API_TOKEN"));
		SpringApplication.run(TiendaDonDougDimmadomeApplication.class, args);

	}

}
