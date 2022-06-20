package dongduk.cs.pulpul.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import dongduk.cs.pulpul.domain.Alert;
import dongduk.cs.pulpul.domain.Borrow;
import dongduk.cs.pulpul.domain.ShareThing;

@Mapper
public interface BorrowMapper {

	List<Borrow> selectBorrowReservationByMemberId(String memberId);
	
	List<Borrow> selectBorrowReservationByItemId(String itemId);
	
	int insertBorrowReservation(Borrow borrow);
	
	int deleteBorrowReservation(Borrow borrow);
	
	int selectReservationNumber(String itemId);
	
	int updateIsFirstBooker(Borrow borrow);
	
	List<Alert> selectAlertByAlertDate();
	
	List<Alert> selectAlertByMemberId(String memberId);
	
	int selectAlertCountByIsRead(String memberId); // 추가
	
	int insertAlert(Alert alert);
	
	int deleteAlert(Alert alert);
	
	int updateIsRead(Alert alert); // 추가
	
	List<Borrow> selectBorrowByMemberId(String memberId, String identity);
	
	List<Borrow> selectBorrowByItemId(String itemId);
	
	int insertBorrow(Borrow borrow);
	
	int updateTrackingNumber(Borrow borrow);
	
	int updateBorrowStatus(Borrow borrow);
	
	int updateReturnDate(Borrow borrow);

//	int updateIsBorrowed(ShareThing shareThing);

	Borrow selectBorrowById(int borrowId);

	Borrow selectCurrBorrowByItem(String itemId);

	List<Alert> selectAllAlert();

	Borrow selectFirstBookersBorrowReservation(Borrow borrow);

	Borrow selectBorrowReservationByAlert(Alert a);
}
