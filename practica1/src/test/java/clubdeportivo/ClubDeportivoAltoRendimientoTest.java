/*
 * INTEGRANTES DEL GRUPO: Rueda Cabrera Pedro
 *                        Westerhof Rodríguez Guillermo Alejandro
*/

package clubdeportivo;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ClubDeportivoAltoRendimientoTest {
 
    @ParameterizedTest
    @MethodSource("parametrosConstructorClubDeportivoAltoRendimiento")
    @DisplayName("El metodo constructor lanza ClubException con parametros invalidos")
    public void ClubDeportivoAltoRendimiento_ConstructorConParametrosNoPermitidos_LanzaExcepcion(String nombre, int max, double incremento) {
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            new ClubDeportivoAltoRendimiento(nombre, max, incremento);
        });
    }

    //parametros que lanzarian un Club Exception en el constructor (valores negativos o cero)
    private static Stream<Arguments> parametrosConstructorClubDeportivoAltoRendimiento() {
        return Stream.of(
            Arguments.of("nombre", -1, 15.0),
            Arguments.of("nombre", 0, 15.0),
            Arguments.of("nombre", 10, -1.0),
            Arguments.of("nombre", 10, 0.0)
        );
    }

    @ParameterizedTest
    @MethodSource("parametrosConstructorClubDeportivoAltoRendimiento")
    @DisplayName("El metodo constructor lanza ClubException con parametros invalidos")
    public void ClubDeportivoAltoRendimiento_SegundoConstructorConParametrosNoPermitidos_LanzaExcepcion(String nombre, int max, double incremento) {
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            new ClubDeportivoAltoRendimiento(nombre,10, max, incremento);
        });
    }


    @Test
    @DisplayName("Constructor inicializa objeto correctamente con parametros validos")
    public void ClubDeportivoAltoRendimiento_ParametrosValidos_InicializaObjetoCorrectamente() {
        // Arrange
        String nombre = "nombre";
        int maximo = 10;
        double incremento = 15.0;

        // Act & Assert
        assertDoesNotThrow(() -> new ClubDeportivoAltoRendimiento(nombre, maximo, incremento));
    }


    @Test
    @DisplayName("Segundo constructo con parametro que incluye parametro nuevo tam no lanza excepción")
    public void ClubDeportivoAltoRendimiento2_ParametrosValidos_InicializaObjetoCorrectamente() {
        // Arrange
        String nombre = "nombre";
        int tam = 5; // Asumimos que este es un valor válido, ya que el manejo de valores inválidos debe ser probado en la clase padre.
        int maximo = 10;
        double incremento = 15.0;

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> new ClubDeportivoAltoRendimiento(nombre, tam, maximo, incremento),
            "El constructor con parámetro 'tam' válido debe inicializar el objeto correctamente sin lanzar excepciones.");
    }

    @Test
    @DisplayName("se añade correctamente una actividad introduciendo un grupo")
    public void AnyadirActividad_ConDatosInsuficientes_LanzaClubException() throws ClubException{
        // Arrange
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("nombre", 10, 20.0);
        String[] datosInsuficientes = new String[]{"dato1", "dato2", "dato3", "dato4"}; // tiene menos de 5 elementos

        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datosInsuficientes);
            }, "Se esperaba que anyadirActividad lanzara ClubException cuando faltan datos");
    }

    @ParameterizedTest
    @MethodSource("parametrosanyadirActividadNoPermitidos")
    @DisplayName("El metodo anyadirActividad lanza ClubException con parametros invalidos")
    public void AnyadirActividad_NoPermitidaDesdeString_DevuelveClubException(String codigo, String nombre, String plazas, String matriculados, String tarifa ) throws ClubException{
        //Arrange
        ClubDeportivoAltoRendimiento aux = new ClubDeportivoAltoRendimiento("Inazuma Eleven",10,5.0);
        String[] datos = {codigo,nombre,plazas,matriculados,tarifa};
        //Act & Assert
        assertThrows(ClubException.class,()-> aux.anyadirActividad(datos),"El metodo permite añadir grupos con valores no permitidos"); 
    }

    

    private static Stream<Arguments> parametrosanyadirActividadNoPermitidos() {
        return Stream.of(
            Arguments.of("Actividad", "Codigo", "cadena_texto", "20", "100.0"), //	nPlazas como texto
            Arguments.of("Actividad", "Codigo", "0", "cadena_texto", "100.0"),  //	matriculados como texto
            Arguments.of("Actividad", "Codigo", "15", "-1", "cadena_texto") //	tarifa como texto
        );
    }

    @Test
    @DisplayName("se añade correctamente una actividad introduciendo los datos como array de strings")
    public void anyadirActividad_MedianteStringConDatos_ValidaYAdaptaPlazasAlLimite() throws ClubException{
        //Arrange
        String[] datos = {"1234","Ver One Piece","30","0","70"};
        ClubDeportivoAltoRendimiento aux = new ClubDeportivoAltoRendimiento("Inazuma Eleven",20,5.0);
        //Act
        aux.anyadirActividad(datos);
        //Assert
        assertEquals(aux.toString(), "Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:20 - M:0) ]","No se ha añadido correctamente la actividad");
        

    }

    @Test
    @DisplayName("se añade correctamente una actividad introduciendo un array de strings con los datos")
    public void anyadirActividad_ConDatosString_AnyadeCorrectamente() throws ClubException{
        //Arrange
        String[] datos = {"1234","Ver One Piece","15","5","70"};
        ClubDeportivoAltoRendimiento aux = new ClubDeportivoAltoRendimiento("Inazuma Eleven",20,5.0);
        //Act
        aux.anyadirActividad(datos);
        //Assert
        assertEquals(aux.toString(), "Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:15 - M:5) ]","No se ha añadido correctamente la actividad");
        

    }

    @Test
    @DisplayName("los ingresos sean coherentes con el numero de matriculados y grupos")
    public void ingresos_ConActividadesMatriculadas_CalculaCorrectamente() throws ClubException{
        //Arrange
        ClubDeportivoAltoRendimiento aux = new ClubDeportivoAltoRendimiento("Inazuma Eleven",40,5.0);
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,10,70));
        aux.anyadirActividad(new Grupo("1235","Ver One Piece",30,20,30));
        aux.anyadirActividad(new Grupo("1236","Ver One Piece",40,15,55));
        //Act
        double ingresosEsperados = (10 * 70.0) + (20 * 30.0) + (15 * 55.0);
        ingresosEsperados += ingresosEsperados*(5.0/100);    
        //Assert
        assertEquals(ingresosEsperados, aux.ingresos(), 0.001,"No se generan correctamente los ingresos");
    }

    @Test
    @DisplayName("Compruebo que los ingresos sean coherentes con el numero de matriculados y grupos")
    public void ingresos_SinActividades_DevuelveCero() throws ClubException{
        //Arrange
        ClubDeportivoAltoRendimiento aux = new ClubDeportivoAltoRendimiento("Inazuma Eleven",40,5.0);
        //Act
        double ingresosEsperados = 0;    
        //Assert
        assertEquals(ingresosEsperados, aux.ingresos(), 0.001,"No se generan correctamente los ingresos");
    }

}