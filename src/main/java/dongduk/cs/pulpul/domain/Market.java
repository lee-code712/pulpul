package dongduk.cs.pulpul.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Market implements Serializable {

	private int id; /*마켓 식별 번호*/
	@NotBlank
	private String name; /*마켓 이름*/
	@NotBlank
	private String intro; /*마켓 소개*/
	@NotBlank
	private String contactableTime; /*연락가능 시간*/
	private String policy; /*교환/반품/환불 정책*/
	private String precaution; /*구매 전 유의사항*/
	private int openStatus; /*마켓 공개여부*/
	private Member member; /*회원정보*/
	private String imageUrl; /*마켓 이미지 경로*/
	
}
