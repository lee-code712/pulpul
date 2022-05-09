package dongduk.cs.pulpul.service;

import dongduk.cs.pulpul.domain.Member;

public interface MemberService {
	
	// 로그인
	Member login(Member member);
	
	// 회원가입
	boolean register(Member member);
	
	// 회원 정보 조회
	Member getMember(String memberId);
	
	// 회원 정보 수정
	boolean changeMemberInfo(Member member);
	
	// 회원탈퇴
	boolean resign(Member member);
	
}
