package shared;

public enum StatusCode {
	SOCK_CONNECT (0),
	NO_OPERATION (1),
	MESSAGE_SENT (2),
	MOUSE_MOVE (3);
	
	public int statusCode;
	StatusCode(int statusCode)
	{
		 this.statusCode = statusCode;
	}
}

