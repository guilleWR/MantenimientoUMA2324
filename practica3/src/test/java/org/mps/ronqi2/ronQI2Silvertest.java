package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

        /*ESTA ES LA VERSION PARAMETRIZADA
        
        @ParameterizedTest
    @MethodSource("inicializarTestCases")
    void inicializarTests(boolean conectarPresion, boolean configurarPresion, 
                          boolean conectarSonido, boolean configurarSonido, 
                          boolean resultadoEsperado) {
        // Arrange
        Dispositivo dispositivoMock = mock(Dispositivo.class);
        when(dispositivoMock.conectarSensorPresion()).thenReturn(conectarPresion);
        when(dispositivoMock.configurarSensorPresion()).thenReturn(configurarPresion);
        when(dispositivoMock.conectarSensorSonido()).thenReturn(conectarSonido);
        when(dispositivoMock.configurarSensorSonido()).thenReturn(configurarSonido);

        RonQI2Silver ronQI2Silver = new RonQI2Silver();
        ronQI2Silver.anyadirDispositivo(dispositivoMock);

        // Act
        boolean resultado = ronQI2Silver.inicializar();

        // Assert
        assertEquals(resultadoEsperado, resultado);
    }

    static Stream<Arguments> inicializarTestCases() {
        return Stream.of(
            Arguments.of(false, false, false, false, false),
            Arguments.of(true, false, false, false, false),
            Arguments.of(true, true, false, false, false),
            Arguments.of(true, true, true, false, false),
            Arguments.of(true, false, true, true, false),
            Arguments.of(false, true, true, true, false),
            Arguments.of(true, true, true, true, true)
        );
    } */


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
    
    
		
	

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    
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
    
    
    

