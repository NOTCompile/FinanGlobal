package banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class BancoEmpApplication {

    public static void main(String[] args) {

        SpringApplication.run(BancoEmpApplication.class, args);
        System.out.println("\n===========================================");
        System.out.println("Sistema Empeños");
        System.out.println("API disponible en: http://localhost:8080/api");
        System.out.println("Base de datos: PostgreSQL");
        System.out.println("===========================================\n");
    }

}