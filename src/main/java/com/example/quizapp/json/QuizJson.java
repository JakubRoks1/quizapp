package com.example.quizapp.json;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * Do wszystkich pododawaj Walidacje lub JsonView
 * 1) Tworzenie quizu
 * 2) Modyfikacja danych quizu - jak nie podam pola to zostaje nieruszone
 * 3) Usuwanie quizu (do sprawdzenia dalsze zależności - w przyszłości usuwamy tylko pusty quiz) +
 * 4) Dodawanie pytania do quizu [!] - same pytania, dodawanie do quizu to podanie nr quizu i numeru istniejeacego pytania (łączymy)
 * 5) Dodawanie pytania do quizu (kaskada) - podaję nr quizu, i obiekt pytania
 * 6) Odpięcie pytania od quizu (nie usuwamy pytania)
 *
 * pliki json ponumerowane, 01.., 02..., 03..., 04.., 05..., 06...
 */
@Data
public class QuizJson {

    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @NotEmpty
    private String quizCategory;

    @Length(min = 20, max = 40, groups = ValidationGroups.Output.class)
    private String description;

    public record ValidationGroups() {
        public interface Input {};
        public interface Output {};
    }
}
