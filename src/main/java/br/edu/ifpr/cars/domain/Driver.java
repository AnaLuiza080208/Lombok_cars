package br.edu.ifpr.cars.domain;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "É necessário digitar um nome")
    @Size(min = 2, max=50, message = "Tamanho deve ser entre 3 e 50 caracteres")
    String name;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve estar em um formato válido.")
    String email;

    @NotBlank(message = "O cpf é obrigatório.")
    @CPF(message = "O CPF deve ser válido.")
    String cpf;

    LocalDate birthDate;

    public Driver() {
    }

    public Driver(Long id, String name, String email, String cpf, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }
}
