package com.sorapp.jowncms.jowncms;

import com.sorapp.jowncms.jowncms.DatabaseRepository.DBRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JowncmsApplication {

	public static void main(String[] args)
	{

		//Database start
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					new DBRepository();
				}
			}
		).start();
		//Database end

		SpringApplication.run(JowncmsApplication.class, args);
	}

}
