/*  Westerhof Rodríguez Guillermo Alejandro
    Rueda Cabrera Pedro 
*/
package org.mps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;
import org.mps.mutation.MutationOperator;
import org.mps.mutation.SwapMutation;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

public class EvolutionaryAlgorithmTest {
    @Nested
    @DisplayName("Test para el contructor")
    public class EvolutionaryAlgorithmConstructorTest{

        @Test
        @DisplayName("Inserto selection null y devuelve EvolutionaryAlgorithmException")
        public void EvolutionaryAlgorithm_SelectionOperatorComoNull_DevuelveEvolutionaryAlgorithmException(){
            //Arrange
            SelectionOperator select = null;
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class,()->new EvolutionaryAlgorithm(select,muta,cross));
        }

        @Test
        @DisplayName("Inserto mutation null y devuelve EvolutionaryAlgorithmException")
        public void EvolutionaryAlgorithm_MutationOperatorComoNull_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = null;
            CrossoverOperator cross = new OnePointCrossover();
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class,()->new EvolutionaryAlgorithm(select,muta,cross));
        }

        @Test
        @DisplayName("Inserto crossover null y devuelve EvolutionaryAlgorithmException")
        public void EvolutionaryAlgorithm_CrossoverOperatorComoNull_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = null;
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class,()->new EvolutionaryAlgorithm(select,muta,cross));
        }

        @Test
        @DisplayName("Inserto operadores correctamente y compruebo que no devuelve EvolutionaryAlgorithmException")
        public void EvolutionaryAlgorithm_ContructorCorrecto_SeCreaCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            
            //Act & Assert
            assertDoesNotThrow(()->new EvolutionaryAlgorithm(select,muta,cross));
        }
    }

    @Nested
    @DisplayName("Test para los métodos get y set")
    public class GetYSetTest{
        
        @Test
        @DisplayName("EvolutionaryAlgorithm Con operador mutation devuelve correctamente el operador")
        public void getMutationOperator_ConMutationOperator_DevuelveElObjetoCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertEquals(muta, Algoritmo.getMutationOperator());
        }

        @Test
        @DisplayName("EvolutionaryAlgorithm cambia operador mutation correctamente")
        public void setMutationOperator_CambioMutationOperator_DevuelveElObjetoCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            MutationOperator muta2 = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act 
            Algoritmo.setMutationOperator(muta2);
            //Assert
            assertEquals(muta2, Algoritmo.getMutationOperator());
        }

        @Test
        @DisplayName("EvolutionaryAlgorithm Con operador selection devuelve correctamente el operador")
        public void getSelectionOperator_ConSelectionOperator_DevuelveElObjetoCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertEquals(select, Algoritmo.getSelectionOperator());
        }

        @Test
        @DisplayName("EvolutionaryAlgorithm cambia operador selection correctamente")
        public void setSelectionOperator_CambioSelectionOperator_DevuelveElObjetoCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            SelectionOperator select2 = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act 
            Algoritmo.setSelectionOperator(select2);
            //Assert
            assertEquals(select2, Algoritmo.getSelectionOperator());
        }

        @Test
        @DisplayName("EvolutionaryAlgorithm Con operador crossover devuelve correctamente el operador")
        public void getCrossoverOperator_ConCrossoverOperator_DevuelveElObjetoCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertEquals(cross, Algoritmo.getCrossoverOperator());
        }

        @Test
        @DisplayName("EvolutionaryAlgorithm cambia operador crossover correctamente")
        public void setCrossoverOperator_CambioCrossoverOperator_DevuelveElObjetoCorrectamente() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            CrossoverOperator cross2 = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act 
            Algoritmo.setCrossoverOperator(cross2);
            //Assert
            assertEquals(cross2, Algoritmo.getCrossoverOperator());
        }
    }

    @Nested
    @DisplayName("Test para el metodo optimize")
    public class optimazeTest{
        
        @Test
        @DisplayName("Optimize con poblacion valida devuelve una nueva poblacion del mismo tamaño")
        public void optimize_ConPoblacionValida_DevuelvePoblacionMismoTamaño() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act
            int[][] resultPopulation = Algoritmo.optimize(poblacion);
            //Assert
            assertEquals(poblacion.length, resultPopulation.length);
        }

        @Test
        @DisplayName("Optimize con poblacion valida devuelve una nueva poblacion distinta")
        public void optimize_ConPoblacionValida_DevuelvePoblacionDistinta() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
            int[][] poblacionResultado = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act
            poblacionResultado = Algoritmo.optimize(poblacionResultado);
            //Assert
            assertNotEquals(poblacion, poblacionResultado);
        }

        @Test
        @DisplayName("Optimize con subpoblaciones vacias devuelve EvolutionaryAlgorithmException")
        public void optimize_ConSubPoblacionesVacias_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {{}, {}, {}, {}};
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, ()->Algoritmo.optimize(poblacion));
        }

        @Test
        @DisplayName("Optimize con poblacion nulla devuelve EvolutionaryAlgorithmException")
        public void optimize_ConPoblacionNulla_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = null;
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, ()->Algoritmo.optimize(poblacion));
        }

        @Test
        @DisplayName("Optimize con poblacion vacia devuelve EvolutionaryAlgorithmException")
        public void optimize_ConPoblacionVacia_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {};
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, ()->Algoritmo.optimize(poblacion));
        }

        @Test
        @DisplayName("Optimize con primera subpoblacion nulla devuelve EvolutionaryAlgorithmException")
        public void optimize_ConPrimeraPoblacionNulla_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {null, {}, {}, {}};
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, ()->Algoritmo.optimize(poblacion));
        }

        @Test
        @DisplayName("Optimize con poblacion valida usando otro operador selection devuelve EvolutionaryAlgorithmException")
        public void optimize_ConPoblacionValidaDistintoTournamentSelection_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
            SelectionOperator select = new TournamentSelection(10);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, ()->Algoritmo.optimize(poblacion));
            
            
        }


        private  int[][] generarPoblacionLarga(int tamPoblacion, int tamIndividual) {
            Random random = new Random();
            int[][] population = new int[tamPoblacion][tamIndividual];
            for (int i = 0; i < tamPoblacion; i++) {
                for (int j = 0; j < tamIndividual; j++) {
                    population[i][j] = random.nextInt(100); // Genera valores aleatorios entre 0 y 99
                }
            }
            return population;
        }

        @Test
        @DisplayName("Optimize con poblacion valida grande devuelve una nueva poblacion del mismo tamaño")
        public void optimize_ConPoblacionValidaGrande_DevuelvePoblacionDelMismoTamaño() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = generarPoblacionLarga(100, 100);
            SelectionOperator select = new TournamentSelection(100);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act
            int[][] resultPopulation = Algoritmo.optimize(poblacion);
            //Assert
            assertEquals(poblacion.length, resultPopulation.length);
        }

        @Test
        @DisplayName("Optimize con poblacion impar devuelve una nueva poblacion del mismo tamaño")
        public void optimize_ConPoblacionImpar_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            int[][] poblacion = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}, {13, 14, 15}};
            SelectionOperator select = new TournamentSelection(3);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            EvolutionaryAlgorithm Algoritmo = new EvolutionaryAlgorithm(select, muta, cross);
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class, ()->Algoritmo.optimize(poblacion));
           
        }
    }

}


       