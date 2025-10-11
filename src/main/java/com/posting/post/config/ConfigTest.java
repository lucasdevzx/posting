package com.posting.post.config;

import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.Post;
import com.posting.post.entities.User;
import com.posting.post.repositories.PostRepository;
import com.posting.post.repositories.UserRepository;

@Configuration
@Profile("test")
public class ConfigTest implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Lucas", "lucas@gmail.com", "2049");
        User u2 = new User(null, "Maria", "maria@gmail.com", "1520");
        User u3 = new User(null, "Ana", "ana@gmail.com", "4020");
        userRepository.saveAll(Arrays.asList(u1, u2, u3));

        AdressUser ad1 =
                new AdressUser(null, "Brazil", "Rio", "Campos", "Estrada", "Coronel", 307, u1);
        AdressUser ad2 = new AdressUser(null, "United States", "Oregon", "Orlando", "Neska",
                "Clinton", 227, u2);
        AdressUser ad3 = new AdressUser(null, "Brazil", "Minas Gerais", "Belo Horizonte", "Barroso",
                "Garimpo", 190, u3);
        u1.setAdressUser(ad1);
        u2.setAdressUser(ad2);
        u3.setAdressUser(ad3);
        userRepository.saveAll(Arrays.asList(u1, u2, u3));

        Post p1 = new Post(null, "lucas", "BOA", LocalDate.now());
        postRepository.save(p1);
    }
}
