package se.kth.iv1201.recruitmentsystem.presentation.app;

import se.kth.iv1201.recruitmentsystem.domain.ApplicationDTO;

import java.util.List;

/**
 * A bean for the search application.
 */
public class SearchApplication {

    public List<ApplicationDTO> getApplicationDTOList() {
        return applicationDTOList;
    }

    public void setApplicationDTOList(List<ApplicationDTO> applicationDTOList) {
        this.applicationDTOList = applicationDTOList;
    }
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private String lang;

    private List<ApplicationDTO> applicationDTOList;

}
