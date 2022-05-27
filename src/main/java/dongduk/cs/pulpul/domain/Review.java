package dongduk.cs.pulpul.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Review {

	private int id; /*후기 식별 번호*/
	private String writeDate;
	private String content;
	private int rating; 
	private Order order; /*주문 정보*/
	private Item item; /*주문 품목*/
	private List<String> imageUrlList; /*후기 이미지 경로*/
}
