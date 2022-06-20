package dongduk.cs.pulpul.service;

import java.util.Date;
import java.util.List;

import dongduk.cs.pulpul.domain.Alert;
import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.ShareThing;

public interface BorrowService {
	// 예약 자동 취소
	public void reservationCancelScheduler(Date closingTime);
	
	// 회원 id로 대여 예약 목록 조회
	List<Borrow> getBorrowReservationByMember(String memberId);
	
	// 대여 예약 생성
	boolean makeBorrowReservation(Borrow borrow);
	
	// 대여 예약 취소
	boolean cancelBorrowReservation(Borrow borrow);
	
	// 전체 알림 목록 조회
	List<Alert> getAllAlert();
	
	// 회원 id로 알림 목록 조회
	List<Alert> getAlertByMember(String memberId);
	
	// 회원의 읽지 않은 알림 개수
	int getAlertCountByIsRead(String memberId);
	
	// 알림 읽음 처리
	boolean changeIsRead(Alert alert);
	
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
