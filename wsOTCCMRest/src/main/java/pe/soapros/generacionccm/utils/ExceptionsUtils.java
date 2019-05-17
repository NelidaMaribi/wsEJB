package pe.soapros.generacionccm.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.soapros.generacionccm.exception.ExceptionResponse;

public class ExceptionsUtils {

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public ExceptionResponse setException(String message, String source) {
		List<String> errores = new ArrayList<String>();
		errores.add(source);
		return new ExceptionResponse(format.format(new Date()), message, errores);
	}
	
	public ExceptionResponse setException(Exception e, String source) {
		List<String> errores = new ArrayList<String>();
		errores.add(source);
		errores.add(e.getLocalizedMessage());
		return new ExceptionResponse(format.format(new Date()), e.getMessage(), errores);
	}
}
