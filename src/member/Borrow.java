package member;

public class Borrow {
	int id;
	int B_id;
	String B_name;
	String Book_name;
	String B_date;
	String R_date;
	public Borrow(int id, int b_id, String b_name, String book_name, String b_date, String r_date) {
		super();
		this.id = id;
		B_id = b_id;
		B_name = b_name;
		Book_name = book_name;
		B_date = b_date;
		R_date = r_date;
	}
	public Borrow() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getB_id() {
		return B_id;
	}
	public void setB_id(int b_id) {
		B_id = b_id;
	}
	public String getB_name() {
		return B_name;
	}
	public void setB_name(String b_name) {
		B_name = b_name;
	}
	public String getBook_name() {
		return Book_name;
	}
	public void setBook_name(String book_name) {
		Book_name = book_name;
	}
	public String getB_date() {
		return B_date;
	}
	public void setB_date(String b_date) {
		B_date = b_date;
	}
	public String getR_date() {
		return R_date;
	}
	public void setR_date(String r_date) {
		R_date = r_date;
	}
	@Override
	public String toString() {
		return "Borrow [id=" + id + ", B_id=" + B_id + ", B_name=" + B_name + ", Book_name=" + Book_name + ", B_date="
				+ B_date + ", R_date=" + R_date + "]";
	}
	
}
