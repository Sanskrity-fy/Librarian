package member;

public class Book {
	int id;
	String type;
	String name;
	float prince;
	int count;
	String publish_house;
	public Book() {
		super();
	}
	public Book(int id, String type, String name, float prince, int count, String publish_house) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.prince = prince;
		this.count = count;
		this.publish_house = publish_house;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrince() {
		return prince;
	}
	public void setPrince(float prince) {
		this.prince = prince;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPublish_house() {
		return publish_house;
	}
	public void setPublish_house(String publish_house) {
		this.publish_house = publish_house;
	}
	@Override
	public String toString() {
		return "book [id=" + id + ", type=" + type + ", name=" + name + ", prince=" + prince + ", count=" + count
				+ ", publish_house=" + publish_house + "]";
	}
}
