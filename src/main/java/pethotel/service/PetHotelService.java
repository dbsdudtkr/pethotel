package pethotel.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pethotel.dto.ApplyDto;
import pethotel.dto.CompanyDto;
import pethotel.dto.ConsultingDto;
import pethotel.dto.ReviewDto;
import pethotel.dto.StarDto;

public interface PetHotelService {
//	List<ConsultingDto>insertconsulting ()throws Exception;

	//qna
	void insertconsulting(ConsultingDto consultingDto)throws Exception;

	void insertreply(ConsultingDto consultingDto)throws Exception;

	ConsultingDto detail(int consultingIdx) throws Exception;
	List<ConsultingDto> selectConsultingList(int offset) throws Exception;
	int selectConsultingListCount() throws Exception;
	//업체등록(사진)
	void insertcompany( MultipartFile file,CompanyDto companydto)throws Exception;
	String saveFile(MultipartFile file) throws Exception;



	CompanyDto onecompany(int companyIdx)throws Exception;
//----------------------------------컴퍼니 디테일-----------------------------
	CompanyDto companydetail(int companyIdx);

	List<ReviewDto> reviewlist2(int companyIdx);

	CompanyDto reviewlist1(int companyIdx);

	void insertreview(ReviewDto reviewDto);

	
	//예약등록-------------------------------------------------------------------
		void insertapply(ApplyDto applyDto) throws Exception;
		//예약목록
		List<ApplyDto> applylist(int offset,int companyIdx) throws Exception;
		//예약상세
		public ApplyDto reservation(int applyIdx)throws Exception;

		CompanyDto displayinsert(int companyIdx);
		int selectApplyListCount() throws Exception;
//페이징-------------------------
		int selectBoardListCount() throws Exception;

		List<CompanyDto> companylist(int offset)throws Exception;

		List<StarDto> star()throws Exception;

		ReviewDto averageStar(int companyIdx);

	


}
