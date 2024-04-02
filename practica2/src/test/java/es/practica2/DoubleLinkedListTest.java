/*  Westerhof Rodríguez Guillermo Alejandro
    Rueda Cabrera Pedro 
*/

package es.practica2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class DoubleLinkedListTest<T> {

    //primera sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo constructor")
    class ConstructorTests {

        @Test
        @DisplayName("Se comprueba que el constructor sin parametros inicializa su referencia a first como nulo")
        public void DoubleLinkedList_ConsTructorSinParametros_InicializaFirstNulo() {
            // Arrange
            LinkedNode<T> expectedResult = null;

            // Act
            DoubleLinkedList<T> list = new DoubleLinkedList<>();

            // Assert
            assertEquals(expectedResult, list.first());
            // tambien existe assertNull pero no seguiria el patron AAA
        }

        @Test
        @DisplayName("Se comprueba que el constructor sin parametros inicializa su referencia a last como nulo")
        public void DoubleLinkedList_ConsTructorSinParametros_InicializaLastNulo() {
            // Arrange
            LinkedNode<T> expectedResult = null;

            // Act
            DoubleLinkedList<T> list = new DoubleLinkedList<>();

            // Assert
            assertEquals(expectedResult, list.last());
            // tambien existe assertNull pero no seguiria el patron AAA
        }

        @Test
        @DisplayName("Se comprueba que el constructor sin parametros inicializa el tamaño a cero")
        public void DoubleLinkedList_ConsTructorSinParametros_InicializaTamañoCero() {
            // Arrange
            int expectedSize = 0;

            // Act
            DoubleLinkedList<T> list = new DoubleLinkedList<>();

            // Assert
            assertEquals(expectedSize, list.size());
        }
    }
    //end tests constructor


    //primera sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados a los metodos getter")
    class GettersTest {

        @Test
        @DisplayName("Cuando la lista esta vacia first devolvera null")
        public void First_LinkedListEstaVacia_DevuelveNull() {
            // Arrange
            DoubleLinkedList<T> list = new DoubleLinkedList<>();

            // Act & Assert
            assertNull(list.first());
        }

        @Test
        @DisplayName("Cuando la lista esta vacia last devolvera null")
        public void Last_LinkedListEstaVacia_DevuelveNull() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();

            //Act
            String result = list.first();

            //Assert
            assertNull(result);
        }

        @Test
        @DisplayName("Cuando la lista tiene elementos first devolvera el primero de la lista")
        public void First_LinkedListConVariosElementos_DevuelvePrimerElementoDeLinkedList() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";
            list.append(value1);
            list.append(value2);

            //Act
            String result = list.first();

            // Act & Assert
            assertEquals(value1, result);
        }
        
        @Test
        @DisplayName("Cuando la lista tiene elementos last devolvera el ultimo de la lista")
        public void Last_LinkedListConVariosElementos_DevuelveUltimoElementoDeLinkedList() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";
            list.append(value1);
            list.append(value2);

            //Act
            String result = list.last();

            // Act & Assert
            assertEquals(value2, result);
        }
    }
    //end tests getters


    //primera sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo prepend")
    class PrependTest {

        @Test
        @DisplayName("Se comprueba que al usar prepend cuando la lista esta vacia, el tamaño de la lista resultado es uno")
        public void Prepend_LinkedListEstaVacia_TamanyoLinkedListEsUno() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            int expectedSize = 1;
            String value = "Node1";

            // Act
            list.prepend(value);

            // Assert
            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("Se comprueba que al usar prepend cuando la lista esta vacia, first apunta a ese unico elemento de la lista")
        public void Prepend_LinkedListEstaVacia_ExtremoFirstApuntaAlUnicoNodo() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "Node1";

            // Act
            list.prepend(value);

            // Assert
            assertEquals(value, list.first());
        }

        @Test
        @DisplayName("Se comprueba que al usar prepend cuando la lista esta vacia, last apunta a ese unico elemento de la lista")
        public void Prepend_LinkedListEstaVacia_ExtremoLastApuntaAlUnicoNodo() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "Node1";

            // Act
            list.prepend(value);

            // Assert
            assertEquals(value, list.last());
        }
   
        @Test
        @DisplayName("Se comprueba que al usar prepend cuando la lista esta vacia, tanto first y last apuntan a ese unico elemento de la lista")
        public void Prepend_LinkedListEstaVacia_ExtremosFirstYLastApuntaAlUnicoNodo() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "Node1";

            // Act
            list.prepend(value);

            // Assert
            assertEquals(list.first(), list.last());
        }

        @Test
        @DisplayName("Se comprueba que al anteponer un segundo elemento, first apunta al nuevo nodo")
        public void Prepend_ConSegundoElemento_FirstApuntaAlNuevoNodo() {
            // Arrange
            DoubleLinkedList<String> lista = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";

            // Act
            lista.prepend(value1);
            lista.prepend(value2);

            // Assert
            assertEquals(value2, lista.first());
        }

        @Test
        @DisplayName("Se comprueba que al anteponer un segundo elemento, last apunta al nodo que ya estaba")
        public void Prepend_ConSegundoElemento_LastApuntaAlNodoQueYaEstaba() {
            // Arrange
            DoubleLinkedList<String> lista = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";

            // Act
            lista.prepend(value1);
            lista.prepend(value2);

            // Assert
            assertEquals(value1, lista.last());
        }
    }
    //end tests prepend


    //primera sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo append")
    class AppendTest {

        @Test
        @DisplayName("Se comprueba que al usar append cuando la lista esta vacia, el tamaño de la lista resultado es uno")
        public void Append_LinkedListEstaVacia_TamanyoLinkedListEsUno() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            int expectedSize = 1;
            String value = "Node1";

            // Act
            list.append(value);

            // Assert
            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("Se comprueba que al usar appebd cuando la lista esta vacia, first apunta a ese unico elemento de la lista")
        public void Append_LinkedListEstaVacia_ExtremoFirstApuntaAlUnicoNodo() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "Node1";

            // Act
            list.append(value);

            // Assert
            assertEquals(value, list.first());
        }

        @Test
        @DisplayName("Se comprueba que al usar append cuando la lista esta vacia, last apunta a ese unico elemento de la lista")
        public void Append_LinkedListEstaVacia_ExtremoLastApuntaAlUnicoNodo() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "Node1";

            // Act
            list.append(value);

            // Assert
            assertEquals(value, list.last());
        }
   

        @Test
        @DisplayName("Se comprueba que al usar append cuando la lista esta vacia, tanto first y last apuntan a ese unico elemento de la lista")
        public void Append_LinkedListEstaVacia_ExtremosFirstYLastApuntaAlUnicoNodo() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "Node1";

            // Act
            list.append(value);

            // Assert
            assertEquals(list.first(), list.last());
        }

        @Test
        @DisplayName("Se comprueba que al postponer un segundo elemento, last apunta al nuevo nodo")
        public void Append_ConSegundoElemento_LastApuntaAlNuevoNodo() {
            // Arrange
            DoubleLinkedList<String> lista = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";

            // Act
            lista.append(value1);  
            lista.append(value2); //segundo nodo "nuevo nodo"

            // Assert
            assertEquals(value2, lista.last());
        }

        @Test
        @DisplayName("Se comprueba que al postponer un segundo elemento, first apunta al nodo que ya estaba")
        public void Append_ConSegundoElemento_FirstApuntaAlNuevoNodo() {
            // Arrange
            DoubleLinkedList<String> lista = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";

            // Act
            lista.append(value1);
            lista.append(value2);

            // Assert
            assertEquals(value1, lista.first());
        }
    }
    //end tests append


    //primera sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo deleteFirst")
    class DeleteFirstTest {

        @Test
        @DisplayName("Si la lista esta vacia deleteFirst lanza DoubleLinkedQueueException")
        public void DeleteFirst_LinkedListEstaVacia_LanzaDoubleLinkedQueueException() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
           
            //Act & Assert
            assertThrows(DoubleLinkedQueueException.class, () -> list.deleteFirst());
        }

        @Test
        @DisplayName("Si la lista contiene un solo elemento y se borra ese elemento el tamaño final sera cero")
        public void DeleteFirst_LinkedListContieneUnElemento_DevuelveTamanyoCero() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            list.append(value1);    //da igual si usamos append o prepend para añadir un solo elemento a la lista vacia
            int expectedSize = 0;

            //Act
            list.deleteFirst();

            //Assert
            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("Si la lista contiene un solo elemento y se borra ese elemento first apuntara a null")
        public void DeleteFirst_LinkedListContieneUnElemento_FirstApuntaNull() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            list.append(value1);    //da igual si usamos append o prepend para añadir un solo elemento a la lista vacia

            //Act
            list.deleteFirst();

            //Assert
            assertNull(list.first());
        }

        @Test
        @DisplayName("Si la lista contiene un solo elemento y se borra ese elemento last apuntara a null")
        public void DeleteFirst_LinkedListContieneUnElemento_LastApuntaNull() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            list.append(value1);    //da igual si usamos append o prepend para añadir un solo elemento a la lista vacia

            //Act
            list.deleteFirst();

            //Assert
            assertNull(list.last());
        }


        @Test
        @DisplayName("Si la lista contiene dos elementos y se borra el primer elemento first apuntara al unico elemento que queda")
        public void DeleteFirst_LinkedListContieneDosElementos_FirstApuntaAlElementoRestante() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";
            list.append(value1);    
            list.append(value2);

            //Act
            list.deleteFirst();

            //Assert
            assertEquals(list.first(), value2);
        }

        @Test
        @DisplayName("Si la lista contiene dos elementos y se borra el primer elemento size se decrementa en uno")
        public void DeleteFirst_LinkedListContieneDosElementos_SizeSeDecrementaEnUno() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";
            list.append(value1);    
            list.append(value2);
            int expectedSize = 1;

            //Act
            list.deleteFirst();

            //Assert
            assertEquals(list.size(), expectedSize);
        }
    }
    //end tests deleteFirst


    //primera sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo deleteLast")
    class DeleteLastTest {

        @Test
        @DisplayName("Si la lista esta vacia deleteLast lanza DoubleLinkedQueueException")
        public void DeleteLast_LinkedListEstaVacia_LanzaDoubleLinkedQueueException() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            
            //Act & Assert
            assertThrows(DoubleLinkedQueueException.class, () -> list.deleteLast());
        }

        @Test
        @DisplayName("Si la lista contiene un solo elemento y se borra ese elemento, el tamaño final sera cero")
        public void DeleteLast_LinkedListContieneUnElemento_DevuelveTamanyoCero() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            list.append(value1);    //da igual si usamos append o prepend para añadir un solo elemento a la lista vacia
            int expectedSize = 0;

            //Act
            list.deleteLast();

            //Assert
            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("Si la lista contiene un solo elemento y se borra ese elemento first apuntara a null")
        public void DeleteLast_LinkedListContieneUnElemento_FirstApuntaNull() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            list.append(value1);    //da igual si usamos append o prepend para añadir un solo elemento a la lista vacia

            //Act
            list.deleteLast();

            //Assert
            assertNull(list.first());
        }

        @Test
        @DisplayName("Si la lista contiene un solo elemento y se borra ese elemento last apuntara a null")
        public void DeleteLast_LinkedListContieneUnElemento_LastApuntaNull() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            list.append(value1);    //da igual si usamos append o prepend para añadir un solo elemento a la lista vacia

            //Act
            list.deleteLast();

            //Assert
            assertNull(list.last());
        }

        @Test
        @DisplayName("Si la lista contiene dos elementos y se borra el primer elemento last apuntara al unico elemento que queda")
        public void DeleteLast_LinkedListContieneDosElementos_FirstApuntaAlElementoRestante() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";
            list.append(value1);    
            list.append(value2);

            //Act
            list.deleteLast();

            //Assert
            assertEquals(list.last(), value1);
        }

        @Test
        @DisplayName("Si la lista contiene dos elementos y se borra el primer elemento size se decrementa en uno")
        public void DeleteLast_LinkedListContieneDosElementos_SizeSeDecrementaEnUno() {
            // Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value1 = "Node1";
            String value2 = "Node2";
            list.append(value1);    
            list.append(value2);
            int expectedSize = 1;

            //Act
            list.deleteLast();

            //Assert
            assertEquals(list.size(), expectedSize);
        }
    }
    //end tests deleteLast

    //segunda sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo remove")
    class RemoveTest {
        @Test
        @DisplayName("Intento eliminar un elemento en una lista vacia por lo que devuelve una excepcion")
        public void Remove_LinkedListVacia_DevuelveDoubleLinkedQueueException(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            //Act & Assert
            assertThrows(DoubleLinkedQueueException.class, ()->list.remove(value),"Deberia lanzar una Excepcion, puesto que la lista es vacia");
            
        }

        @Test
        @DisplayName("Elimino un value en una lista que solo contiene este elemento")
        public void Remove_LinkedListConUnSoloElementoQueQuieroEliminar_EliminaElElemento(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            list.append(value);
            int expectedSize = 0;
            //Act 
            list.remove(value);
            //Assert
            assertEquals(expectedSize, list.size(),"El size debe ser cero, ya que hemos eliminado el unico elemento de la lista");
            assert(!list.contains(value));
            
        }

        @Test
        @DisplayName("Intento eliminar un elemento que no aparece en una lista de un elemento, no elimina ningun elemento")
        public void Remove_LinkedListConUnSoloElementoQueNoQuieroEliminar_NoEliminaElElemento(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            String value2 = "value que no esta en el array";
            list.append(value);
            int expectedSize = 1;
            //Act 
            list.remove(value2);
            //Assert
            assertEquals(expectedSize, list.size(),"El size debe ser 1, ya que no hemos eliminado el elemento");
            assert(list.contains(value));
            
        }

        @Test
        @DisplayName("Elimino el ultimo elemento de la lista")
        public void Remove_LinkedListConMasDeUnElementoSiendoELQueQueremosEliminarElPrimero_EliminaElElemento(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            String value2 = "value2";
            String value3 = "value3";
            list.append(value);
            list.append(value2);
            list.append(value3);
            int expectedSize = 2;
            //Act 
            list.remove(value);
            //Assert
            assertEquals(expectedSize, list.size(),"El size debe ser 2, ya que hemos eliminado el elemento de la lista");
            assert(!list.contains(value));
            
        }

        @Test
        @DisplayName("Elimino el ultimo elemento de la lista")
        public void Remove_LinkedListConMasDeUnElementoSiendoELQueQueremosEliminarElUltimo_EliminaElElemento(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            String value2 = "value2";
            String value3 = "value3";
            list.append(value);
            list.append(value2);
            list.append(value3);
            int expectedSize = 2;
            //Act 
            list.remove(value3);
            //Assert
            assertEquals(expectedSize, list.size(),"El size debe ser 2, ya que hemos eliminado el elemento de la lista");
            assert(!list.contains(value3));
            
        }

        @Test
        @DisplayName("Elimino uno de los elementos en un nodo no terminal de la lista")
        public void Remove_LinkedListConMasDeUnElementoQueremosEliminarUnIntermedio_EliminaElElemento(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            String value2 = "value2";
            String value3 = "value3";
            list.append(value);
            list.append(value2);
            list.append(value3);
            int expectedSize = 2;
            //Act 
            list.remove(value2);
            //Assert
            assertEquals(expectedSize, list.size(),"El size debe ser 2, ya que hemos eliminado el elemento de la lista");
            assert(!list.contains(value2));
            
        }

        @Test
        @DisplayName("Intento eliminar un elemento en una lista que no lo contiene")
        public void Remove_LinkedListConMasDeUnElementoQueremosEliminarUnElementoInexistente_NoEliminaNingunElemento(){
            //Arrange
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            String value = "value";
            String value2 = "value2";
            String value3 = "value3";
            String value4 = "no esta en el array";
            list.append(value);
            list.append(value2);
            list.append(value3);
            int expectedSize = 3;
            //Act 
            list.remove(value4);
            //Assert
            assertEquals(expectedSize, list.size(),"El size debe ser 3, ya que no hemos eliminado el elemento de la lista");
            
        }
    }

    //segunda sesion (practica 2)
    @Nested
    @DisplayName("Tests dedicados al metodo sort")
    class sortTest {
        @Test
        @DisplayName("Inserto una lista vacia que no se deberia modificar")
        public void sort_ListaCon0Elementos_NoEsOrdenada(){
            //Arrange 
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            Comparator<String> alphabeticalComparator = Comparator.naturalOrder();
            int expectedSize = 0;
            //Act
            list.sort(alphabeticalComparator);
            //Assert
            assertEquals(expectedSize, list.size(),"No debe haber elementos en la lista"); 
            assertThrows(IndexOutOfBoundsException.class, ()->list.get(0));
           
        }

        @Test
        @DisplayName("Inserto una lista con un solo elemento que no puede ser ordenada")
        public void sort_ListaCon1Elementos_NoEsOrdenada(){
            //Arrange 
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            Comparator<String> alphabeticalComparator = Comparator.naturalOrder();
            int expectedSize = 1;
            list.append("value");
            //Act
            list.sort(alphabeticalComparator);
            //Assert
            assertEquals(expectedSize, list.size(),"Debe haber un solo elemento en la lista"); 
            assertEquals("value", list.get(0),"El unico elemento debería ser value");
            
           
        }

        @Test
        @DisplayName("Inserto una lista desordenada de elementos para ordenar")
        public void sort_ListaConVariosElementosDesordenados_EsOrdenada(){
            //Arrange 
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            list.append("z_Zoro");
            list.append("e_");
            list.append("p_");
            Comparator<String> alphabeticalComparator = Comparator.naturalOrder();
            //Act
            list.sort(alphabeticalComparator);
            //Assert
            assertEquals("e_", list.get(0),"El primer elemento debería ser e_"); 
            assertEquals("p_", list.get(1),"El segundo elemento debería ser p_"); 
            assertEquals("z_Zoro", list.get(2),"El tercer elemento debería ser z_Zoro");
        }

        @Test
        @DisplayName("Inserto una lista ya ordenada de elementos por lo que su orden no debe ser modificado")
        public void sort_ListaConVariosElementosYaOrdenados_NoDebeVariarSuOrden(){
            //Arrange 
            DoubleLinkedList<String> list = new DoubleLinkedList<>();
            list.append("e_");
            list.append("p_");
            list.append("z_Zoro");
            Comparator<String> alphabeticalComparator = Comparator.naturalOrder();
            //Act
            list.sort(alphabeticalComparator);
            //Assert
            assertEquals("e_", list.get(0),"El primer elemento debería ser e_"); 
            assertEquals("p_", list.get(1),"El segundo elemento debería ser p_"); 
            assertEquals("z_Zoro", list.get(2),"El tercer elemento debería ser z_Zoro");
        }

        

    }



}
