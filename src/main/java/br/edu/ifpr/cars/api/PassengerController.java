package br.edu.ifpr.cars.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifpr.cars.domain.Passenger;
import br.edu.ifpr.cars.domain.PassengerRepository;

@Service
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PassengerController {

    @Autowired // injeção de dependencia
    PassengerRepository passengerRepository; //objeto instancia do repositorio

    @GetMapping("/passengers")
    public List<Passenger> listPassengers() {
        return passengerRepository.findAll();
    }

    // Definir uma Exception personalizada 
    @GetMapping("/passengers/{id}")
    public Passenger findPassenger(@PathVariable("id") Long id) {
        return passengerRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping("/passengers")
    public Passenger createPassenger(@RequestBody Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    // Update completo
    @PutMapping("/passengers/{id}")
    public Passenger fullUpdatePassenger(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
        Passenger foundPassenger = findPassenger(id);
        foundPassenger.setName(passenger.getName());
        foundPassenger.setEmail(passenger.getEmail());
        return passengerRepository.save(foundPassenger);
    }

    // Update parcial
    @PatchMapping("/passengers/{id}")
    public Passenger incrementalUpdatePassenger(@PathVariable("id") Long id, @RequestBody Passenger passenger) {
        Passenger foundPassenger = findPassenger(id);
        foundPassenger.setName(Optional.ofNullable(passenger.getName()).orElse(foundPassenger.getName()));
        foundPassenger.setEmail(Optional.ofNullable(passenger.getEmail()).orElse(foundPassenger.getEmail()));
        return passengerRepository.save(foundPassenger);
    }

    @DeleteMapping("/passengers/{id}")
    public void deletePassenger(@PathVariable("id") Long id) {
        passengerRepository.deleteById(id);
    }
}
