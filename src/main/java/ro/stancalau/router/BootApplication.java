package ro.stancalau.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ro.stancalau.router")
public class BootApplication implements CommandLineRunner {

    @Autowired
    public BootApplication() {
    }

    @Override
    public void run(String... args) {
    }

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
