package dongduk.cs.pulpul.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dongduk.cs.pulpul.dao.BorrowDao;
import dongduk.cs.pulpul.domain.Alert;
import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.ShareThing;

@Service
public class BorrowServiceImpl implements BorrowService {
	private final BorrowDao borrowDao;

	@Autowired
	public BorrowServiceImpl(BorrowDao borrowDao) {
		this.borrowDao = borrowDao;
	}
	
	@Autowired		// SchedulerConfig에 설정된 TaskScheduler 빈을 주입 받음
	private TaskScheduler scheduler;
	
	@Transactional
	public void reservationCancelScheduler(Date closingTime) {
		Runnable updateTableRunner = new Runnable() { // anonymous class 정의
			@Override
			public void run() {   // 스케쥴러에 의해 미래의 특정 시점에 실행될 작업을 정의			
				Date date = new Date(); // 현재 시각
				// 전체 알림을 가져와서(알림에는 대출 가능 알림만 존재), date + 3, 해당 일자를 지난 값을 갖는 event의 상태를 변경 
				List<Alert> alertList = borrowDao.findAlertByAlertDate();
				for (Alert a : alertList) {
					System.out.println(a.toString());
					// 예약 취소
					Borrow borrow = getBorrowReservationByAlert(a);
					if (borrow != null) {
						cancelBorrowReservation(borrow);
						
						// 다음 예약자가 있다면 is_first_booker를 1로 만든 뒤 알림 생성
						if (checkNumberBorrowReservation(borrow.getShareThing().getItem().getId()) != 0) {
							changeIsFirstBooker(borrow);
							
							Alert alert = new Alert();
							Borrow firstReservation = borrowDao.findFirstBookersBorrowReservation(borrow);
							alert.setMemberId(firstReservation.getBorrower().getId());
							alert.setShareThingId(borrow.getShareThing().getItem().getId());
							borrowDao.createAlert(alert);
						}
						else { // 다음 예약자가 없다면 shareThing is_borrowed를 0으로
							ShareThing shareThing = borrow.getShareThing();
							shareThing.setIsBorrowed(0);
							changeIsBorrowed(shareThing);
						}
					}
				}

				System.out.println("updateTableRunner is executed at " + date);
			}
		};

		// 스케줄 생성: closingTime에 updateTableRunner.run() 메소드 실행
		Calendar cal =  Calendar.getInstance();
		cal.setTime(closingTime);
		cal.add(Calendar.DATE, 3);
		cal.add(Calendar.SECOND, 1);
		closingTime = cal.getTime(); 
		scheduler.schedule(updateTableRunner, closingTime);		
		System.out.println("updateTableRunner has been scheduled to execute at " + closingTime);
	}
	
	// 회원 아이디로 예약 목록 조회
	public List<Borrow> getBorrowReservationByMember(String memberId) {
		return borrowDao.findBorrowReservationByMember(memberId);
	}
	
	// 알림으로 예약 목록 조회
	public Borrow getBorrowReservationByAlert(Alert alert) {
		return borrowDao.findBorrowReservationByAlert(alert);
	}
	
	// 첫 번째 예약자의 예약 정보 조회
	public Borrow getFirstBookersBorrowReservation(Borrow borrow) {
		return borrowDao.findFirstBookersBorrowReservation(borrow);
	}
	
	// 예약 생성
	public boolean makeBorrowReservation(Borrow borrow) {
		int borrowReservationNum = borrowDao.checkNumberBorrowReservation(borrow.getShareThing().getItem().getId());
		if (borrowReservationNum == 0) {
			borrow.setIsFirstBooker(1);
		}
		else if (borrowReservationNum == 1){
			borrow.setIsFirstBooker(0);
		}
		else {
			return false;
		}
		return borrowDao.createBorrowReservation(borrow);
	}
	
	// 예약 취소 - 예약 취소 또는 대여 시작할 때 예약 삭제
	public boolean cancelBorrowReservation(Borrow borrow) {
		boolean result = borrowDao.deleteBorrowReservation(borrow);
		return result;
	}
	
	// 알림 삭제 - 첫번째 대기자인데 예약 취소한 경우, 대여신청을 한 경우
	boolean removeAlert(Alert alert) {
		return borrowDao.deleteAlert(alert);
	}
		
	// 알림 삭제 - 첫번째 예약자인데 3일 동안 대여신청을 안 한 경우
	// 알림 삭제만 할 게 아니라 3일 지났을 때 예약 취소를 실시간으로 확인해야 함
	public boolean removeReservationByNotBorrowed() { // -> 예약삭제도 같이 하기 위해 함수명을 removeReservationByNotBorrowed로 변경
		boolean result = false;
		
		/*String startDate = alert.getAlertDate().substring(0, 10);
		String[] dateArr = startDate.split("-");
		int dday = getDDay(dateArr[0], dateArr[1], dateArr[2]);
		if (dday >= 3) {
			result =  removeAlert(alert);
		}*/
		
		return result;
	}
	
	// 알림 목록 조회
	public List<Alert> getAlertByMember(String memberId) {
		return borrowDao.findAlertByMember(memberId);
	}

	// 회원 아이디로 대여 목록 조회
	public List<Borrow> getBorrowByMember(String memberId, String identity) {
		return borrowDao.findBorrowByMember(memberId, identity);
	}
	
	// 품목 아이디로 대여현황 조회
	public List<Borrow> getBorrowByItem(String itemId) {
		return borrowDao.findBorrowByItem(itemId);
	}
	
	// 현재 대여 중인 품목 대여 현황 조회
	public Borrow getCurrBorrowByItem(String itemId) {
		return borrowDao.findCurrBorrowByItem(itemId);
	}

	// 대여 생성
	public boolean borrow(Borrow borrow) {
		boolean success = borrowDao.createBorrow(borrow);
		
		if (success) {
			// 예약자가 대여 시 isFirstBooker 수정
			if (borrow.getIsFirstBooker() == 1) {
				cancelBorrowReservation(borrow);
				if (checkNumberBorrowReservation(borrow.getShareThing().getItem().getId()) != 0) {
					changeIsFirstBooker(borrow);
				}
				
			}
			return borrowDao.changeIsBorrowed(borrow.getShareThing());
		}
		return false;
	}
	
	// 특정 대여내역에 운송장번호 입력
	public boolean changeTrackingNumber(Borrow borrow) {
		return borrowDao.changeTrackingNumber(borrow);
	}

	// 공유 물품 대여여부 변경 - 사용자가 반납버튼 눌렀을 때 예약자 수 확인
	// 없으면 대여 가능 상태(0)로, 대여하기 클릭 시 대여 중 상태(1)로 변경
	// is_borrowed = 1:대여 불가 0: 대여 하기
	boolean changeIsBorrowed(ShareThing shareThing) { // service에서 예약자 수 확인 후 0, 1 넣어서 전달
		int reservationNumber = borrowDao.checkNumberBorrowReservation(shareThing.getItem().getId());
		if (reservationNumber == 0) {
			shareThing.setIsBorrowed(0);
		}
		else {
			shareThing.setIsBorrowed(1);
		}
		return borrowDao.changeIsBorrowed(shareThing);
		// return false;
	}
	
	// 연장하기 borrow_status가 대여 상태면, 연장 하기 버튼
	// is_extended = 1로 변경하고, 연장 기간 계산해서 returnDate 변경
	public boolean extendBorrow(Borrow borrow) { // -> extendBorrow로 함수명 변경
		borrow.setIsExtended(1);
		
		String returnDate = borrow.getReturnDate();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = transFormat.parse(returnDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); // 시간 설정
		cal.add(Calendar.DATE, 14); // 일 연산
		
		SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyyMMdd");
		returnDate = transFormat2.format(cal.getTime());
		
		borrow.setReturnDate(returnDate);
		
		return borrowDao.changeReturnDate(borrow);
	}

	// 공유 물품 대여 상태 변경 - 반납이 되면 대여한 물품 대여 상태 변경
	// *borrow_status = 1:대여 0:반납
	public boolean returnShareThing(Borrow borrow) { // -> 반납 시 사용하므로 returnShareThings 으로 함수명 변경
		// 예약자가 없는 경우에는 공유 물품 대여 가능 상태 변경
		if (checkNumberBorrowReservation(borrow.getShareThing().getItem().getId()) == 0) {
			ShareThing shareThing = borrow.getShareThing();
			shareThing.setIsBorrowed(0);
			borrowDao.changeIsBorrowed(shareThing);
		}
		else {
			// 첫 번째 예약자에게 알림 생성
			Alert alert = new Alert();
			Borrow firstReservation = borrowDao.findFirstBookersBorrowReservation(borrow);
			alert.setMemberId(firstReservation.getBorrower().getId());
			alert.setShareThingId(borrow.getShareThing().getItem().getId());
			borrowDao.createAlert(alert);
		}
		borrow.setBorrowStatus("0");
		return borrowDao.changeBorrowStatus(borrow);
	}

	// 공유물품 대기자 수 확인 -> 대기자가 꽉 차면 예약 못하니까 대기자가 없거나 한 명만 있으면 예약 가능 -> 예약 시 확인
	int checkNumberBorrowReservation(String itemId) {
		return borrowDao.checkNumberBorrowReservation(itemId);
	}
	
	// is_first_booker 상태 변경, 두번째->첫번째 예약자가 된 경우 -> 필요한 함수에서 사용하는걸로
	boolean changeIsFirstBooker(Borrow borrow) {
		borrow.setIsFirstBooker(1);
		return borrowDao.changeIsFirstBooker(borrow);
	}

	// 알림 생성 - is_first_booker가 1인 경우
	boolean addAlert(Alert alert) {
		return borrowDao.createAlert(alert);
	}

	
	// d-day 계산
	private int getDDay(String year, String month, String day) {
		try {
			Calendar today = Calendar.getInstance();
			Calendar dday = Calendar.getInstance();
			dday.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
				
			long lToday = today.getTimeInMillis() / (24*60*60*1000);
			long lDday = dday.getTimeInMillis() / (24*60*60*1000);
				
			long substract = lDday - lToday;
			return (int)substract;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public Borrow getBorrowById(int borrowId) {
		return borrowDao.findBorrowById(borrowId);
	}

	@Override
	public List<Alert> getAllAlert() {
		return borrowDao.findAllAlert();
	}

}
