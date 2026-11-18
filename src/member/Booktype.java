package member;

public class Booktype {
	int id;
	String type;
	public Booktype(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	public Booktype() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Booktype [id=" + id + ", type=" + type + "]";
	}
}
