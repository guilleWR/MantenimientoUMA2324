/*
 * INTEGRANTES DEL GRUPO: Rueda Cabrera Pedro
 *                        Westerhof Rodríguez Guillermo Alejandro
*/

package clubdeportivo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ClubDeportivoTest {

    @Test
    @DisplayName("Se comprueba que el club se cree correctamente")
    public void ClubDeportivo_ConstructorConNombre_SeCreaCorrectamente() throws ClubException{
        //Arrange
        String nombre = "Inazuma Eleven";

        //Act
        ClubDeportivo aux = new ClubDeportivo(nombre);

        //Asserts
        assertEquals("Inazuma Eleven --> [  ]", aux.toString(), "El grupo no se ha creado correctamente");
    }

    @Test
    @DisplayName("Se comprueba que el contructor lance ClubException al introducir tamaño total de grupos negativo")
    public void ClubDeportivo_ConstructorConNumeroDeGruposNegativos_LanzaClubException(){
        //Arrangue
        String nombre = "nombre";
        int tam = -1;

        //Act & Assert
        assertThrows(ClubException.class, ()->new ClubDeportivo(nombre,tam),"El metodo no ha devuelto ClubException al introducir grupos negativos");
    }

    @Test
    @DisplayName("Se comprueba que el contructor lance ClubException al introducir 0 grupos")
    public void ClubDeportivo_ConstructorConCeroGrupos_LanzaClubException(){
        //Assert
        String nombre = "nombre";
        int tam = 0;
        
        //Act & Assert
        assertThrows(ClubException.class, ()->new ClubDeportivo(nombre,tam),"El metodo no ha devuelto ClubException al introducir 0 grupos");
    }

    @Test
    @DisplayName("Se comprueba que el contructor lance ClubException al introducir un nombre null")
    public void ClubDeportivo_ConstructorConNombreNull_LanzaClubException(){
        //Arrange 
        String nombre = null;

        //Act & Assert
        assertThrows(Exception.class, ()->new ClubDeportivo(nombre),"El metodo no ha devuelto ClubException al introducir un nombre como Null");
    }

    @Test
    @DisplayName("Se comprueba que el contructor devuelva ClubException al introducir un nombre vacio")
    public void ClubDeportivo_ConstructorConNombreVacio_LanzaClubException(){
        //Arrange 
        String nombre = " ";

        //Act & Assert
        assertThrows(Exception.class, ()->new ClubDeportivo(nombre),"El metodo no ha devuelto ClubException al introducir un nombre vacio");
    }

    @Test
    @DisplayName("Se comprueba que se añade correctamente una actividad introduciendo los datos comoun array de strings")
    public void anyadirActividad_DatosEnStringArray_AnyadeActividadCorrectamente() throws ClubException{
        //Arrange
        String[] datos = {"1234","Ver One Piece","24","23","70"};
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        //Act
        aux.anyadirActividad(datos);
        //Assert
        assertEquals(aux.toString(), "Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:24 - M:23) ]","No se ha añadido correctamente la actividad");
    }

    @Test
    @DisplayName("Se comprueba que el metodo permite añadir mas de una actividad")
    public void anyadirActividad_VariasActividades_AnyadTodasLasActividadesCorrectamente() throws ClubException {
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Inazuma Eleven");
        Grupo g1 = new Grupo("1234", "Ver One Piece", 24, 23, 70);
        Grupo g2 = new Grupo("1235", "Ver One Piece", 30, 23, 70); // Mismo código y actividad, diferente número de plazas
        Grupo g3 = new Grupo("1236", "Ver One Piece", 25, 23, 70); 
        //Act
        club.anyadirActividad(g1);
        club.anyadirActividad(g2);
        club.anyadirActividad(g3);// Intenta añadir duplicado, debería actualizar plazas de g1

        //Assert
        assertEquals("Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:24 - M:23), (1235 - Ver One Piece - 70.0 euros - P:30 - M:23), (1236 - Ver One Piece - 70.0 euros - P:25 - M:23) ]", club.toString(), "El grupo existente debería tener sus plazas actualizadaDeberian haberse añadido los tres grupos.");
    }

    @Test
    @DisplayName("Se comprueba que se añade correctamente una actividad introduciendo un grupo")
    public void AnyadirActividad_GrupoComoParametro_AnyadeActividadCorrectamente() throws ClubException{
        //Arrange
        Grupo g1 = new Grupo("1234","Ver One Piece",24,23,70.0);
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        //Act
        aux.anyadirActividad(g1);
        //Assert
        assertEquals(aux.toString(), "Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:24 - M:23) ]","No se ha añadido correctamente la actividad");
    }

    @Test
    @DisplayName("Se comprueba que se Lanza ClubException al intentar añadir un grupo null")
    public void anyadirActividad_GrupoEsNulo_LanzaClubException() throws ClubException {
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Inazuma Eleven");
        //Act & Assert
        assertThrows(ClubException.class, () -> club.anyadirActividad((Grupo)null), "ERROR: el grupo es nulo");
    }

    @ParameterizedTest
    @MethodSource("parametrosanyadirActividadNoPermitidos")
    @DisplayName("Se comprueba que el metodo anyadirActividad lanza ClubException con parametros invalidos")
    public void AnyadirActividad_NoPermitidaDesdeString_DevuelveClubException(String codigo, String nombre, String plazas, String matriculados, String tarifa ) throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
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
    @DisplayName("Se comprueba que añadir un grupo null lance una ClubException")
    public void AnyadirActividad_ConGrupoNulo_LanzaClubException() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        Grupo g1 = null;
        //Act & Assert
        assertThrows(ClubException.class,()-> aux.anyadirActividad(g1),"El metodo permite añadir grupos nulos");
    }

    @Test
    @DisplayName("Se comprueba que no se puede añadir mas grupos que la capacidad del array de grupos de un club")
    public void AnyadirActividad_NumeroDeGruposConActividadesDistintasEsMayorQueMaximoPermitidoEnClub_LanzaClubException() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven",2);
        Grupo g1 = new Grupo("1234","Ver One Piece",20,3,70);
        Grupo g2 = new Grupo("1235", "Actividad 2", 30, 23, 70);
        Grupo g3 = new Grupo("1236", "Ver One Piece", 25, 23, 70);
        //Act
        aux.anyadirActividad(g1);
        aux.anyadirActividad(g2);
        //Assert
        assertThrows(ClubException.class,()-> aux.anyadirActividad(g3),"El club no deberia admitir más grupos");
    }

    @Test
    @DisplayName("No permite añadir grupos duplicados, sino que actualiza las plazas del grupo existente")
    public void anyadirActividad_GruposIgualesConDistintoNumeroDeplazas_ActualizaPlazasDelGrupoExistente() throws ClubException {
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Inazuma Eleven");
        Grupo g1 = new Grupo("1234", "Ver One Piece", 24, 23, 70);
        Grupo g2 = new Grupo("1234", "Ver One Piece", 30, 23, 70); // Mismo código y actividad, diferente número de plazas

        //Act
        club.anyadirActividad(g1);
        club.anyadirActividad(g2); // Intenta añadir duplicado, debe actualizar plazas de g1

        //Assert
        assertEquals("Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:30 - M:23) ]", club.toString(), "El grupo existente debería tener sus plazas actualizadas.");
    }

    @Test
    @DisplayName("Se comprueba que se devuelve el numero correcto de plazas libres")
    public void plazasLibres_ConActividadExistente_DevuelveNumeroCorrecto() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,10,70)); 

        //Act
        int plibres = aux.plazasLibres("Ver One Piece");

        //Assert
        assertEquals(10, plibres,"Deberia haber 10 plazas libres");
    }

    @Test
    @DisplayName("Se comprueba que se devuelve -1 al no existir la actividad")
    public void PlazasLibres_ActividadNoExistente_DevuelveNumeroNegativo() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,10,70)); 
        //Act
        int plibres = aux.plazasLibres("Noexiste");
        //Assert
        assertEquals(-1, plibres,"La actividad deberia devolver negativo al no existir");
    }

    @Test
    @DisplayName("Se comprueba que se lanza ClubException al insertar una actividad null")
    public void PlazasLibres_ActividadNull_LanzaClubException() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,10,70)); 
        //Act & Assert
        assertThrows(ClubException.class, ()->aux.plazasLibres(null),"El metodo deberia lanzar ClubException al pasar una actividad null");
    }

    

    @Test
    @DisplayName("Se comprueba que matricula correctamente")
    public void matricular_ConEspacioDisponibleYActividadExistente_MatriculaCorrectamente() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,0,70)); 
        aux.anyadirActividad(new Grupo("5","furbo",20,0,70));       
         //Act
        aux.matricular("Ver One Piece",10);
        //Assert
        assertEquals("Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:20 - M:10), (5 - furbo - 70.0 euros - P:20 - M:0) ]", aux.toString(), "No se han matriculado correctamente en la actividad");
    }

    @Test
    @DisplayName("Se comprueba que se lanza ClubException al matricular mas personas que plazas")
    public void matricular_ConMasPersonasQuePlazasDisponibles_LanzaClubException() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,0,70)); 
        //Act & Assert
        assertThrows(ClubException.class, ()->aux.matricular("Ver One Piece", 30),"No deberian estar matriculados");
    }

    @Test
    @DisplayName("Se comprueba que al matricular con cero personas no se realiza la matriculacion")
    public void matricular_ConCeroPersonas_NoRealizaMatriculacion() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,0,70)); 

        //Act
        aux.matricular("Ver One Piece", 0); //0 personas

        //Assert
        assertEquals("Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:20 - M:0) ]", aux.toString(), "No deberia haberse matriculado nadie en la actividad");
    }

    @Test
    @DisplayName("Se comprueba que matricular un un numero de personas igual a la maxima capacidad matricula a todos correctamente")
    public void Matricular_NumeroDePersonasIgualANumeroDePlazasMaximo_MatriculaCorrectamente() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,0,70)); 

        //Act
        aux.matricular("Ver One Piece", 20); //numero de personas igual a la maxima capacidad (plazas totales)

        //Act
        assertEquals("Inazuma Eleven --> [ (1234 - Ver One Piece - 70.0 euros - P:20 - M:20) ]", aux.toString(), "No deberia haberse matriculado nadie en la actividad");
    }

    @Test
    @DisplayName("Se comprueba que matricular un numero de personas negativo lanza una clubExcepcion")
    public void Matricular_NumeroDePersonasNegativas_LanzaClubException() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,0,70)); 

        // Act & Assert
        assertThrows(ClubException.class, ()->aux.matricular("Ver One Piece", -1),"No se puede matricular un numero negativo de personas");
    }

    @Test
    @DisplayName("Se comprueba que se lanza ClubException al matricular mas personas que plazas")
    public void Matricular_actividadNoExistente_LanzaClubException() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,0,70)); 
        
        //Act & Assert
        assertThrows(ClubException.class, ()->aux.matricular("no existo", 30),"No deberian estar matriculados");
    }

    @Test
    @DisplayName("Se comprueba que los ingresos sean coherentes con el numero de matriculados y grupos")
    public void ingresos_ConActividadesYMatriculados_CalculaIngresosCorrectamente() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        aux.anyadirActividad(new Grupo("1234","Ver One Piece",20,10,70));
        aux.anyadirActividad(new Grupo("1235","Ver One Piece",30,20,30));
        aux.anyadirActividad(new Grupo("1236","Ver One Piece",40,15,55));
        //Act
        double ingresosEsperados = (10 * 70.0) + (20 * 30.0) + (15 * 55.0);
            
        //Assert
        assertEquals(ingresosEsperados, aux.ingresos(), 0.001,"No se generan correctamente los ingresos");
    }

    @Test
    @DisplayName("Se comprueba que el metodo ingresos devuelva cero si un club no tiene ningun grupo")
    public void ingresos_ClubConCeroGrupos_DevuelveCantidadCero() throws ClubException{
        //Arrange
        ClubDeportivo aux = new ClubDeportivo("Inazuma Eleven");
        //Act
        double ingresosEsperados = 0;    
        //Assert
        assertEquals(ingresosEsperados, aux.ingresos(), 0.001,"No se generan correctamente los ingresos");
    }

    @Test
    @DisplayName("toString devuelve correctamente la representación de cadena de un ClubDeportivo sin grupos")
    public void toString_ClubDeportivoSinGrupos_DevuelveCadenaCorrecta() throws ClubException {
        // Arrange
        ClubDeportivo club = new ClubDeportivo("Club Sin Grupos");
        String expected = "Club Sin Grupos --> [  ]";

        // Act
        String result = club.toString();

        // Assert
    
        assertEquals(expected, result, "El método toString no maneja correctamente un ClubDeportivo sin grupos.");
    }

    @Test
    @DisplayName("toString devuelve correctamente la representación de cadena de un ClubDeportivo con grupos")
    public void toString_ClubDeportivoConGrupos_DevuelveCadenaCorrecta() throws ClubException {
        // Arrange
        ClubDeportivo club = new ClubDeportivo("Club Con Grupos");
        String expected = "Club Con Grupos --> [ (codigo1 - Fútbol - 50.0 euros - P:25 - M:20) ]";
        Grupo grupo = new Grupo("codigo1", "Fútbol", 25, 20, 50.0);
        club.anyadirActividad(grupo);

        // Act
        String result = club.toString();

        // Assert
        assertEquals(expected, result, "El método toString no maneja correctamente un ClubDeportivo con grupos.");
    }
}
