/*
 * INTEGRANTES DEL GRUPO: Rueda Cabrera Pedro
 *                        Westerhof Rodríguez Guillermo Alejandro
*/


package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GrupoTest {

    @ParameterizedTest
    @MethodSource("parametrosNoPermitidosConstructorGrupo")
    @DisplayName("El metodo constructor lanza ClubException con parametros invalidos")
    public void Grupo_ConstructorConParametrosNoPermitidos_LanzaClubExcepcion(String codigo,
                                                                         String actividad, int nPlazas, int nMatriculados, double tarifa) {
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            new Grupo(codigo, actividad, nPlazas, nMatriculados, tarifa);
        });
    }

    
    private static Stream<Arguments> parametrosNoPermitidosConstructorGrupo() {
        return Stream.of(
            Arguments.of("Actividad", "Codigo", -1, 20, 100.0), //	nPlazas negativo
            Arguments.of("Actividad", "Codigo", 0, 20, 100.0),  //	nPlazas cero
            Arguments.of("Actividad", "Codigo", 15, -1, 150.0), //	nMatriculados negativo
            Arguments.of("Actividad", "Codigo", 10, 8, -1.0),   //	tarifa negativa
            Arguments.of("Actividad", "Codigo", 10, 8, 0.0),    //	tarifa cero
            Arguments.of("Actividad", "Codigo", 25, 26, 90.0),   //	mas matriculados que plazas
            Arguments.of(null, "actividad", 20, 10, 100.0),     // codigo null (estos dos ultimos metodos daban error y es por que no se manejaban bien)
            Arguments.of("actividad", null, 20, 10, 100.0),    // actividad null
            Arguments.of("actividad", "", 20, 10, 100.0),       // código vacío
            Arguments.of("actividad", " ", 20, 10, 100.0),      // código solo espacios
            Arguments.of("", "codigo", 20, 10, 100.0),          // actividad vacía
            Arguments.of(" ", "codigo", 20, 10, 100.0)          // actividad solo espacios
        );
    }


    @Test
    @DisplayName("Se comprueba que al crear el objeto grupo se inicialice correctamente el codigo que se le pasa como parametro en el constructor")
    public void Grupo_ParametroConstructorCodigoValido_InicializaCodigoEnGrupoCorrectamente() throws ClubException {
        //Arrange
        String codigoEsperado = "codigo";
        Grupo g = new Grupo(codigoEsperado, "actividad", 20, 20, 100.0);

        //Act 
        String codigoActual = g.getCodigo();

        //Assert
        assertEquals(codigoActual, codigoEsperado);
    }

    @Test
    @DisplayName("Se comprueba que al crear el objeto grupo se inicialice correctamente la actividad que se le pasa como parametro en el constructor")
    public void Grupo_ParametroConstructorActividadValida_InicializaActividadEnGrupoCorrectamente() throws ClubException {
        //Arrange
        String actividadEsperada = "actividad";
        Grupo g = new Grupo("codigo", actividadEsperada, 20, 20, 100.0);

        //Act 
        String actividadActual = g.getActividad();

        //Assert
        assertEquals(actividadActual, actividadEsperada);
    }

    @Test
    @DisplayName("Se comprueba que al crear el objeto grupo se inicialice correctamente el número de plazas que se le pasa como parámetro en el constructor")
    public void Grupo_ParametroConstructorNPlazasValido_InicializaNPlazasEnGrupoCorrectamente() throws ClubException {
        //Arrange
        int nplazasEsperadas = 20;
        Grupo g = new Grupo("codigo", "actividad", nplazasEsperadas, 20, 100.0);

        //Act 
        int nplazasActual = g.getPlazas();

        //Assert
        assertEquals(nplazasActual, nplazasEsperadas);
    }


    @Test
    @DisplayName("Se comprueba que al crear el objeto grupo se inicialice correctamente el número de matriculados que se le pasa como parámetro en el constructor")
    public void Grupo_ParametroConstructorNMatriculadosValido_InicializaNMatriculadosEnGrupoCorrectamente() throws ClubException {
        //Arrange
        int nmatriculadosEsperados = 20;
        Grupo g = new Grupo("codigo", "actividad", 20, nmatriculadosEsperados, 100.0);

        //Act 
        int nmatriculadosActual = g.getMatriculados();

        //Assert
        assertEquals(nmatriculadosActual, nmatriculadosEsperados);
    }

    @Test
    @DisplayName("Se comprueba que al crear el objeto grupo se inicialice correctamente la tarifa que se le pasa como parámetro en el constructor")
    public void Grupo_ParametroConstructorTarifaValida_InicializaTarifaEnGrupoCorrectamente() throws ClubException {
        //Arrange
        double tarifaEsperada = 100.0;
        Grupo g = new Grupo("codigo", "actividad", 20, 20, tarifaEsperada);

        //Act 
        double tarifaActual = g.getTarifa();

        //Assert
        assertEquals(tarifaActual, tarifaEsperada, 0.001);
    }

    
    @Test
    @DisplayName("El metodo getCodigo devuelve el codigo asociado al grupo")
    public void getCodigo_CuandoGrupoEsCreadoConCodigoEspecifico_DevuelveCodigoCorrecto() throws ClubException  {
        //Arrange
        String expectedCodigo = "codigo";
        Grupo g = new Grupo(expectedCodigo, "actividad", 20, 0, 100.0);

        //Act
        String result = g.getCodigo();

        //Assert
        assertEquals(expectedCodigo, result, "El getter getCodigo no devuelve el valor esperado.");
    }

        
    @Test
    @DisplayName("El metodo getActicidad devuelve la actividad asociada el grupo")
    public void getActividad_CuandoGrupoEsCreadoConActividadEspecifica_DevuelveActividadCorrecta() throws ClubException {
        //Arrange
        String expectedActividad = "actividad";
        Grupo g = new Grupo("codigo", expectedActividad, 20, 0, 100.0);

        //Act
        String result = g.getActividad();

        //Assert
        assertEquals(expectedActividad, result, "El getter getActividad no devuelve el valor esperado.");
    }
    

    @Test
    @DisplayName("El metodo getPlazas devuelve el número de plazas asociadas al grupo")
    public void getPlazas_CuandoGrupoEsCreadoConNumeroDePlazasEspecifico_DevuelveNumeroPlazasCorrecto() throws ClubException {
        // Arrange
        int expectedPlazas = 20;
        Grupo g = new Grupo("codigo", "actividad", expectedPlazas, 0, 100.0);

        // Act
        int result = g.getPlazas();

        // Assert
        assertEquals(expectedPlazas, result, "El getter getPlazas no devuelve el valor esperado.");
    }


    @Test
    @DisplayName("El metodo getMatriculados devuelve el número de matriculados en el grupo")
    public void getMatriculados_CuandoGrupoEsCreadoConNumeroDeMatriculadosEspecifico_DevuelveNumeroMatriculadosCorrecto() throws ClubException {
        // Arrange
        int expectedMatriculados = 0; // Asumiendo que este es un valor válido para tus pruebas
        Grupo g = new Grupo("codigo", "actividad", 20, expectedMatriculados, 100.0);

        // Act
        int result = g.getMatriculados();

        // Assert
        assertEquals(expectedMatriculados, result, "El getter getMatriculados no devuelve el valor esperado.");
    }


    @Test
    @DisplayName("El metodo getTarifa devuelve la tarifa asociada al grupo")
    public void getTarifa_CuandoGrupoEsCreadoConTarifaEspecifica_DevuelveTarifaCorrecta() throws ClubException {
        // Arrange
        double expectedTarifa = 100.0;
        Grupo g = new Grupo("codigo", "actividad", 20, 0, expectedTarifa);

        // Act
        double result = g.getTarifa();

        // Assert
        assertEquals(expectedTarifa, result, "El getter getTarifa no devuelve el valor esperado.");
    }


    @Test
    @DisplayName("El metodo plazasLibres devuelve las plazas libres que quedan respecto a los matriculados que ya hay en el grupo")
    public void plazasLibres_NumeroDePlazasMayorQueNumeroDeMatriculados_DevuelvePlazasLibrbesCorrectamente() throws ClubException {
        // Arrange  
        int plazas = 20;
        int matriculados = 10;
        Grupo g = new Grupo("codigo", "actividad", plazas, matriculados, 100.0);
        int expectedPlazasRestantes = 10;

        // Act
        double result = g.plazasLibres();

        // Assert
        assertEquals(expectedPlazasRestantes, result, "El metodo plazasLibrbes no devuelve el valor de plazas restantes esperado.");
    }


    @Test
    @DisplayName("El metodo actualizarPlazas con numero de plazas menor que matriculas devuelve una excepcion")
    public void actualizarPlazas_NumeroDePlazasEsMenorQueNumeroDeMatriculados_LanzaClubExcepcion() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 20, 100.0);
        int nPlazas = 10;
        
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            g.actualizarPlazas(nPlazas);
        });
    }

    @Test
    @DisplayName("El metodo actualizarPlazas con numero de plazas cero devuelve una excepcion")
    public void actualizarPlazas_NumeroDePlazasEsCero_LanzaClubException() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 20, 100.0);
        int nPlazas = 0;
        
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            g.actualizarPlazas(nPlazas);
        });
    }

    @Test
    @DisplayName("El metodo actualizarPlazas con numero de plazas negativo devuelve una excepcion")
    public void actualizarPlazas_NumeroDePlazasEsNegativo_LanzaClubException() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 20, 100.0);
        int nPlazas = -1;
        
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            g.actualizarPlazas(nPlazas);
        });
    }


    @Test
    @DisplayName("El metodo actualizarPlazas con numero de plazas mayor que numero de matriculados actualiza correctamente plazas")
    public void actualizarPlazas_NumeroDePlazasMayorQueMatriculados_ActualizaCorrectamenteNumeroDePlazas() throws ClubException {
        //Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 20, 100.0);
        int expectedNPlazas = 25;

        //Act
        g.actualizarPlazas(expectedNPlazas);
        int result = g.getPlazas();

        //Assert
        assertEquals(expectedNPlazas, result);

    }



    @Test
    @DisplayName("El metodo matricular con numero de matriculados es mayor que numero de plazas disponible devuelve una excepcion")
    public void matricular_NumeroDeMatriculadosEsMayorQueNumeroDePlazasLibres_LanzaClubException() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 10, 100.0);
        int nuevasMatriculas = 15;
        
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            g.matricular(nuevasMatriculas);
        });
    }


    @Test
    @DisplayName("El metodo matricular con numero de matriculados igual a cero devuelve una excepcion")
    public void matricular_NumeroDeMatriculadosEsCero_LanzaClubException() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 10, 100.0);
        int nuevasMatriculas = 0;
        
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            g.matricular(nuevasMatriculas);
        });
    }

    @Test
    @DisplayName("El metodo matricular con numero de matriculados negativo devuelve una excepcion")
    public void matricular_NumeroDeMatriculadosNegativo_LanzaClubException() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 10, 100.0);
        int nuevasMatriculas = -1;
        
        // Act & Assert
        Assertions.assertThrows(ClubException.class, () -> {
            // Aquí se intenta crear el objeto Grupo, lo cual debería lanzar una ClubException si los parámetros no son inválidos
            g.matricular(nuevasMatriculas);
        });
    }


    @Test
    @DisplayName("El metodo matricular con numero de matriculados menor que plazas disponibles actualiza correctamente matriculados")
    public void matricular_NumeroDeMatriculadosMenorQueNumeroDePlazas_ActualizaCorrectamenteNumeroDeMatriculados() throws ClubException {
        // Arrange
        Grupo g = new Grupo("codigo", "actividad", 20, 10, 100.0);
        int nuevasMatriculas = 5;
        int expectedMatriculas = 15;
        
        //Act
        g.matricular(nuevasMatriculas);
        int result = g.getMatriculados();

        //Assert
        assertEquals(expectedMatriculas, result);
        
    }


    @Test
    @DisplayName("El metodo toString devuelve la representacion en cadena correcta del grupo")
    public void toString_CuandoGrupoEsCreadoConParametrosEspecificos_DevuelveRepresentacionCadenaCorrecta() throws ClubException {
        // Arrange
        String codigo = "Actividad";
        String actividad = "Codigo";
        int nPlazas = 25;
        int nMatriculados = 20;
        double tarifa = 100.0;
        Grupo g = new Grupo(codigo, actividad, nPlazas, nMatriculados, tarifa);
        String expected = "("+ codigo + " - "+ actividad +" - " + tarifa + " euros "+ "- P:" + nPlazas +" - M:" + nMatriculados +")";

        // Act
        String result = g.toString();

        // Assert
        assertEquals(expected, result, "El método toString no devuelve la representación en cadena esperada.");
    }

    
    @Test
    @DisplayName("El metodo equals devuelve true cuando se comparan dos objetos con codigo y actividad iguales")
    public void equals_DosGruposConCodigosYActividadesIguales_DevuelveTrue() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo", "actividad", 20, 10, 100.0);
        Grupo g2 = new Grupo("CoDiGo", "AcTiViDaD", 30, 20, 200.0);
        
        // Act
        Boolean result = g1.equals(g2);

        // Assert
        assertTrue(result);
    }
    

    @Test
    @DisplayName("El metodo equals devuelve false cuando se comparan dos objetos con codigo y/o actividad diferente")
    public void equals_DosGruposConCodigosDistintos_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo1", "actividad", 20, 10, 100.0);
        Grupo g2 = new Grupo("codigo2", "actividad", 30, 20, 200.0);
        
        // Act
        boolean result = g1.equals(g2);

        // Assert
        assertFalse(result, "Se esperaba que los objetos Grupo fueran diferentes.");
    }

    
    @Test
    @DisplayName("Cuadno se crea un grupo, el metodo hashcode devuelve un hash consistente")
    public void hashCode_CuandoObjetoEsCreado_DevuelveHashCodeConsistente() throws ClubException {
        // Arrange
        String codigo = "codigo1";
        String actividad = "actividad";
        Grupo g1 = new Grupo(codigo, actividad, 20, 10, 100.0);
        int expectedHash = codigo.toUpperCase().hashCode()+actividad.toUpperCase().hashCode();

        // Act
        int result =  g1.hashCode();
        
        // Assert
        assertEquals(expectedHash, result);
    }

    @Test
    @DisplayName("Dos grupos que tienen solo sus codigos iguales deveulven el mismo hashCode")
    public void hashCode_DosGruposConCodigosIguales_DevuelvenMismoHashCode() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo", "actividad1", 20, 10, 100.0);
        Grupo g2 = new Grupo("codigo", "actividad2", 30, 15, 200.0); // Mismos código y actividad, diferentes otros valores
       
        //Act
        Boolean result = g1.equals(g2);

        assertFalse(result, "Se esperaba que los objetos no fueran iguales cuando la actividad es diferente.");
    }

    @Test
    @DisplayName("Dos grupos que tienen solo sus actividades iguales deveulven el mismo hashCode")
    public void hashCode_DosGruposConActividadesIguales_DevuelvenMismoHashCode() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo1", "actividad", 20, 10, 100.0);
        Grupo g2 = new Grupo("codigo2", "actividad", 30, 15, 200.0); // Mismos código y actividad, diferentes otros valores
       
        //Act
        Boolean result = g1.equals(g2);

        assertFalse(result, "Se esperaba que los objetos no fueran iguales cuando la actividad es diferente.");
    }


    @Test
    @DisplayName("Dos grupos que tienen iguales sus codigos y sus actividades devuelven el mismo hashCode")
    public void hashCode_DosGruposConCodigosYActividadesIguales_DevuelvenMismoHashCode() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo", "actividad", 20, 10, 100.0);
        Grupo g2 = new Grupo("CoDigO", "AcTiViDaD", 30, 15, 200.0); // Mismos código y actividad, diferentes otros valores

        // Act
        int h1 = g1.hashCode();
        int h2 = g2.hashCode();

        // Assert
        assertEquals(h1, h2, "Dos objetos iguales deben tener el mismo código hash.");
    }


    @Test
    @DisplayName("Dos grupos distintos devuelven diferente hashCode")
    public void hashCode_DosObjetosDiferentes_DevuelvenHashCodeDistinto() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo1", "actividad1", 20, 10, 100.0);
        Grupo g2 = new Grupo("codigo2", "actividad2", 30, 15, 200.0); // Mismos código y actividad, diferentes otros valores

        // Act
        int h1 = g1.hashCode();
        int h2 = g2.hashCode();

        // Assert
        assertNotEquals(h1, h2, "Dos objetos iguales deben tener el mismo código hash.");
    }

    

    @Test
    @DisplayName("Metodo equals con un un grupo y un objeto que no es un grupo devuelve false si no es una instancia de Grupo")
    public void equals_ConUnObjetoNoGrupo_DevuelveFalse() throws ClubException {
        // Arrange
        Grupo g1 = new Grupo("codigo1", "actividad1", 20, 10, 100.0);
        Object g2 = new Object();

        //Act
        Boolean result = g1.equals(g2);

        assertFalse(result);
    }

}
