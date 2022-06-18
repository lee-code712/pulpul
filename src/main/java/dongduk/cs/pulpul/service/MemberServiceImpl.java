package dongduk.cs.pulpul.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dongduk.cs.pulpul.dao.MemberDao;
import dongduk.cs.pulpul.domain.Market;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.repository.MemberRepository;
import dongduk.cs.pulpul.service.exception.ChangePwdException;
import dongduk.cs.pulpul.service.exception.LoginException;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public Member login(Member member) throws LoginException {

		Optional<Member> result = memberRepo.findById(member.getId());
		
		Member findMember = null;
		if(result.isPresent()) findMember = result.get();

		if (findMember == null) {
			throw new LoginException("존재하지 않는 회원입니다.");
		}
		
		if (!member.getPassword().equals(findMember.getPassword())) {
			throw new LoginException("비밀번호가 일치하지 않습니다.");
		}

		findMember.setId(member.getId());
		
		return findMember;
	}
	
	@Override
	public boolean register(Member member) {
		Member result = memberRepo.save(member);
		if (result != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public void changeMemberInfo(Member member) {
		Member findMember = getMember(member.getId());
		
		if (findMember != null) {
			member.setPoint(findMember.getPoint());
			memberRepo.save(member);
		}
	}
	
	@Override
	public Member getMember(String memberId) {
		Optional<Member> result = memberRepo.findById(memberId);
		
		Member findMember = null;
		if(result.isPresent()) findMember = result.get();
		
		return findMember;
	}
	
	@Override
	public void resign(Member member) {
		memberRepo.delete(member);
	}
	
	@Override
	@Transactional
	public void changePoint(Member member, int status, int point) { // -> 필요한 함수에 넣어서 사용
		Optional<Member> result = memberRepo.findById(member.getId());
		
		Member findMember = null;
		if(result.isPresent()) findMember = result.get();
		
		if (findMember != null) {
			int updatedPoint = findMember.getPoint();
			if (status == 1) {
				updatedPoint += point;
			}
			else {
				updatedPoint -= point;
			}
			
			
			memberRepo.updatePoint(updatedPoint, member.getId());
		}
	}
}

