package br.edu.ifpr.cars.validate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImparValidator implements ConstraintValidator<Impar, Integer> {

    @Override
    public boolean isValid(Integer value,ConstraintValidatorContext context) {
        return value % 2 != 0; 
    }
}