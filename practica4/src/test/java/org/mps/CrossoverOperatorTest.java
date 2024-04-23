/*  Westerhof Rodríguez Guillermo Alejandro
    Rueda Cabrera Pedro 
*/
package org.mps;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;

public class CrossoverOperatorTest {
    @Test
    @DisplayName("Inserto parent1 como nulo y me devuelve EvolutionaryAlgorithmException")
    public void crossover_Parent1Nullo_DevuelveEvolutionaryAlgorithmException(){
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = null;
        int [] parent2 = {1,2,3};
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, ()->cross.crossover(parent1, parent2));

    }

    @Test
    @DisplayName("Inserto parent2 como nulo y me devuelve EvolutionaryAlgorithmException")
    public void crossover_Parent2Nullo_DevuelveEvolutionaryAlgorithmException(){
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = {1,2,3};
        int [] parent2 = null;
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, ()->cross.crossover(parent1, parent2));

    }

    @Test
    @DisplayName("Inserto ambos parents como nulos y me devuelve EvolutionaryAlgorithmException")
    public void crossover_ParentsNullos_DevuelveEvolutionaryAlgorithmException(){
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = null;
        int [] parent2 = null;
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, ()->cross.crossover(parent1, parent2));

    }

    @Test
    @DisplayName("Inserto parent1 con length 0 y me devuelve EvolutionaryAlgorithmException")
    public void crossover_Parent1ConLenght0_DevuelveEvolutionaryAlgorithmException(){
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = {};
        int [] parent2 = {1,2,3};
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, ()->cross.crossover(parent1, parent2));

    }

    @Test
    @DisplayName("Inserto parents con distinto tamaño y me devuelve EvolutionaryAlgorithmException")
    public void crossover_Parent1ConLenghtDistintaDeParent2_DevuelveEvolutionaryAlgorithmException(){
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = {1,2};
        int [] parent2 = {1,2,3};
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, ()->cross.crossover(parent1, parent2));

    }

    @Test
    @DisplayName("Inserto parents validos y parent1 es distinto del primer children generado")
    public void crossover_ConParentsCorrectos_DevuelveChildren1distinto() throws EvolutionaryAlgorithmException{
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = {4,5,6};
        int [] parent2 = {1,2,3};
        //Act 
        int [][] aux = cross.crossover(parent1, parent2);
        //Assert
        assertNotEquals(parent1, aux[0]);

    }

    @Test
    @DisplayName("Inserto parents validos y parent2 es distinto del segundo children generado")
    public void crossover_ConParentsCorrectos_DevuelveChildren2distinto() throws EvolutionaryAlgorithmException{
        //Arrange
        CrossoverOperator cross = new OnePointCrossover();
        int [] parent1 = {4,5,6,5,6,5,6,5,6,5,6,5,6,5,6};
        int [] parent2 = {1,2,3,5,6,5,6,5,6,5,6,5,6,5,6};
        //Act 
        int [][] aux = cross.crossover(parent1, parent2);
        //Assert
        assertNotEquals(parent2, aux[1]);

    }
}
