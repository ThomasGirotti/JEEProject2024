package com.jeemudae.collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollectionApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(CollectionApplication.class, args);
	}

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PassportRepository passportRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		
//		System.out.println("egal? = " + (passport.hashCode() == passport2.hashCode()));
		
//		Set<Passport> passports = new HashSet<>();
//		passports.add(passport);
//		passports.add(passport2);
//		System.out.println("taille=" + passports.size());
		
		Customer remy = new Customer();
		remy.setName("remy");
		remy.setEmail("remy@cytech.fr");
		customerRepository.save(remy);
		
		Passport passport = new Passport();
		passport.setCode("P234Y546");
		passport.setOwner(remy);
		passportRepository.save(passport);
		
		Passport passport2 = new Passport();
		passport2.setCode("U518GG599");
		passport2.setOwner(remy);
		passportRepository.save(passport2);

		System.out.println("passport:" + passport);
		System.out.println("customer:"+remy);
		remy = customerRepository.findByEmail(remy.getEmail()).get();
		System.out.println("passport:" + passport);
		System.out.println("customer:"+remy);
	}

}
