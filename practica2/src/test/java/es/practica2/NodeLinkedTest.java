/*  Westerhof Rodríguez Guillermo Alejandro
    Rueda Cabrera Pedro 
*/
package es.practica2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NodeLinkedTest<T>{

    @Test
    @DisplayName("Se comprueba que el constructor crea correctamente un Node con previus y next apuntando a null")
    public void LinkedNode_ConstructorNodo_CreaNodoSinRelacionesCorrectamente(){
        //Arrange 
        Object aux = new Object();
        // Act & Assert
        assertDoesNotThrow(()-> new LinkedNode<Object>(aux, null, null), "El nodo deberia haberse creado correctamente");
    }

    @Test
    @DisplayName("Se comprueba que el constructor crea correctamente un Node con sus relaciones previus y next apuntando a otros nodos correctamente")
    public void LinkedNode_ConstructorNodo_CreaNodoConRelacionesPreviousYNextCorrectamente(){
        //Arrange 
        Object aux = new Object();
        // Act 
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node3 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, node1, node3);
        //Assert
        assertEquals(node1, node2.getPrevious(),"El node1 deberia ser el previous de Node2");
        assertEquals(node3, node2.getNext(),"El node3 deberia ser el next de Node2");

    }
    
    @Test
    @DisplayName("El metodo devuelve el Item del nodo asociado")
    public void getItem_NodeConItem_DevuelveElItemDelNodo(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        // Act & Assert
        assertEquals(aux, node1.getItem(),"Deberia haber devuelto el item asociado"); 
    }

    @Test
    @DisplayName("El metodo actualiza el Item del nodo asociado")
    public void setItem_NodeConItem_ActualizaElItemDelNodo(){
        //Arrange 
        Object aux = new Object();
        Object aux2 = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        // Act 
        node1.setItem(aux2);
        //Assert
        assertEquals(aux2, node1.getItem(),"Deberia haber actualizado el item del nodo asociado"); 
    }

    @Test
    @DisplayName("El metodo devuelve el nodo Previous del nodo asociado")
    public void getPrevious_NodeConPrevious_DevuelveElPreviousDelNodo(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, node1, null);
        // Act & Assert
        assertEquals(node1, node2.getPrevious(),"Deberia haber devuelto el nodo previous asociado"); 
    }

    @Test
    @DisplayName("El metodo actualiza el Previous del nodo asociado")
    public void setPrevious_NodeConPrevious_ActualizaElPreviousDelNodo(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, null);
        // Act 
        node1.setPrevious(node2);
        //Assert
        assertEquals(node2, node1.getPrevious(),"Deberia haber actualizado el previous del nodo asociado"); 
    }

    @Test
    @DisplayName("El metodo devuelve el nodo Nest del nodo asociado")
    public void getNext_NodoConNext_DevuelveElNextDelNodo(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, node1);
        // Act & Assert
        assertEquals(node1, node2.getNext(),"Deberia haber devuelto el nodo next asociado"); 
    }

    @Test
    @DisplayName("El metodo actualiza el next del nodo asociado")
    public void setNext_NodoConNext_ActualizaElNextDelNodo(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, node1);
        // Act 
        node1.setNext(node2);
        //Assert
        assertEquals(node2, node1.getNext(),"Deberia haber actualizado el next del nodo asociado"); 
    }

    @Test
    @DisplayName("Compruebo que el metodo devuelve true cuando el nodo es primero")
    public void isFirstNode_EnPrimerNodo_Devuelvetrue(){
        //Arrange 
        Object aux = new Object();
        // Act 
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        //Assert
        assert(node1.isFirstNode()); 
    }

    @Test
    @DisplayName("Compruebo que el metodo devuelve false cuando el nodo es primero")
    public void isFirstNode_EnNodoNoFirst_DevuelveFalse(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, node1);   
        // Act 
        node1.setPrevious(node2);
        //Assert
        assert(!node1.isFirstNode()); 
    }

    @Test
    @DisplayName("Compruebo que el metodo devuelve true cuando el nodo es ultimo")
    public void isLastNode_EnUltimoNodo_Devuelvetrue(){
        //Arrange 
        Object aux = new Object();
        // Act 
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        //Assert
        assert(node1.isLastNode()); 
    }

    @Test
    @DisplayName("Compruebo que el metodo devuelve false cueando el nodo no es ultimo")
    public void isLastNode_EnNodoNoLast_DevuelveFalse(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, node1);   
        // Act 
        node1.setNext(node2);
        //Assert
        assert(!node1.isLastNode()); 
    }

    @Test
    @DisplayName("El metodo devuelve false al insertar un node first")
    public void isNotATerminalNode_EnPrimerNodo_Devuelvetrue(){
        //Arrange 
        Object aux = new Object();
        // Act 
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, node1); 
        //Assert
        assert(!node2.isNotATerminalNode()); 
    }

    @Test
    @DisplayName("El metodo devuelve true al insertar un node no terminal")
    public void isNotATerminalNode_EnNodoNoTerminal_DevuelveFalse(){
        //Arrange 
        Object aux = new Object();
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, null, node1);   
        // Act 
        node1.setPrevious(node2);
        node1.setNext(node2);
        //Assert
        assert(node1.isNotATerminalNode()); 
    }

    @Test
    @DisplayName("El metodo devuelve false al insertar un node last")
    public void isNotATerminalNode_EnUltimoNodo_Devuelvetrue(){
        //Arrange 
        Object aux = new Object();
        // Act 
        LinkedNode<Object> node1 = new LinkedNode<Object>(aux, null, null);
        LinkedNode<Object> node2 = new LinkedNode<Object>(aux, node1, null); 
        //Assert
        assert(!node2.isNotATerminalNode()); 
    }

    


    





}