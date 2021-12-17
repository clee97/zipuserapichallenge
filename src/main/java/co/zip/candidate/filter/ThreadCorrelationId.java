package co.zip.candidate.filter;

import java.util.UUID;

public class ThreadCorrelationId {
	public static final String CORRELATION_ID_HEADER = "correlationId";
    private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();


    public static String getId() { 
    	return CORRELATION_ID.get(); 
	}

    public static void setId(String correlationId) { 
    	CORRELATION_ID.set(correlationId); 
	}
    
    public static void generate() {
    	CORRELATION_ID.set(UUID.randomUUID().toString());
    }
}
