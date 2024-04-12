package org.mps.ronqi2;

import org.mps.dispositivo.Dispositivo;

public abstract class RonQI2 {
    protected Dispositivo disp;

    /* 
     * Inicializa el sistema ronQI2 configurando los dos sensores del dispositivo conectado.
    */
    public boolean inicializar(){
        boolean result = false;
        if (disp.conectarSensorPresion()){
            boolean confPresion = disp.configurarSensorPresion();
            if (disp.conectarSensorSonido()){
                result = disp.configurarSensorSonido() && confPresion;
            }
        }
        return result;   
    }

    /* 
     * Lee y almacena las lecturas de presion y sonido del dispositivo.
    */
    public abstract void obtenerNuevaLectura();

    //error encontrado: no se comprueba que d sea distinto de nulo. (si fuese nulo, en inicializar daria un nullPointerException)
    public void anyadirDispositivo(Dispositivo d) {
        if (d == null) 
            throw new IllegalArgumentException("Dispositivo es nulo");
        disp = d;
    }

    /* 
     * Reconecta el dispositivo cuando esta desconectado.
    */
    public boolean reconectar(){
        boolean result = false;
        if (!disp.estaConectado()){
            result = disp.conectarSensorPresion() && disp.conectarSensorSonido();
        }
        return result;
    }

    /* 
     * Evalua la apnea del suenyo con las medidas recogidas.
    */
    public abstract boolean evaluarApneaSuenyo();
    
    /* 
     * Lee y almacena las lecturas de presion y sonido del dispositivo.
    */
    public boolean estaConectado(){
        return disp.estaConectado();
    }    
}
