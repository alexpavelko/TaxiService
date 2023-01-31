package exception;

public class ServiceException extends Exception{

    public ServiceException(){
        super();
    }

    public ServiceException(Throwable cause){
        super(cause);
    }
}
