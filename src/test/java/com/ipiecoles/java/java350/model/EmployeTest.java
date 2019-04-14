package com.ipiecoles.java.java350.model;

import com.ipiecoles.java.java350.exception.EmployeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EmployeTest {

    @Test
    public void getNombreAnneeAncienneteNow(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now());

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNminus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().minusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(2, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNull(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(null);

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @Test
    public void getNombreAnneeAncienneteNplus2(){
        //Given
        Employe e = new Employe();
        e.setDateEmbauche(LocalDate.now().plusYears(2L));

        //When
        Integer anneeAnciennete = e.getNombreAnneeAnciennete();

        //Then
        Assertions.assertEquals(0, anneeAnciennete.intValue());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 'T12345', 0, 1.0, 1000.0",
            "1, 'T12345', 2, 0.5, 600.0",
            "1, 'T12345', 2, 1.0, 1200.0",
            "2, 'T12345', 0, 1.0, 2300.0",
            "2, 'T12345', 1, 1.0, 2400.0",
            "1, 'M12345', 0, 1.0, 1700.0",
            "1, 'M12345', 5, 1.0, 2200.0",
            "2, 'M12345', 0, 1.0, 1700.0",
            "2, 'M12345', 8, 1.0, 2500.0"
    })
    public void getPrimeAnnuelle(Integer performance, String matricule, Long nbYearsAnciennete, Double tempsPartiel, Double primeAnnuelle){
        //Given
        Employe employe = new Employe("Doe", "John", matricule, LocalDate.now().minusYears(nbYearsAnciennete), Entreprise.SALAIRE_BASE, performance, tempsPartiel);

        //When
        Double prime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertEquals(primeAnnuelle, prime);

    }


    @Test
    public void augmenterSalaireAZero() {
        //Given
        Double augmentation = 1.10D;
        Employe employe = new Employe();

        //When
        Double salaire = 0.00D;

        //Then
        employe.setSalaire(salaire);
    }


    @Test
    public void augmenterSalaireTauxZero() {
        //Given
        Double augmentation = 0.00D;
        Employe employe = new Employe();

        //When
        Double salaire = 1500.00D;

        //Then
        employe.setSalaire(salaire);
    }

    @Test
    public void augmenterSalaireTauxNegatif() {
        //Given
        Double augmentation = -0.50D;
        Employe employe = new Employe();

        //When
        Double salaire = 1500.00D;

        //Then
        employe.setSalaire(salaire);

    }

    @Test

    public void augmenterSalaireTauxPositif() throws EmployeException {
        //Given
        Double augmentation = 0.10;
        Employe employe = new Employe();

        Double salaire = 1500.00D;
        employe.setSalaire(salaire);

        //When
        employe.augmenterSalaire(augmentation);

        //Then
        assertThat(employe.getSalaire()).isEqualTo(salaire * (1.00D + augmentation));
    }



    @ParameterizedTest()
    @CsvSource({
            "2019-08-06, 8",
            "2021-10-21, 11",
            "2022-10-09, 10",
            "2032-08-25, 12",
    })

    void testGetNbRtt(LocalDate date, Integer nbRtt){

        //Given
        //Fonctionne sans le détail de l'employe
        Employe employe = new Employe("Doe",
                                "John",
                                "M12345",
                                LocalDate.now().minusYears(1),
                                Entreprise.SALAIRE_BASE,
                                Entreprise.PERFORMANCE_BASE,
                                1.0);

        Integer nbRttEmploye;

        //When
        nbRttEmploye = employe.getNbRtt(date);

        //then
        Assertions.assertEquals(nbRtt, nbRttEmploye);
    }

}