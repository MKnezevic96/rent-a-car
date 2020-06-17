package com.rent_a_car.agentski_bekend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class/*, HibernateJpaAutoConfiguration.class*/})
@RestController
//@EntityScan("model")
//@EnableJpaRepositories(basePackages = "repository")
@SpringBootApplication//(scanBasePackages = {"model", "service", "repository", "controller"})
public class AgentskiBekendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentskiBekendApplication.class, args);
	}

	private static final Logger LOGGER = LogManager.getLogger(AgentskiBekendApplication.class.getName());

	@RequestMapping("/health")
	public String sayHello(){
//		Person percy = new Person ();
//		percy.setFirstName("Percy");
//		percy.setLastName("the Person");
//		People people = new People();
		String retVal = "";
//		for (Person p : people.getAllThePeople()) {
//			retVal += p.getFirstName();

//		}
		return "Hello from agent backend!" + " " + retVal;
	}

}
