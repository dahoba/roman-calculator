package xyz.dahoba;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class RomanCalculatorApplication implements CommandLineRunner {

    @Bean
    public RomanCalculator calculator() {
        return new RomanCalculator();
    }

    public static void main(String[] args) {
        SpringApplication.run(RomanCalculatorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length > 0) {
            if ("-f".equals(args[0]) && args[1] != null) {
                System.out.println("file: " + args[1]);
                File file = FileUtils.getFile(args[1]);
                if (!file.exists()) {
                    throw new FileNotFoundException("File not found!");
                }
                List<String> lines = FileUtils.readLines(file, "ISO-8859-1");
                for (String line : lines) {
                    calculator().calculate(line);
                }
            } else {
                calculator().calculate(args[0]);
            }
        }else{
            System.out.println("Usage: java -jar roman-calculator-1.0.20170330.jar arguments");
            System.out.println(" ");
            System.out.println("Arguments: ");
            System.out.println("roman numeral equation i.e.: XIV+LX");
            System.out.println("-f file i.e.: -f input.txt");
        }
    }


}
