package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mps.dispositivo.Dispositivo;

public class ronQI2SilverTest {

    
    @Nested
    @DisplayName("En esta clase realizamos los test ") 
    class inicializarTest {
  
        /*
        * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
        * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
        * el método inicializar de ronQI2 o sus subclases, 
        * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
        */

        @Test
		@DisplayName("Si conectarSensorPresion devuelve false inicializar tambien devuelve false")
		void inicializar_sensorPresionNoConecta_devuelveFalse() {
			// Arrange
			Dispositivo dispositivoMock = mock(Dispositivo.class);

			when(dispositivoMock.conectarSensorPresion()).thenReturn(false);
			
			RonQI2Silver ronQI2Silver = new RonQI2Silver();
			ronQI2Silver.anyadirDispositivo(dispositivoMock);
			
			// Act
			boolean resultado = ronQI2Silver.inicializar();
			
			// Assert
			assertFalse(resultado);
		}

        @Test
		@DisplayName("Si conectarSensorPresion devuelve true pero conectarSonido devuelve false, inicializar devuelve false")
		void inicializar_sensorPresionConectadoPeroSensorSonidoNoConectado_devuelveFalse() {
			// Arrange
			Dispositivo dispositivoMock = mock(Dispositivo.class);

			when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(false);
			
			RonQI2Silver ronQI2Silver = new RonQI2Silver();
			ronQI2Silver.anyadirDispositivo(dispositivoMock);
			
			// Act
			boolean resultado = ronQI2Silver.inicializar();
			
			// Assert
			assertFalse(resultado);
		}

        @Test
		@DisplayName("Ambos sensores estan conectados pero no esta configurado el sonido, se devuelve false")
		void inicializar_SensoresSonidoYPresionEstanConectadosPeroNoEstaConfiguradoElSonido_devuelveFalse() {
			// Arrange
			Dispositivo dispositivoMock = mock(Dispositivo.class);

			when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(false);
			
			RonQI2Silver ronQI2Silver = new RonQI2Silver();
			ronQI2Silver.anyadirDispositivo(dispositivoMock);
			
			// Act
			boolean resultado = ronQI2Silver.inicializar();
			
			// Assert
			assertFalse(resultado);
		}


        @Test
		@DisplayName("Ambos sensores estan conectados, el sonido esta configurado pero la presion no, se devuelve false")
		void inicializar_SensoresSonidoYPresionEstanConectadosSonidoEstaConfiguradoPeroLaPresionNo_devuelveFalse() {
			// Arrange
			Dispositivo dispositivoMock = mock(Dispositivo.class);

			when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(false);
			
			RonQI2Silver ronQI2Silver = new RonQI2Silver();
			ronQI2Silver.anyadirDispositivo(dispositivoMock);
			
			// Act
			boolean resultado = ronQI2Silver.inicializar();
			
			// Assert
			assertFalse(resultado);
		}

        @Test
		@DisplayName("Los sensores y configuraciones del sonido y la presion estan correctos, devuelve true")
		void inicializar_TodosLosSensoresYConfiguracionesEstanCorrectos_devuelveTrue() {
			// Arrange
			Dispositivo dispositivoMock = mock(Dispositivo.class);

			when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
			
			RonQI2Silver ronQI2Silver = new RonQI2Silver();
			ronQI2Silver.anyadirDispositivo(dispositivoMock);
			
			// Act
			boolean resultado = ronQI2Silver.inicializar();
			
			// Assert
			assertTrue(resultado);
		}

        /*
        * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
        * se llama una sola vez al configurar de cada sensor.
        */
        @Test
        @DisplayName("Se comprueba que se ejecuta exactamente 1 vez la configuracion del sonido y presion (los conectores estan a true)")
        void inicializar_conectaYConfiguraSensoresCorrectamente_llamaConfigurarUnaVezPorSensor() {
            // Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);
            
            // Act
            ronQI2Silver.inicializar();
            
            // Assert
            verify(dispositivoMock, times(1)).configurarSensorPresion();
            verify(dispositivoMock, times(1)).configurarSensorSonido();
        }

    }
    
    @Nested
    @DisplayName("En esta clase realizamos los test de anyadirDispositivo") 
    class anyadirDispositivoTest {

        @Test
        @DisplayName("Se comprueba que se ejecuta exactamente 1 vez la configuracion del sonido y presion (los conectores estan a true)")
        void anyadirDispositivo_ElDispositivoEsNulo_LanzaIllegalArgumentException() {
            // Arrange
            Dispositivo dispositivoMock = null;
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> ronQI2Silver.anyadirDispositivo(dispositivoMock));
        }
    }
    
	
    @Nested
    @DisplayName("En esta clase realizamos los test de reconectar") 
    class reconectarTest {

        /*
        * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
        * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
        * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
        */

        @Test
        @DisplayName("Si el dispositivo esta conectado, devuelve false (no reconecta)")
        void reconectar_ElDispositivoEstaConectado_DevuelveFalse() {
            // Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            when(dispositivoMock.estaConectado()).thenReturn(true);   

            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            // Act
            boolean result = ronQI2Silver.reconectar();
            
            // Assert
            assertFalse(result);
        }

        @Test
        @DisplayName("Si el dispositivo no esta conectado, pero el sensor de presion no esta conectado, devuelve false")
        void reconectar_DispositivoENoConectadoPeroElSensorDePresionDesconectado_DevuelveFalse() {
            // Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            when(dispositivoMock.estaConectado()).thenReturn(false); 
            when(dispositivoMock.conectarSensorPresion()).thenReturn(false);

            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            // Act
            boolean result = ronQI2Silver.reconectar();
            
            // Assert
            assertFalse(result);
        }

        @Test
        @DisplayName("Si el dispositivo no esta conectado, pero el sensor de sonido no esta conectado, devuelve false")
        void reconectar_DispositivoENoConectadoPeroElSensorDeSonidoDesconectado_DevuelveFalse() {
            // Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            when(dispositivoMock.estaConectado()).thenReturn(false); 
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(false);

            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            // Act
            boolean result = ronQI2Silver.reconectar();
            
            // Assert
            assertFalse(result);
        }

        @Test
        @DisplayName("Si el dispositivo no esta conectado, y ambos sensores estan conectados devuelve true")
        void reconectar_DispositivoYSensoresEstanConectados_DevuelveTrue() {
            // Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            when(dispositivoMock.estaConectado()).thenReturn(false); 
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);

            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            // Act
            boolean result = ronQI2Silver.reconectar();
            
            // Assert
            assertTrue(result);
        }

    }
    

    @Nested
    @DisplayName("En esta clase realizamos los test de estaConectado") 
    class estaConectadoTest {

        @Test
        @DisplayName("devuelve false si el dispositivo no esta conectado")
        void estaConectado_ElDispositivoNoEstaConectado_DevuelveFalse() {
            // Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            when(dispositivoMock.estaConectado()).thenReturn(false); 
            
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            // Act
            boolean result = ronQI2Silver.estaConectado();
            
            // Assert
            assertFalse(result);
        }

    }

    @Nested
    @DisplayName("En esta clase realizamos los test de obtenerNuevaLectura") 
    class obtenerNuevaLecturaTest {
        
        @Test
        @DisplayName("")
        void obtenerNuevaLectura_dispositivoAñadido_lecturasDeSensoresSolicitadasUnaVez() {
            //Arrange
            Dispositivo dispositivoMock = mock(Dispositivo.class);
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dispositivoMock);

            // Act
            ronQI2Silver.obtenerNuevaLectura();

            // Assert
            // Verificar que los métodos leerSensorPresion() y leerSensorSonido() sean llamados.
            verify(dispositivoMock, times(1)).leerSensorPresion();
            verify(dispositivoMock, times(1)).leerSensorSonido();
        }

        //TODOO (faltan mas cosas por completar) pero es muy dificil hacer pruebas del comportamiento de las listas
        
    }
    

    // este ultimo metodo no esta en una clase nest porque da error con el stream de datos (no entiendo el por qué)

    private static Stream<Integer> proporcionarNumLecturas() {
        return Stream.of(4, 5, 10); // Los diferentes números de lecturas que quieres probar
    }
    
    @ParameterizedTest
    @MethodSource("proporcionarNumLecturas")
    @DisplayName("Se comprueba que no hay apnea con diferentes numero de lecturas, la medias estan por encima del umbral, no hay apnea y devuelve false")
    void evaluarApneaSuenyo_LecturasDePresionYSonidoMayoresQueLimites_DevuelveFalse(int numLecturas) {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        
        when(dispositivoMock.leerSensorPresion()).thenReturn(25.0f); // Valor por encima del umbral
        when(dispositivoMock.leerSensorSonido()).thenReturn(35.0f);  // Valor por encima del umbral

        RonQI2Silver ronQI2Silver = new RonQI2Silver();
        ronQI2Silver.anyadirDispositivo(dispositivoMock);

        for (int i = 0; i < numLecturas; i++) {
            ronQI2Silver.obtenerNuevaLectura();
        }

        // Act
        boolean resultado = ronQI2Silver.evaluarApneaSuenyo();

        // Assert
        assertFalse(resultado); // Asumiendo que el resultado esperado es true para estos valores
    }

    @ParameterizedTest
    @MethodSource("proporcionarNumLecturas")
    @DisplayName("Se comprueba que si hay apnea con diferentes numero de lecturas, la media esta por debajo del umbral, si hay apnea y devuelve true")
    void evaluarApneaSuenyo_LecturasDePresionYSonidoMenoresQueLimites_DevuelveTrue(int numLecturas) {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        
        when(dispositivoMock.leerSensorPresion()).thenReturn(19.0f); // Valor por debajo del umbral
        when(dispositivoMock.leerSensorSonido()).thenReturn(29.0f);  // Valor por debajo del umbral

        RonQI2Silver ronQI2Silver = new RonQI2Silver();
        ronQI2Silver.anyadirDispositivo(dispositivoMock);

        for (int i = 0; i < numLecturas; i++) {
            ronQI2Silver.obtenerNuevaLectura();
        }

        // Act
        boolean resultado = ronQI2Silver.evaluarApneaSuenyo();

        // Assert
        assertTrue(resultado); // Asumiendo que el resultado esperado es true para estos valores
    }

    @ParameterizedTest
    @MethodSource("proporcionarNumLecturas")
    @DisplayName("Se comprueba que si hay apnea con diferentes numero de lecturas, la media de lecturas de presion esta por debajo del umbral")
    void evaluarApneaSuenyo_LecturaPresionPorDebajoDelLimite_DevuelveTrue(int numLecturas) {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        
        when(dispositivoMock.leerSensorPresion()).thenReturn(19.0f); // Valor por debajo del umbral
        when(dispositivoMock.leerSensorSonido()).thenReturn(31.0f);  // Valor por encima del umbral

        RonQI2Silver ronQI2Silver = new RonQI2Silver();
        ronQI2Silver.anyadirDispositivo(dispositivoMock);

        for (int i = 0; i < numLecturas; i++) {
            ronQI2Silver.obtenerNuevaLectura();
        }

        // Act
        boolean resultado = ronQI2Silver.evaluarApneaSuenyo();

        // Assert
        assertTrue(resultado); // Asumiendo que el resultado esperado es true para estos valores
    }

    @ParameterizedTest
    @MethodSource("proporcionarNumLecturas")
    @DisplayName("Se comprueba que si hay apnea con diferentes numero de lecturas, la media de lecturas de sonido esta por debajo del umbral")
    void evaluarApneaSuenyo_LecturaSonidoPorDebajoDelLimite_DevuelveTrue(int numLecturas) {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        
        when(dispositivoMock.leerSensorPresion()).thenReturn(21.0f); // Valor por encima del umbral
        when(dispositivoMock.leerSensorSonido()).thenReturn(29.0f);  // Valor por debajo del umbral

        RonQI2Silver ronQI2Silver = new RonQI2Silver();
        ronQI2Silver.anyadirDispositivo(dispositivoMock);

        for (int i = 0; i < numLecturas; i++) {
            ronQI2Silver.obtenerNuevaLectura();
        }

        // Act
        boolean resultado = ronQI2Silver.evaluarApneaSuenyo();

        // Assert
        assertTrue(resultado); // Asumiendo que el resultado esperado es true para estos valores
    }

    @ParameterizedTest
    @MethodSource("proporcionarNumLecturas")
    @DisplayName("Se comprueba con las dos medias de sonido y presion al limite del umbral, que no hay apnea y devuelve false")
    void evaluarApneaSuenyo_LecturaSonidoYPresionAlLimiteDelUmbral_DevuelveTrue(int numLecturas) {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        
        when(dispositivoMock.leerSensorPresion()).thenReturn(20.0f); // Valor por encima del umbral
        when(dispositivoMock.leerSensorSonido()).thenReturn(30.0f);  // Valor por debajo del umbral

        RonQI2Silver ronQI2Silver = new RonQI2Silver();
        ronQI2Silver.anyadirDispositivo(dispositivoMock);

        for (int i = 0; i < numLecturas; i++) {
            ronQI2Silver.obtenerNuevaLectura();
        }

        // Act
        boolean resultado = ronQI2Silver.evaluarApneaSuenyo();

        // Assert
        assertFalse(resultado); // Asumiendo que el resultado esperado es true para estos valores
    }

        

    




    

    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

}
    
    
    

