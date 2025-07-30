package es.cic.curso25.proy009.configuration;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.cic.curso25.proy009.exceptiones.CreacionSecurityException;
import es.cic.curso25.proy009.exceptiones.ModificacionSecurityException;

public class ControllerAdvice {
  //Creación - Status code 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CreacionSecurityException.class)
    public void controlCreacion(){

    }

    //Lectura - Status code 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void controlLectura(){

    }

    //Modificación - Status code 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ModificacionSecurityException.class)
    public void controlModificacion(){

    }
}
