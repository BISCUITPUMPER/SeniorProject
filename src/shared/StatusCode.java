package shared;

public enum StatusCode {
	SOCK_CONNECT (0),
	NO_OPERATION (1),
	MESSAGE_SENT (2),
	MOUSE_MOVE (3),
	MOUSE_SCROLL (4),
	KEY_PRESS (5),
	KEY_RELEASE (6),
	MOUSE_PRESS (7),
	MOUSE_RELEASE (8),
	PROG_EXECUTE (9),
	SCREEN_REQ (10),
	SCREEN_SENT (11),
	SOCK_DISCONNECT (12);
	
	public int statusCode;
	StatusCode(int statusCode)
	{
		 this.statusCode = statusCode;
	}
}
