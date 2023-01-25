package pethotel.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import pethotel.dto.ApplyDto;
import pethotel.dto.CompanyDto;
import pethotel.dto.ConsultingDto;
import pethotel.dto.ReviewDto;
import pethotel.dto.StarDto;
import pethotel.service.PetHotelService;

@Slf4j
@Controller
public class PetHotelController {
	@Autowired
	private PetHotelService petHotelService;

	// --------------------------qna등록페이지-----------------------
	@GetMapping("/detail.register")
	public ModelAndView consulting() throws Exception {
		ModelAndView mv = new ModelAndView("Business_QnA_regist.html");
		return mv;
	}

	@PostMapping("/register/12")
	public String insertConsulting(ConsultingDto consultingDto) throws Exception {

		petHotelService.insertconsulting(consultingDto);
		return ("redirect:/list");
	}

	// ----------------------qna상세 페이지---------------------------------
	@GetMapping("/detail.openconsultDetail.do")
	public ModelAndView detail(@RequestParam int consultingIdx) throws Exception {

		ConsultingDto consultingDto = petHotelService.detail(consultingIdx);
		ModelAndView mv = new ModelAndView("Business_answer_content.html");

		mv.addObject("detail", consultingDto);
		return mv;

	}

	@PostMapping("/reply/1234")
	public String insertreply(HttpSession session, ConsultingDto consultingDto) throws Exception {
		petHotelService.insertreply(consultingDto);
		return ("redirect:/list");
	}

	// ---------------------qnalist--------------------------------------------
	@GetMapping("/list")
	public ModelAndView consultinglist(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage)
			throws Exception {
		ModelAndView mv = new ModelAndView("Business_QA.html");
		List<ConsultingDto> list = petHotelService.selectConsultingList((currentPage - 1) * 10);
		mv.addObject("list", list);
		mv.addObject("pageCount", Math.ceil(petHotelService.selectConsultingListCount() / 10.0));
		mv.addObject("currentPage", currentPage);
		return mv;
	}

	// --------------------------------업체
	// 등록-----------------------------------------
	@GetMapping("/company")
	public ModelAndView companyregist() throws Exception {
		ModelAndView mv = new ModelAndView("Business_registration.html");
		return mv;
	}

	@PostMapping("/company/regist")
	public String insertcompany(@RequestParam("file") MultipartFile file, CompanyDto companydto) throws Exception {
		petHotelService.insertcompany(file, companydto);
		return ("redirect:/company");
	}

	// ---------------------------메인페이지--------------------------
	@GetMapping("/mainpage")
	public ModelAndView mainpage(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage)
			throws Exception {
		ModelAndView mv = new ModelAndView("/mainpage");

		List<CompanyDto> list = petHotelService.companylist((currentPage - 1) * 4);
		mv.addObject("list", list);
		mv.addObject("pageCount", Math.ceil(petHotelService.selectBoardListCount() / 4.0));
		mv.addObject("currentPage", currentPage);

		return mv;
	}

	// -------------------------회사 상세----------------------------------
	@GetMapping("/detail.company")
	public ModelAndView detailcompany() throws Exception {
		ModelAndView mv = new ModelAndView("Company_detail.html");
		return mv;
	}
	// ----------------------회사 리스트---------------------------------

	@GetMapping("/download.do")
	public void downloadFile(@RequestParam int companyIdx, HttpServletResponse response) throws Exception {
		CompanyDto companyDto = petHotelService.onecompany(companyIdx);
		String companyPhoto = companyDto.getCompanyPhoto();

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			response.setHeader("Content-Disposition", "inline;");

			byte[] buf = new byte[1024];
			fis = new FileInputStream(companyPhoto);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
		} finally {
			bos.close();
			bis.close();
			fis.close();
		}
	}

	// -------------------------------------------------------------업체 상세페이지
	@GetMapping("/companyDetail.do")
	public ModelAndView companydetail(@RequestParam int companyIdx) throws Exception {
		CompanyDto companydetail = petHotelService.companydetail(companyIdx);
		ModelAndView mv = new ModelAndView("company_info_detail.html");

		mv.addObject("companydetail", companydetail);

		CompanyDto reviewlist1 = petHotelService.reviewlist1(companyIdx);
		mv.addObject("reviewlist1", reviewlist1);

		List<ReviewDto> reviewlist2 = petHotelService.reviewlist2(companyIdx);
		mv.addObject("reviewlist2", reviewlist2);
		// 별점 불러오는거
		List<StarDto> starDto = petHotelService.star();
		mv.addObject("star", starDto);

		ReviewDto reviewDto = petHotelService.averageStar(companyIdx);
		mv.addObject("averagestar", reviewDto);
		return mv;
	}

	@PostMapping("/insertCompanyReview")
	public String insertreview(ReviewDto reviewDto) throws Exception {
		petHotelService.insertreview(reviewDto);
		return ("redirect:/companyDetail.do?companyIdx=" + reviewDto.getCompanyIdx());
	}

	// --------------------예약 등록----------------------------
	@PostMapping("/apply")
	public String insertapply(ApplyDto applyDto) throws Exception {
		petHotelService.insertapply(applyDto);
		return ("redirect:/applylist?companyIdx=" + applyDto.getCompanyIdx());
	}

	@GetMapping("/apply123")
	public ModelAndView displayinsert(@RequestParam int companyIdx) throws Exception {
		ModelAndView mv = new ModelAndView("apply.html");
		CompanyDto companyDto = petHotelService.displayinsert(companyIdx);
		mv.addObject("displayinsert", companyDto);
		return mv;
	}

	@GetMapping("/applylist")
	public ModelAndView applylist(
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam int companyIdx) throws Exception {
		ModelAndView mv = new ModelAndView("applylist.html");
		List<ApplyDto> list = petHotelService.applylist((currentPage - 1) * 10, companyIdx);
		mv.addObject("companyIdx", companyIdx);
		mv.addObject("applylist", list);
		mv.addObject("pageCount", Math.ceil(petHotelService.selectApplyListCount() / 10.0));
		mv.addObject("currentPage", currentPage);
		return mv;
	}

	// --------------------예약 확인------------------------
	@GetMapping("/reservation")
	public ModelAndView reservation(@RequestParam int applyIdx) throws Exception {
		ModelAndView mv = new ModelAndView("reservation.html");
		ApplyDto applyDto = petHotelService.reservation(applyIdx);
		mv.addObject("reservation", applyDto);
		return mv;
	}

}