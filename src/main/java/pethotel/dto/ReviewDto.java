package pethotel.dto;

import lombok.Data;

@Data
public class ReviewDto {

    private int reviewIdx;
    private String reviewWriter;
    private String reviewContents;
    private int  companyIdx;
    private int starIdx;
    private float averageStar;
}