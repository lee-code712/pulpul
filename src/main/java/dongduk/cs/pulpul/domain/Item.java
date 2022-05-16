package dongduk.cs.pulpul.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {

	private String id; /*품목 식별번호*/
	private String name; /*품목 이름*/
	private String uploadDate; /*품목 생성일*/
	private String description; /*품목 설명*/
	private int openStatus; /*품목 공개여부*/
	private Market market; /*마켓 정보*/
	private String thumbnailUrl; /*품목 대표 이미지 경로*/
	private List<String> imageUrlList; /*품목 이미지 경로 리스트*/
	
}
