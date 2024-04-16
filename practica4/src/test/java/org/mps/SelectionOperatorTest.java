package org.mps;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.selection.TournamentSelection;

public class SelectionOperatorTest {
    
    @Nested
    @DisplayName("En esta clase realizamos los test de la clase TournamentSelection ") 
    class SelectTest {
        
        @Test
        @DisplayName("Si el constructor recibe un tamaño de torneo negativo debe lanzar una excepcion")
        public void TournamentSelection_TamanyoTorneoMenorQueCero_LanzaEvolutionaryAlgorithmExcepcion() throws EvolutionaryAlgorithmException {
            //Arrange
            int tamanyo = -1;
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> new TournamentSelection(tamanyo));
        }

        @Test
        @DisplayName("Si el constructor recibe un tamaño de torneo igual a cero debe lanzar una excepcion")
        public void TournamentSelection_TamanyoTorneoIgualACero_LanzaEvolutionaryAlgorithmExcepcion() throws EvolutionaryAlgorithmException {
            //Arrange
            int tamanyo = 0;
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> new TournamentSelection(tamanyo));
        }

        @Test
        @DisplayName("Si el constructor recibe un tamaño de torneo positivo inicializa correctamente el tamaño del objeto")
        public void TournamentSelection_TamanyoTorneoMayorQueCero_InicializaTamanyoCorrectamente() {
            // Arrange
            int tamanyoEsperado = 5;

            // Act & Assert
            assertDoesNotThrow(() -> new TournamentSelection(tamanyoEsperado));
        }


        @Test
        @DisplayName("si la poblacion pasada como parametro es nulo lanza una excepcion")
        public void select_ArrayPoblacionEsNulo_LanzaEvolutionaryAlgorithmExcepcion () throws EvolutionaryAlgorithmException {
            //Arrange
            int[] poblacion = null;
            int tamanyo = 5;
            TournamentSelection ts = new TournamentSelection(tamanyo);

            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> ts.select(poblacion),
            "Se esperaba que TournamentSelection lanzara una EvolutionaryAlgorithmException debido a un array nulo");
        }

        @Test
        @DisplayName("si el tamaño del array de población es cero, lanza una excepción")
        public void select_ArrayPoblacionEsCero_LanzaEvolutionaryAlgorithmExcepcion() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] poblacion = new int[0];  // Array de tamaño cero
            int tamanyo = 5;
            TournamentSelection ts = new TournamentSelection(tamanyo);

            // Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, () -> ts.select(poblacion),
                "Se esperaba que TournamentSelection lanzara una EvolutionaryAlgorithmException debido a un array de población de tamaño cero");
        }


        @Test
        @DisplayName("El metodo select devuelve una seleccion de candidatos distinto a la poblacion inical")
        public void select_ArrayPoblacionConVariosElementos_DevuelveSeleccionadaDistintaAPoblacionInicial() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] poblacion = {3,5,7,9,2};  // Array con poblacion
            int tamanyo = 5;
            TournamentSelection ts = new TournamentSelection(tamanyo);

            // Act
            int[] seleccion = ts.select(poblacion);

            String poblacionString = poblacion.toString();
            String seleccionString = seleccion.toString();


            //Assert
            assertNotEquals(poblacionString, seleccionString);
        }

        
        @Test
        @DisplayName("El metodo select devuelve una seleccion del mismo tamaño que la poblacion inicial")
        public void select_ArrayPoblacionConVariosElementos_PoblacionSeleccionadaTieneMismoTamañoQuePoblacionInicial() throws EvolutionaryAlgorithmException {
            // Arrange
            int[] poblacion = {3,5,7,9,2};  // Array con poblacion
            int tamanyo = 5;
            TournamentSelection ts = new TournamentSelection(tamanyo);

            // Act
            int[] seleccion = ts.select(poblacion);

            //Assert
            assertEquals(tamanyo, seleccion.length);
        }


    }

}
