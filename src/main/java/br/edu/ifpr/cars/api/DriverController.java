package br.edu.ifpr.cars.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifpr.cars.domain.Driver;
import br.edu.ifpr.cars.domain.DriverRepository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@Service
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class DriverController {

    @Autowired// injeção de dependencia
    DriverRepository driverRepository; //objeto instancia do repositorio

    @GetMapping("/drivers")
   public List<Driver> listDrivers() {
        return driverRepository.findAll();
    }

    //definir uma Exception personalizada 
    @GetMapping("/drivers/{id}")
    public Driver findDrivers(@PathVariable("id") Long id) {
         return driverRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
         );
     }

     @PostMapping("/drivers")
     public Driver createDriver(@RequestBody Driver driver){
        return driverRepository.save(driver);
     }

     //update
     @PutMapping("/drivers{id}")
     public Driver fullUpdateDriver(@PathVariable("id") Long id, @RequestBody Driver driver){
        Driver foundDriver = findDrivers(id);
        foundDriver.setName(driver.getName());
        foundDriver.setBirthDate(driver.getBirthDate());
        return driverRepository.save(foundDriver);

     }
}
