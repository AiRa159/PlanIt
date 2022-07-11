package cz.cvut.fel.ear.planIt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"cz.cvut.fel.ear.planIt.*"})
public class PlanItApplication {
    public static void main(String[] args){
        SpringApplication.run(PlanItApplication.class, args);
    }
}
