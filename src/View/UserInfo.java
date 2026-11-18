package View;

public class UserInfo {
	    private static String id;
	    private static String name; 
	    private String type;
	    // 构造函数
	    public UserInfo(String id, String name, String type) {
	        this.id = id;
	        this.name = name;
	        this.type = type;
	    }
	    // Getter 方法
	    public static String getId() {
	        return id;
	    }

	    public static String getName() {
	        return name;
	    }

	    public String getType() {
	        return type;
	    }	    
	}

