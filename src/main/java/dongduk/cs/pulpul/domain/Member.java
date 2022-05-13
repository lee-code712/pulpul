package dongduk.cs.pulpul.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {

	private String id;
	private String password;
	private String name;
	private String birth;
	private String address;
	private String addressDetail;
	private String zip;
	private String phone;
	private String email;
	private int point;
}
