package com.example.quizapp.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * Do wszystkich pododawaj Walidacje lub JsonView [x]
 * 1) Tworzenie quizu [x]
 * 2) Modyfikacja danych quizu - jak nie podam pola to zostaje nieruszone [x] PATCH
 * 3) Usuwanie quizu (do sprawdzenia dalsze zależności - w przyszłości usuwamy tylko pusty quiz) +
 * 4) Dodawanie pytania do quizu [!] - same pytania, dodawanie do quizu to podanie nr quizu i numeru istniejeacego pytania (łączymy) [?]
 * nowa praca -> get na quizie zwraca obiekt quizu ze wszystkimi pytaniami (jak nie ma pytan, to nie pojawia sie pole questions) - Jackson Annotations
* przeanalizowane zajęć
 * do poprawienia znowu jsony

 * 5) Dodawanie pytania do quizu (kaskada) - podaję nr quizu, i obiekt pytania
 * 6) Odpięcie pytania od quizu (nie usuwamy pytania)
 *
 * pliki json ponumerowane, 01.., 02..., 03..., 04.., 05..., 06... [x]
 */
@Data
@JsonIgnoreProperties(value = { "questions" }, allowGetters = true)
public class QuizJson {

    @JsonView(Views.IdOnly.class)
    @Null(groups = ValidationGroups.Input.class)
    private Long id;

    @JsonView(Views.Input.class)
    @NotEmpty
    private String quizCategory;

    @JsonView(Views.Input.class)
    @Length(min = 20, max = 40, groups = ValidationGroups.Output.class)
    private String description;

    public interface Views {
        interface IdOnly {}
        interface Input {}
        interface Output {}
    }

    public record ValidationGroups() {
        public interface Input {};
        public interface Output {};
    }
}
