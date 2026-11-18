package member;

public class Librarian {
	int id;
	String name;
	String sex;
	String password;
	public Librarian() {
		super();
	}
	public Librarian(int id, String name, String sex, String password) {
		super();
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Librarian [id=" + id + ", name=" + name + ", sex=" + sex + ", password=" + password + "]";
	}
}
