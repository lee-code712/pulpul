package dongduk.cs.pulpul.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@SuppressWarnings("serial")
public class Member implements Serializable {

	private String id;
	private String password;
	private String passwordCheck;
	private String name;
	private String birth;
	private String address;
	private String addressDetail;
	private String zip;
	private String phone;
	private String email;
	private int point;
}
