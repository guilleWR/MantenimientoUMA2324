package org.mps;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mps.mutation.SwapMutation;

public class SwapMutationTest {
    
    @Nested
    @DisplayName("En esta clase realizamos los test de la clase SwapMutation") 
    class MutateTest {

        //no hacemos testing de constructor (no buscamos cobertura de linea 100%, sino de rama)

        @Test
        @DisplayName("si el array individual pasado como parametro es nulo, lanza una excepcion")
        public void Mutate_ArrayIndividualNulo_LanzaEvolutionaryAlgorithmException () throws EvolutionaryAlgorithmException {
            //Arrange
            int[] individual = null;
            SwapMutation sm = new SwapMutation();

            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> sm.mutate(individual));
        }

        @Test
        @DisplayName("si el tamaño del array de individual es cero, lanza una excepción")
        public void Mutate_ArrayIndividualVacio_LanzaEvolutionaryAlgorithmException () throws EvolutionaryAlgorithmException {
            // Arrange
            int[] individual = new int[0];  // Array de tamaño cero
            SwapMutation sm = new SwapMutation();

            // Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> sm.mutate(individual));
        }


    }
    
    

}
