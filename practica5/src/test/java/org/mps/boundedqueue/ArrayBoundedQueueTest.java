package org.mps.boundedqueue;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class ArrayBoundedQueueTest {
    
    @Nested
    @DisplayName("En esta clase se realizamos los test del constructor ArrayBoundedQueue")
    class ArrayBoundedQueueTests {

        @Test
        @DisplayName("Si la capacidad es negativa el constructor lanza una excepcion")
        public void ArrayBoundedQueue_CapacidadNegativa_LanzaIllegalArgumentException() {
            // Arrange
            int capacidad = -1;

            // Act && Assert 
            assertThatThrownBy(() -> { new ArrayBoundedQueue<>(capacidad); })
            .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Si la capacidad es cero el constructor lanza una excepcion")
        public void ArrayBoundedQueue_CapacidadCero_LanzaIllegalArgumentException() {
            // Arrange
            int capacidad = 0;

            // Act && Assert 
            assertThatThrownBy(() -> { new ArrayBoundedQueue<>(capacidad); })
            .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Si la capacidad es positiva mayor que cero el no lanza ninguna excepcion")
        public void ArrayBoundedQueue_CapacidadPositiva_InicializaCorrectamenteArrayBoundedQueue() {
            // Arrange
            int capacidad = 5; //capacidad positiva cualquiera

            // Act && Assert 
            assertThatCode(() -> { new ArrayBoundedQueue<>(capacidad); })
                .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Si la capacidad es positiva,la cola inicialmente esta vacia (no hay elementos justo a la hora de crear la cola)")
        public void ArrayBoundedQueue_CapacidadPositiva_NumeroDeElementosEnLaColaEsCero() {
            // Arrange
            int capacidad = 5; //capacidad positiva cualquiera
            int expectedSizeElements = 0;

            // Act
            @SuppressWarnings("rawtypes")
            ArrayBoundedQueue cola = new ArrayBoundedQueue<>(capacidad);

            // Assert
            assertThat(cola.size()).isEqualTo(expectedSizeElements);
        }
    }

    @Nested
    @DisplayName("Clase dedicada a los Test del método put")
    public class putTests{
        @Test
        @DisplayName("Put con un value correto añade el valor al buffer")
        public void Put_ConValueCorrecto_AnyadeCorrectamenteElValueAlBuffer(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            int value = 1;
            //Act
            cola.put(value);
            //Assert
            assertThat(cola).element(0).isEqualTo(value);
        }

        @Test
        @DisplayName("Put con un value correcto actuliza el size")
        public void Put_ConValueCorrecto_ActualizaElSize(){
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
        @DisplayName("Put con un value correcto no mueve el first")
        public void Put_ConValueCorrecto_NoActualizaElFirst(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            int value = 1;
            //Act
            cola.put(value);
            //Assert
            assertThat(cola.getFirst()).isEqualTo(0);
        }

        @Test
        @DisplayName("Put con el Buffer completo devuelve FullBoundedQueueException")
        public void Put_ConBufferCompleto_DevuelveFullBoundedQueueException(){
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
        @DisplayName("Put con un value null devuelve IllegalArgumentException")
        public void Put_ConValueNull_DevuelveIllegalArgumentException(){
            //Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            //Act & Assert
            assertThatThrownBy(()->cola.put(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("Añadir varios valores con put actualiza correctamente el buffer con dichos valores ")
        public void Put_ConValuesCorrectos_AnyadeCorrectamenteLosValuesAlBuffer(){
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

        @Test
        @DisplayName("El buffer solamente tiene un elemento y nextFree debera estar en la siguiente posicion del buffer (avanza una casilla)")
        public void Put_TamanyoBufferEsUno_NextFreeAvanzaUnaCasilla(){
            // Arrange
            int capacidad = 4;
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(capacidad);
            int valor = 10;
            int expectedPosition = 1;

            // Act
            cola.put(valor);

            // Assert
            assertThat(cola.getLast()).isEqualTo(expectedPosition);
            
        }   

        @Test
        @DisplayName("El buffer esta casi lleno (queda uno hueco libre) y nextfree apunta a la ultima posicion del buffer")
        public void Put_TamanyoBufferEsCapacidadMenosUno_NextFreeEstaEnLaUltimaPoscicionDelBuffer(){
            //Arrange
            int capacidad = 4;
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(capacidad);
            int primero  = 1;
            int segundo = 2;
            int tercero  = 3;
            int expectedPosition = capacidad-1; // Ultima poscicion del buffer

            //Act
            cola.put(primero);
            cola.put(segundo);
            cola.put(tercero);

            //Assert
            assertThat(cola.getLast()).isEqualTo(expectedPosition);
        }   

        @Test
        @DisplayName("Cuando se han introducido tantos valores como capacidad maxima del buffer, el 'puntero' nextFree apunta a la primera posiccion de la casilla")
        public void Put_NextFreeEstaEnUltimaPosicionDelBuffer_NextFreeAvanzaHaciaLaPrimeraPosicionDelBuffer() {
            //Arrange
            int capacidad = 4;
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(capacidad);
            int primero  = 1;
            int segundo = 2;
            int tercero  = 3;
            int cuarto = 4;
            int expectedPosition = 0; // Ultima poscicion del buffer

            //Act
            cola.put(primero);
            cola.put(segundo);
            cola.put(tercero);
            cola.put(cuarto);

            //Assert
            assertThat(cola.getLast()).isEqualTo(expectedPosition);
        }
    }

    @Nested
    @DisplayName("En esta clase se realizamos los test del metodo get")
    class GetTests {

        @Test
        @DisplayName("Si se hace llamada a get con una cola vacia se lanza una excepcion")
        public void Get_BufferSinElementos_LanzaEmptyBoundedQueueException(){
            // Arrange
            int capacidad = 5;
            @SuppressWarnings("rawtypes")
            ArrayBoundedQueue cola = new ArrayBoundedQueue<>(capacidad);

            // Act && Assert 
            assertThatThrownBy(() -> { cola.get(); })
            .isInstanceOf(EmptyBoundedQueueException.class);
        }
        
        
        @Test
        @DisplayName("Si se hace llamada a get con arios elementos en la cola devuelve el primer elemento")
        @SuppressWarnings("unchecked")
        public void Get_BufferConVariosElementos_DevuelvePrimerElementoYActualizaBufferBorrandoEseElemento(){
            // Arrange
            int capacidad = 3;
            @SuppressWarnings("rawtypes")
            ArrayBoundedQueue cola = new ArrayBoundedQueue<>(capacidad);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            int expectedValue = 1;
            int expectedIndexFirst = 1;

            // Act
            int valueAfterGet = (int) cola.get();

            // Assert
            // Ha devuelto el primero elemento de la cola
            assertThat(valueAfterGet)
            .isEqualTo(expectedValue);

            //el numero de elementos se ha decrementado en 1
            assertThat(cola.size()).isEqualTo(capacidad-1);

            //first ha pasado a la siguiente casilla
            assertThat(cola.getFirst()).isEqualTo(expectedIndexFirst);   
        }

        
        @Test
        @DisplayName("El indice del primer elemento pasa de la posicion n-1 (ultima posicion buffer) a la 0 (posicion inicial buffer) Habiendo solo 1 elemento en el buffer ")
        public void Get_IndiceFirstEnUltimaPosicionDelBufferConUnElemento_IndiceSeMantieneEnLaPosicionInicialDelBuffer(){
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(1);
            int value = 1;
            
            int expectedFirstPosition = 0; // Posicion inicial del buffer
            cola.put(value);
            
            // Act
            cola.get();
            
            // Assert
            assertThat(cola.getFirst()).isEqualTo(expectedFirstPosition);
        }


        @Test
        @DisplayName("El indice del primer elemento pasa de la posicion n-1 (ultima posicion buffer) a la 0 (posicion inicial buffer)")
        public void Get_IndiceFirstEnUltimaPosicionDelBuffer_PasaALaPosicionInicialDelBuffer(){
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(4);
            int primero = 1;
            int segundo = 2;
            int tercero = 3;
            int cuarto = 4;
            int expectedFirstPosition = 0; // Posicion inicial del buffer
            cola.put(primero);
            cola.put(segundo);
            cola.put(tercero);
            cola.put(cuarto);

            // Act
            cola.get();
            cola.get();
            cola.get();
            // Ahora el indice de first esta en la posicion n-1. al hacer otro get el buffer 
            // quedaria vacio y ademas el indice first pasa a la posicion 0
            cola.get();

            // Assert
            assertThat(cola.getFirst()).isEqualTo(expectedFirstPosition);
        }

        @Test
        @DisplayName("Al hacer N llamadas al metodo get, el indice first avanza el mismo numero N de veces")
        public void Get_IndiceFirstEnPosicionInicialDelBuffer_PasaALasSiguientesPosicionesDelBuffer(){
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(4);
            int primero = 1;
            int segundo = 2;
            int tercero = 3;
            int cuarto = 4;
            int expectedFirstPosition = 2; // Posicion inicial del buffer
            cola.put(primero);
            cola.put(segundo);
            cola.put(tercero);
            cola.put(cuarto);

            // Act
            cola.get();
            cola.get();
            //en este momento el indice first ha avanzado dos casillas

            // Assert
            assertThat(cola.getFirst()).isEqualTo(expectedFirstPosition);
        }

    }

    @Nested
    @DisplayName("Clase dedicada a los Test del metodo isFull")
    public class isFullTests{
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
    @DisplayName("Clase dedicada a los Test del metodo isEmpty")
    public class isEmptyTests{
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


    @Nested
    @DisplayName("Clase dedicada a los Test del metodo size")
    public class sizeTests{

        @Test
        @DisplayName("Si el  buffer esta vacio, size devuelve cero")
        public void Size_bufferVacio_DevuelveCero() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            int expectedSize = 0;

            // Act
            int sizeResult = cola.size();

            // Assert
            assertThat(sizeResult).isEqualTo(expectedSize);
        }


        @Test
        @DisplayName("Si el buffer contiene n elementos, size devuelve n")
        public void Size_BufferConNElementos_DevuelveN() {
            // Arrange
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            int expectedSize = 3;

            // Act
            int sizeResult = cola.size();

            // Assert
            assertThat(sizeResult).isEqualTo(expectedSize);
        }
    }
}
