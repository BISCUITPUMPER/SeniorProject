package shared;

public enum StatusCode {
	SOCK_CONNECT (0),
	NO_OPERATION (1),
	MESSAGE_SENT (2),
	MOUSE_MOVE (3),
	MOUSE_SCROLL (4),
	KEY_PRESS (5),
	KEY_RELEASE (6);
	
	public int statusCode;
	StatusCode(int statusCode)
	{
		 this.statusCode = statusCode;
	}
}

