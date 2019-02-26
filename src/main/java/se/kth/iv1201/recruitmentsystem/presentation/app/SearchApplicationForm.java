package se.kth.iv1201.recruitmentsystem.presentation.app;

import se.kth.iv1201.recruitmentsystem.domain.ApplicationDTO;

import java.util.List;

/**
 * A bean for the search application form.
 */
public class SearchApplicationForm {

    public List<ApplicationDTO> getApplicationDTOList() {
        return applicationDTOList;
    }

    public void setApplicationDTOList(List<ApplicationDTO> applicationDTOList) {
        this.applicationDTOList = applicationDTOList;
    }

    private List<ApplicationDTO> applicationDTOList;

}
