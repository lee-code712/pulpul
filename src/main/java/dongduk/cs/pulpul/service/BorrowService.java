package dongduk.cs.pulpul.service;

import java.util.List;

import dongduk.cs.pulpul.domain.Alert;
import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.ShareThing;

public interface BorrowService {
	
	// 회원 id로 대여 예약 목록 조회
	List<Borrow> getBorrowReservationByMember(String memberId);
	
	// 대여 예약 생성
	boolean makeBorrowReservation(Borrow borrow);
	
	// 대여 예약 취소
	boolean cancelBorrowReservation(Borrow borrow);
	
	// 알림이 생성되고 3일이 지난 대여 예약 삭제
	boolean removeReservationByNotBorrowed();
	
	// 회원 id로 알림 목록 조회
	List<Alert> getAlertByMember(String memberId);
	
	// 회원 id로 대여 목록 조회
	List<Borrow> getBorrowByMember(String memberId, String identity);
	
	// 품목 id로 대여 목록 조회
	List<Borrow> getBorrowByItem(String itemId);
	
	public Borrow getCurrBorrowByItem(String itemId);
	
	// 대여 id로 대여 조회
	Borrow getBorrowById(int borrowId);
	
	// 대여
	boolean borrow(Borrow borrow);
	
	// 대여에 대한 운송장번호 입력
	boolean changeTrackingNumber(Borrow borrow);
	
	// 대여 연장
	boolean extendBorrow(Borrow borrow);
	
	// 반납 (returnItem에서 메소드명 변경)
	boolean returnShareThing(Borrow borrow);
	
}
