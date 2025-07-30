package es.cic.curso25.proy009.exceptiones;

public class NotFoundException extends RuntimeException {

    public NotFoundException() { 
        super("Registro no encontrado");
    }

    public NotFoundException(String mensaje){
        super(mensaje);
    }

    public NotFoundException(String mensaje, Throwable throwable){
        super(mensaje, throwable);
    }

}
