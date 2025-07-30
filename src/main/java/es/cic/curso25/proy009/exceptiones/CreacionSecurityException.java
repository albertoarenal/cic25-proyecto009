package es.cic.curso25.proy009.exceptiones;

public class CreacionSecurityException extends RuntimeException {

    public CreacionSecurityException() { 
        super("Intento de creación en el create");
    }

    public CreacionSecurityException(String mensaje){
        super(mensaje);
    }

    public CreacionSecurityException(String mensaje, Throwable throwable){
        super(mensaje,throwable);
    }

}
