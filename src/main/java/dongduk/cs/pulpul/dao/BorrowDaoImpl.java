package dongduk.cs.pulpul.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.BorrowMapper;
import dongduk.cs.pulpul.domain.Alert;
import dongduk.cs.pulpul.domain.Borrow;

@Component
public class BorrowDaoImpl implements BorrowDao {

	@Autowired
	private BorrowMapper borrowMapper;
	
	@Override
	public List<Borrow> findBorrowReservationByMember(String memberId) {
		// TODO Auto-generated method stub
		return borrowMapper.selectBorrowReservationByMemberId(memberId);
	}

	@Override
	public boolean createBorrowReservation(Borrow borrow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBorrowReservation(Borrow borrow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int checkNumberBorrowReservation(String itemId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean changeIsFirstBooker(Borrow borrow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Alert> findAlertByAlertDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alert> findAlertByMember(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findAlertCountByIsRead(String memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean createAlert(Alert alert) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAlert(Alert alert) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeIsRead(String memberId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Borrow> findBorrowByMember(String memberId, String identity) {
		// TODO Auto-generated method stub
		return borrowMapper.selectBorrowByMemberId(memberId, identity);
	}

	@Override
	public List<Borrow> findBorrowByItem(String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createBorrow(Borrow borrow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeTrackingNumber(Borrow borrow) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeBorrowStatus(int orderId, int borrowStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeReturnDate(Borrow borrow) {
		// TODO Auto-generated method stub
		return false;
	}

	
}