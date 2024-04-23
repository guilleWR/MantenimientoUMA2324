/*  Westerhof Rodríguez Guillermo Alejandro
    Rueda Cabrera Pedro 
*/
package org.mps;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        @Test
        @DisplayName("mutate se ejecuta sin lanzar excepciones para un array válido")
        public void Mutate_ArrayConVariosElementos_NoLanzaExcepcion() {
            // Arrange
            int[] individual = {1, 2, 3, 4, 5};
            SwapMutation sm = new SwapMutation();

            // Act & Assert
            assertDoesNotThrow(() -> sm.mutate(individual));
        }       

        @Test
        @DisplayName("mutate conserva el tamaño del array original")
        public void Mutate_ArrayConVariosElementos_ArraySolucionTieneMismaLongitudQueArrayDeEntrada() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] individual = {1, 2, 3, 4, 5};
            SwapMutation sm = new SwapMutation();

            // Act
            int[] mutated = sm.mutate(individual);

            // Assert
            assertEquals(individual.length, mutated.length, "El tamaño del array debe permanecer igual despues de la mutacion.");
        }
    }
}
