package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.dao.ReviewDao;
import dongduk.cs.pulpul.domain.Member;
import dongduk.cs.pulpul.domain.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	private final ReviewDao reviewDao;
	
	@Autowired
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	@Override
	public List<Review> getReviewByItem(String itemId) {
		List<Review> reviewList = reviewDao.findReviewByListItem(itemId);
		for (Review review : reviewList) {
			System.out.println(review.getId());
			review.setImageUrl(reviewDao.findReviewImage(review.getId()));
		}
		return reviewList;
	}

	@Override
	public int getOrderIdByNotReview(String itemId, String memberId) {
		return reviewDao.findOrderIdByNotReview(itemId, memberId);
	}

	@Override
	public boolean addReview(Review review, FileCommand uploadFile, String memberId) {
		boolean successed = reviewDao.createReview(review);
		if (successed) {
			if (!uploadFile.getFile().isEmpty()) {
				String filename = uploadFile(uploadFile, review.getId());
				review.setImageUrl("/upload/" + filename);
				review.getOrder().setBuyer(new Member());
				review.getOrder().getBuyer().setId(memberId);
				return reviewDao.createReviewImage(review);
			}
			return true;
		}
		return false;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(FileCommand uploadFile, int reviewId) {
		String newFilename = "";
		// System.out.println(path);
		try {       
			// 확장자를 jpg로 제한
            newFilename = "RIMG-" + reviewId + ".jpg";
            
            File newFile = new File(uploadFile.getPath() + newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFile.getFile().transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}

}
