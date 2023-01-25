package pethotel.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pethotel.dto.ApplyDto;
import pethotel.dto.CompanyDto;
import pethotel.dto.ConsultingDto;
import pethotel.dto.ReviewDto;
import pethotel.dto.StarDto;
import pethotel.mapper.PetHotelMapper;

@Service
public class PetHotelServiceImpl implements PetHotelService {
	@Autowired
	private PetHotelMapper petHotelMapper;
	
	@Value("${application.upload-path}")
	private String uploadPath;
	

	@Override
	public void insertconsulting(ConsultingDto consultingDto) throws Exception {
		petHotelMapper.insertconsulting(consultingDto);
	}

//	@Override
//	public void insertreply(int consultingIdx) throws Exception {
//		petHotelMapper.insertreply(consultingIdx);
//	}

	

	@Override
	public List<ConsultingDto> selectConsultingList(int offset) throws Exception {
		return petHotelMapper.selectConsultingList(offset);
	}
	@Override
	public int selectConsultingListCount() throws Exception {
		return petHotelMapper.selectConsultingListCount();
	}
	


	@Override
	public void insertreply(ConsultingDto consultingDto) throws Exception {
	
		petHotelMapper.insertreply(consultingDto);
		
	}
	
	@Override
	public ConsultingDto detail(int consultingIdx) throws Exception {
	
		return petHotelMapper.detail(consultingIdx);
	}

	//업체등록버튼
	@Override
	public void insertcompany(MultipartFile file,CompanyDto companydto) throws Exception {
		String savedFilePath = saveFile(file);
		companydto.setCompanyPhoto(savedFilePath);
		petHotelMapper.insertcompany(companydto);
		
	}
	@Override
	public String saveFile(MultipartFile file) throws Exception {
		String savedFilePath = uploadPath + file.getOriginalFilename();
		File uploadFile = new File(savedFilePath);
		file.transferTo(uploadFile);
		
		return savedFilePath;
	}

	@Override
	public List<CompanyDto> companylist(int offset) throws Exception {
		return petHotelMapper.companylist(offset);
	}

	@Override
	public CompanyDto onecompany(int companyIdx) throws Exception {
		
		return petHotelMapper.onecompany(companyIdx);
	}
//디테일보는거
	@Override
	public CompanyDto companydetail(int companyIdx) {
		
		return petHotelMapper.companydetail(companyIdx);
	}

	@Override
	public List<ReviewDto>reviewlist2(int companyIdx) {
		// TODO Auto-generated method stub
		return petHotelMapper.reviewlist2(companyIdx);
	}

	@Override
	public CompanyDto reviewlist1(int companyIdx) {
		// TODO Auto-generated method stub
		return petHotelMapper.reviewlist1(companyIdx);
	}

	@Override
	public void insertreview(ReviewDto reviewDto) {
		petHotelMapper.insertreview(reviewDto);
		
	}

	//예약등록---------------------------------------------------
	@Override
	public void insertapply(ApplyDto applyDto) throws Exception {
		petHotelMapper.insertapply(applyDto);
	}
	//예약상세
	@Override
	public ApplyDto reservation(int applyIdx) throws Exception {
		return petHotelMapper.reservation(applyIdx);
	}
	@Override
	public List<ApplyDto> applylist(int offset,int companyIdx) throws Exception {

		Map<String, Object> map = new HashMap<>();
		map.put("offset", offset);
		map.put("companyIdx", companyIdx);

		return petHotelMapper.applylist(map);
	}
	@Override
	public int selectApplyListCount() throws Exception {
		return petHotelMapper.selectApplyListCount();
	}

	@Override
	public CompanyDto displayinsert(int companyIdx) {
		
		return petHotelMapper.displayinsert(companyIdx);
	}
//페이징

	@Override
	public int selectBoardListCount() throws Exception {
	
		return petHotelMapper.selectBoardListCount();
	}

	@Override
	public List<StarDto> star() throws Exception {

		return petHotelMapper.star();
	}

	@Override
	public ReviewDto averageStar(int companyIdx) {
		// TODO Auto-generated method stub
		if (petHotelMapper.averageStar() == null) {
			return new ReviewDto();
		} else {
			return petHotelMapper.averageStar();
		}
	}





}
