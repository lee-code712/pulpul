package dongduk.cs.pulpul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.service.exception.ChangePwdException;
import dongduk.cs.pulpul.service.exception.LoginException;

@Service
public class MemberServiceImpl implements MemberService {
	private final MemberDao memberDao;
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public Member login(Member member) throws LoginException {

		Member findMember = memberDao.findMember(member.getId());

		if (findMember == null) {
			throw new LoginException("존재하지 않는 회원입니다.");
		}
		
		if (!member.getPassword().equals(findMember.getPassword())) {
			throw new LoginException("비밀번호가 일치하지 않습니다.");
		}

		findMember.setId(member.getId());
		
		return findMember;
	}
	
	public boolean register(Member member) {
		return memberDao.createMember(member);
	}
	
	public boolean changeMemberInfo(Member member) {
		return memberDao.changeMemberInfo(member);
	}
	
	public Member getMember(String memberId) {
		return memberDao.findMember(memberId);
	}
	
	public boolean resign(Member member) {
		return memberDao.deleteMember(member);
	}
	
	public boolean changePoint(Member member, int status, int point) { // -> 필요한 함수에 넣어서 사용
		return memberDao.changePoint(member.getId(), status, point);
	}
	
	public boolean changePassword(String memberId, String oldPwd, String newPwd, String newPwdCk) throws ChangePwdException { // -> changePassword DAO 메소드가 없어서 회원정보 수정하려면 무조건 비밀번호로 확인해야 하는 걸로 변경
		Member member = memberDao.findMember(memberId);
		if (member != null) {
			if (!member.getPassword().equals(oldPwd)) {
				System.out.println(member.getPassword() + " " + oldPwd);
				throw new ChangePwdException("기존 비밀번호가 일치하지 않습니다.");
			}
			else if (!newPwd.equals(newPwdCk)) {
				throw new ChangePwdException("새 비밀번호 확인 값이 일치하지 않습니다.");
			}
			else {
				member.setPassword(newPwd);
				return memberDao.changeMemberInfo(member);
			}
		}
		return false;
	}
}

