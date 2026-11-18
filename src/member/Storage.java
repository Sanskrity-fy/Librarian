package member;

public class Storage {
	int S_id;
	int Book_id;
	int Quantity;
	public Storage() {
		super();
	}
	public Storage(int s_id, int book_id, int quantity) {
		super();
		S_id = s_id;
		Book_id = book_id;
		Quantity = quantity;
	}
	public int getS_id() {
		return S_id;
	}
	public void setS_id(int s_id) {
		S_id = s_id;
	}
	public int getBook_id() {
		return Book_id;
	}
	public void setBook_id(int book_id) {
		Book_id = book_id;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	@Override
	public String toString() {
		return "Storage [S_id=" + S_id + ", Book_id=" + Book_id + ", Quantity=" + Quantity + "]";
	}
}
