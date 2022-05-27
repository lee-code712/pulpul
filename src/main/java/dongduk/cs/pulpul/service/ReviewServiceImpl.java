package dongduk.cs.pulpul.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dongduk.cs.pulpul.controller.FileCommand;
import dongduk.cs.pulpul.dao.ReviewDao;
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
		return reviewDao.findReviewByListItem(itemId);
	}

	@Override
	public int getOrderIdByNotReview(String itemId, String memberId) {
		return reviewDao.findOrderIdByNotReview(itemId, memberId);
	}

	@Override
	public boolean addReview(Review review, FileCommand uploadFiles) {
		int reviewId = reviewDao.createReview(review);
		if (reviewId > 0) {
			review.setId(reviewId);
			List<String> imageUrlList = new ArrayList<String>();
			int cnt = 1;
			for (MultipartFile uploadFile : uploadFiles.getFiles()) {
				if (!uploadFile.isEmpty()) {
					uploadFiles.setFile(uploadFile);
					String filename = uploadFile(uploadFiles, review.getId(), cnt);
					imageUrlList.add("/upload/" + filename);
					cnt++;
				}
			}
			String memberId = review.getOrder().getBuyer().getId();
			return reviewDao.createReviewImages(imageUrlList, memberId);
		}
		return false;
	}
	
	// 파일 업로드 메소드
	public String uploadFile(FileCommand uploadFiles, int reviewId, int cnt) {
		String newFilename = "";
		// System.out.println(path);
		try {       
			// 확장자를 jpg로 제한
            newFilename = reviewId + "-" + cnt + ".jpg";
            
            File newFile = new File(uploadFiles.getPath(), newFilename);
            if (newFile.exists())
            	newFile.delete();
            uploadFiles.getFile().transferTo(newFile);

        }catch(Exception e) {            
            e.printStackTrace();
        }
		
		return newFilename;
	}
}
