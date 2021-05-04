package com.example.demo.restcontroller;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/test")
public class HelloController {

    private PlayerRepository playerRepository;

    public HelloController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok().body("hellou mai frent end velkom tu mai iutub ceanel!");
    }

    @RolesAllowed("user")
    @GetMapping("/user")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.ok().body("You should see this with user role only...");
    }

    @RolesAllowed("admin")
    @GetMapping("/player")
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = this.playerRepository.findAll();
        return ResponseEntity.ok().body(players);
    }

    @PostMapping("/player")
    public ResponseEntity<Player> createPlayers(@RequestBody Player player) {
        Player savedPlayer = this.playerRepository.save(player);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("{/id}")
                                                    .buildAndExpand(savedPlayer.getId())
                                                    .toUri();

        return ResponseEntity.created(location).body(savedPlayer);
    }

}











