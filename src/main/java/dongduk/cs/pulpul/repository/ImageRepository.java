package dongduk.cs.pulpul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dongduk.cs.pulpul.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	Image findByMemberIdAndCategoryId(String memberId, String categoryId);
}
