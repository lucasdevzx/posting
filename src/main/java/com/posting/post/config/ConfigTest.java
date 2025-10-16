package com.posting.post.config;

import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.posting.post.entities.AdressUser;
import com.posting.post.entities.Category;
import com.posting.post.entities.Coment;
import com.posting.post.entities.Post;
import com.posting.post.entities.User;
import com.posting.post.repositories.CategoryRepository;
import com.posting.post.repositories.ComentRepository;
import com.posting.post.repositories.PostRepository;
import com.posting.post.repositories.UserRepository;

@Configuration
@Profile("test")
public class ConfigTest implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ComentRepository comentRepository;

    @Override
    public void run(String... args) throws Exception {

        User u1 = new User(null, "Lucas", "lucas@gmail.com", "Lucas_fullpvp123@");
        User u2 = new User(null, "Maria", "maria@gmail.com", "Lucas_fullpvp123@");
        User u3 = new User(null, "Ana", "ana@gmail.com", "Lucas_fullpvp123@");
        userRepository.saveAll(Arrays.asList(u1, u2, u3));

        AdressUser ad1 = new AdressUser(null, "Brazil", "Rio", "Campos", "Estrada", "Coronel", 307, u1);
        AdressUser ad2 = new AdressUser(null, "United States", "Oregon", "Orlando", "Neska","Clinton", 227, u2);
        AdressUser ad3 = new AdressUser(null, "Brazil", "Minas Gerais", "Belo Horizonte", "Barroso","Garimpo", 190, u3);
        u1.setAdressUser(ad1);
        u2.setAdressUser(ad2);
        u3.setAdressUser(ad3);
        userRepository.saveAll(Arrays.asList(u1, u2, u3));

        Category ca1 = new Category(null, "Anime");
        categoryRepository.save(ca1);


        Post p1 = new Post(null, "lucas", "BOA", LocalDate.now(),u2);
        Post p2 = new Post(null, "Sousou No Frieren", "Assisti um anime muito bom!", LocalDate.now(),u1);
        postRepository.saveAll(Arrays.asList(p1, p2));
        p2.getCategorys().add(ca1);
        postRepository.save(p2);

        Coment co1 = new Coment(p2, u3, "Realmente, anime muito bom!", LocalDate.now());
        p2.getComents().add(co1);
        comentRepository.save(co1);
    }
}
