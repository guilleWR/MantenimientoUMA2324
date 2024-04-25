package org.mps.boundedqueue;
import static org.assertj.core.api.Assertions.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ArrayBoundedQueueTest {
    
    @Nested
    @DisplayName("Clase dedicada a los Test del método put")
    public class putTest{
        @Test
        @DisplayName("Un put con un value correto añade el valor al buffer")
        public void put_ConValueCorrecto_AñadeCorrectamenteElValueAlBuffer(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            int value = 1;
            //Act
            cola.put(value);
            //Assert
            assertThat(cola).element(0).isEqualTo(value);
        }

        @Test
        @DisplayName("Un put con un value correcto actuliza el size")
        public void put_ConValueCorrecto_ActualizaElSize(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            int expectedSize = 1;
            int value = 1;
            //Act
            cola.put(value);
            //Assert
            assertThat(cola.size()).isEqualTo(expectedSize);
        }

        @Test
        @DisplayName("Un put con un value correcto no mueve el first")
        public void put_ConValueCorrecto_NoActualizaElFirst(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            int value = 1;
            //Act
            cola.put(value);
            //Assert
            assertThat(cola.getFirst()).isEqualTo(0);
        }

        @Test
        @DisplayName("Un put con el Buffer completo devuelve FullBoundedQueueException")
        public void put_ConBufferCompleto_DevuelveFullBoundedQueueException(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(3);
            int value = 1;
            cola.put(value);
            cola.put(value);
            cola.put(value);
            //Act & Assert
            assertThatThrownBy(()->cola.put(value)).isInstanceOf(FullBoundedQueueException.class);
        }

        @Test
        @DisplayName("Un put con un value null devuelve IllegalArgumentException")
        public void put_ConValueNull_DevuelveIllegalArgumentException(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            //Act & Assert
            assertThatThrownBy(()->cola.put(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Varios put son añadidos correctamente ")
        public void put_ConValuesCorrectos_AñadeCorrectamenteLosValuesAlBuffer(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(3);
            int[] values = {1,2,3};
            //Act
            cola.put(values[0]);
            cola.put(values[1]);
            cola.put(values[2]);
            //Assert
            assertThat(cola).element(0).isEqualTo(values[0]);
            assertThat(cola).element(1).isEqualTo(values[1]);
            assertThat(cola).element(2).isEqualTo(values[2]);
        }

       
          
    }

    @Nested
    @DisplayName("Clase dedicada a los Test de la clase isFull")
    public class isFullTest{
        @Test
        @DisplayName("isFull con buffer no completo devuelve false")
        public void isFull_ConBufferNoLleno_DevuelveFalse(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            boolean full;
            //Act
            full = cola.isFull();
            //Assert
            assertThat(full).isFalse();
        }

        @Test
        @DisplayName("isFull con buffer completo devuelve true")
        public void isFull_ConBufferLleno_DevuelveTrue(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(3);
            boolean full;
            cola.put(1);
            cola.put(1);
            cola.put(1);
            //Act
            full = cola.isFull();
            //Assert
            assertThat(full).isTrue();
        }
    }

    @Nested
    @DisplayName("Clase dedicada a los Test de la clase isEmpty")
    public class isEmptyTest{
        @Test
        @DisplayName("isEmpty con buffer vacio devuelve true")
        public void isEmpty_ConBufferVacio_DevuelveTrue(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            boolean empty;
            //Act
            empty = cola.isEmpty();
            //Assert
            assertThat(empty).isTrue();
        }

        @Test
        @DisplayName("isEmpty con buffer no vacio devuelve false")
        public void isEmpty_ConBufferNoVacio_DevuelvecoFalse(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(3);
            boolean empty;
            cola.put(1);
            //Act
            empty = cola.isEmpty();
            //Assert
            assertThat(empty).isFalse();
        }
    }


}
