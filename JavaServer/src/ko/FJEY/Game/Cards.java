package ko.FJEY.Game;

public enum Cards {
	
	
	
	DIAMOND_A("DIAMOND", 14 , "dA"),
	DIAMOND_2("DIAMOND", 2 , "d2"),
	DIAMOND_3("DIAMOND", 3 , "d3"),
	DIAMOND_4("DIAMOND", 4 , "d4"),
	DIAMOND_5("DIAMOND", 5 , "d5"),
	DIAMOND_6("DIAMOND", 6 , "d6"),
	DIAMOND_7("DIAMOND", 7 , "d7"),
	DIAMOND_8("DIAMOND", 8 , "d8"),
	DIAMOND_9("DIAMOND", 9 , "d9"),
	DIAMOND_10("DIAMOND", 10 , "d10"),
	DIAMOND_J("DIAMOND", 11 , "dJ"),
	DIAMOND_Q("DIAMOND", 12 , "dQ"),
	DIAMOND_K("DIAMOND", 13 , "dK"),
	CLOVER_A("CLOVER", 14 , "cA"),
	CLOVER_2("CLOVER", 2 , "c2"),
	CLOVER_3("CLOVER", 3 , "c3"),
	CLOVER_4("CLOVER", 4 , "c4"),
	CLOVER_5("CLOVER", 5 , "c5"),
	CLOVER_6("CLOVER", 6 , "c6"),
	CLOVER_7("CLOVER", 7 , "c7"),
	CLOVER_8("CLOVER", 8 , "c8"),
	CLOVER_9("CLOVER", 9 , "c9"),
	CLOVER_10("CLOVER", 10 , "c10"),
	CLOVER_J("CLOVER", 11 , "cJ"),
	CLOVER_Q("CLOVER", 12 , "cQ"),
	CLOVER_K("CLOVER", 13 , "cK"),
	SPADE_A("SPADE", 14 , "sA"),
	SPADE_2("SPADE", 2 , "s2"),
	SPADE_3("SPADE", 3 , "s3"),
	SPADE_4("SPADE", 4 , "s4"),
	SPADE_5("SPADE", 5 , "s5"),
	SPADE_6("SPADE", 6 , "s6"),
	SPADE_7("SPADE", 7 , "s7"),
	SPADE_8("SPADE", 8 , "s8"),
	SPADE_9("SPADE", 9 , "s9"),
	SPADE_10("SPADE", 10 , "s10"),
	SPADE_J("SPADE", 11 , "sJ"),
	SPADE_Q("SPADE", 12 , "sQ"),
	SPADE_K("SPADE", 13 , "sK"),
	HEART_A("HEART", 14 , "hA"),
	HEART_2("HEART", 2 , "h2"),
	HEART_3("HEART", 3 , "h3"),
	HEART_4("HEART", 4 , "h4"),
	HEART_5("HEART", 5 , "h5"),
	HEART_6("HEART", 6 , "h6"),
	HEART_7("HEART", 7 , "h7"),
	HEART_8("HEART", 8 , "h8"),
	HEART_9("HEART", 9 , "h9"),
	HEART_10("HEART", 10 , "h10"),
	HEART_J("HEART", 11 , "hJ"),
	HEART_Q("HEART", 12 , "hQ"),
	HEART_K("HEART", 13 , "hK"),
	
	DIAMOND("DIAMOND", 0, ""),
	CLOVER("CLOVER", 0 ,""),
	SPADE("SPADE", 0, ""),
	HEART("HEART", 0, ""),
	
	A("", 14,""),
	_2("", 2,""),
	_3("", 3,""),
	_4("", 4,""),
	_5("", 5,""),
	_6("", 6,""),
	_7("", 7,""),
	_8("", 8,""),
	_9("", 9,""),
	_10("", 10,""),
	J("", 11,""),
	Q("", 12,""),
	K("", 13,""),
	
	NO("",0,"");
	
	final private String name;
	final private String type;
	final private int number;
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Cards isSameNumber(Cards card) {
		if(this.getNumber() == card.getNumber()) {
			return this;
		}
		return NO;
	}
	
	public Cards isSameType(Cards card) {
		if(this.getType().equals(card.getType())) {
			return this;
		}
		return NO;
	}
	
	public static Cards getCardByName(String name) {
		for(Cards c : Cards.values()) {
			if(c.getName().equals(name)) return c;
		}
		return NO;
	}
	
	private Cards(String type, int number, String name) {
		this.type = type;
		this.number = number;
		this.name = name;
	}
}
