package zyd.zhihu.async;

public enum EventType {
	LIKE(0),
	COMMENT(1),
	LOGIN(2),
	FOLLOW(3),
	ADD_QUESTION(4);
	
	private int val;
	
	EventType(int val) {
		this.val = val;
	}
	
	public int getValue() {
		return this.val;
	}
}
