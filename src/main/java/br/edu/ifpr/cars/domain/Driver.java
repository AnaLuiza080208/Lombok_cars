package br.edu.ifpr.cars.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// =====================================================
// =============== CLASSE PRINCIPAL DRIVER ==============
// =====================================================
@Data
@Entity
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "É necessário digitar um nome.")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
    String name;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve estar em um formato válido.")
    String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "O CPF deve ser válido.")
    String cpf;

    @Impar
    LocalDate birthDate;

    @PlacaValida
    String placa;

    @CNHValida
    String cnh;

    @AnoFabricacaoValido
    int anoCarro;

    @SemPalavrasOfensivas
    String comentario;

    public Driver() {}

    public Driver(Long id, String name, String email, String cpf, LocalDate birthDate,
                  String placa, String cnh, int anoCarro, String comentario) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.placa = placa;
        this.cnh = cnh;
        this.anoCarro = anoCarro;
        this.comentario = comentario;
    }

    @Documented
    @Constraint(validatedBy = ImparValidator.class)
    @Target({ java.lang.annotation.ElementType.FIELD })
    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface Impar {
        String message() default "O número não é ímpar.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class ImparValidator implements ConstraintValidator<Impar, LocalDate> {
        @Override
        public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
            if (value == null) return true;
            return value.getDayOfMonth() % 2 != 0;
        }
    }

    @Documented
    @Constraint(validatedBy = PlacaValidaValidator.class)
    @Target({ java.lang.annotation.ElementType.FIELD })
    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface PlacaValida {
        String message() default "A placa deve seguir o formato Mercosul (ex: ABC1D23).";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class PlacaValidaValidator implements ConstraintValidator<PlacaValida, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.isBlank()) return false;
            return value.matches("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
        }
    }

    @Documented
    @Constraint(validatedBy = CNHValidaValidator.class)
    @Target({ java.lang.annotation.ElementType.FIELD })
    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface CNHValida {
        String message() default "A CNH deve conter exatamente 11 dígitos numéricos.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class CNHValidaValidator implements ConstraintValidator<CNHValida, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.isBlank()) return false;
            return value.matches("\\d{11}");
        }
    }

    @Documented
    @Constraint(validatedBy = AnoFabricacaoValidoValidator.class)
    @Target({ java.lang.annotation.ElementType.FIELD })
    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface AnoFabricacaoValido {
        String message() default "Ano de fabricação inválido. Deve ser entre 1886 e o ano atual.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class AnoFabricacaoValidoValidator implements ConstraintValidator<AnoFabricacaoValido, Integer> {
        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            if (value == null) return false;
            int anoAtual = LocalDate.now().getYear();
            return value >= 1886 && value <= anoAtual;
        }
    }

    @Documented
    @Constraint(validatedBy = SemPalavrasOfensivasValidator.class)
    @Target({ java.lang.annotation.ElementType.FIELD })
    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface SemPalavrasOfensivas {
        String message() default "O comentário contém palavras ofensivas.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class SemPalavrasOfensivasValidator implements ConstraintValidator<SemPalavrasOfensivas, String> {
        private final List<String> palavrasProibidas = Arrays.asList("burro", "idiota", "lixo");

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.isBlank()) return true;
            String texto = value.toLowerCase();
            for (String palavra : palavrasProibidas) {
                if (texto.contains(palavra)) {
                    return false;
                }
            }
            return true;
        }
    }
}
