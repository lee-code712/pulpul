package dongduk.cs.pulpul.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dongduk.cs.pulpul.dao.mybatis.mapper.MemberMapper;
import dongduk.cs.pulpul.domain.Member;

@Component
public class MemberDaoImpl implements MemberDao {

	@Autowired
    private MemberMapper memberMapper;
	
	@Override
	public Member findMember(String memberId) {
		return memberMapper.selectMemberById(memberId);
	}

	@Override
	public boolean createMember(Member member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changeMemberInfo(Member member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMember(Member member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean changePoint(String memberId, int status, int point) {
		// TODO Auto-generated method stub
		return false;
	}

}
