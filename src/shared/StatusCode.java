package shared;

public enum StatusCode {
	NO_OPERATION (0),
	MESSAGE_SENT (1);
	
	public int statusCode;
	StatusCode(int statusCode)
	{
		 this.statusCode = statusCode;
	}
}

