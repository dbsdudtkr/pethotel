package pethotel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import pethotel.dto.ApplyDto;
import pethotel.dto.CompanyDto;
import pethotel.dto.ConsultingDto;
import pethotel.dto.ReviewDto;
import pethotel.dto.StarDto;
@Mapper
public interface PetHotelMapper {


	List<StarDto> star();
	//	List<ConsultingDto> insertreply(int consultingIdx) throws Exception;
	void insertconsulting(ConsultingDto consultingDto);
	void insertreply(ConsultingDto consultingDto)throws Exception;

	List<ConsultingDto> selectConsultingList(int offset) throws Exception;
	
	ConsultingDto detail(int consultingIdx) throws Exception;
	//사진 등록 파트
	void insertcompany(CompanyDto companyDto)throws Exception;
	List<CompanyDto> companylist(int offset)throws Exception;
	CompanyDto onecompany(int companyIdx) throws Exception;

	int selectConsultingListCount() throws Exception;
	
	//디테일
	CompanyDto companydetail(int companyIdx);
	List<ReviewDto> reviewlist2(int companyIdx);
	CompanyDto reviewlist1(int companyIdx);
	void insertreview(ReviewDto reviewDto);
	//예약확인
	void insertapply(ApplyDto applyDto) throws Exception;
	
	ApplyDto reservation(int applyIdx) throws Exception;
	List<ApplyDto> applylist(Map<String, Object> map) throws Exception;
	int selectApplyListCount() throws Exception;
	CompanyDto displayinsert(int companyIdx);
	//페이징
	int selectBoardListCount() throws Exception;
	ReviewDto averageStar();


}
