package member;

public class Borrower {
	int id;
	String sex;
	int password;
	String name;
	public Borrower(int id, String sex, int password, String name) {
		super();
		this.id = id;
		this.sex = sex;
		this.password = password;
		this.name = name;
	}
	public Borrower() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getPassword() {
		return password;
	}
	public void setPassword(int password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "borrower [id=" + id + ", sex=" + sex + ", password=" + password + ", name=" + name + "]";
	}
}
