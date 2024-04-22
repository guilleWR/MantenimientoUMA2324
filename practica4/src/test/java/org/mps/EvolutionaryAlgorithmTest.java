package org.mps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        @DisplayName("")
        public void EvolutionaryAlgorithm_SelectionOperatorComoNull_DevuelveEvolutionaryAlgorithmException(){
            //Arrange
            SelectionOperator select = null;
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = new OnePointCrossover();
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class,()->new EvolutionaryAlgorithm(select,muta,cross));
        }

        @Test
        @DisplayName("")
        public void EvolutionaryAlgorithm_MutationOperatorComoNull_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = null;
            CrossoverOperator cross = new OnePointCrossover();
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class,()->new EvolutionaryAlgorithm(select,muta,cross));
        }

        @Test
        @DisplayName("")
        public void EvolutionaryAlgorithm_CrossoverOperatorComoNull_DevuelveEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException{
            //Arrange
            SelectionOperator select = new TournamentSelection(5);
            MutationOperator muta = new SwapMutation();
            CrossoverOperator cross = null;
            
            //Act & Assert
            assertThrows(EvolutionaryAlgorithmException.class,()->new EvolutionaryAlgorithm(select,muta,cross));
        }

        @Test
        @DisplayName("")
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
        @DisplayName("")
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
        @DisplayName("")
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
        @DisplayName("")
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
        @DisplayName("")
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
        @DisplayName("")
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
        @DisplayName("")
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
}
