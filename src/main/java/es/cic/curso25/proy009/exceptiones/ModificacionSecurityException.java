package es.cic.curso25.proy009.exceptiones;

public class ModificacionSecurityException extends RuntimeException{

    public ModificacionSecurityException(){
        super("Intento de modificación en el update");
    }

    public ModificacionSecurityException(String mensaje){
        super(mensaje);
    }

    public ModificacionSecurityException(String mensaje, Throwable throwable){
        super(mensaje, throwable);
    }

}
