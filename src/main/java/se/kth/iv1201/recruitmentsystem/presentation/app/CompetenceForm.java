package se.kth.iv1201.recruitmentsystem.presentation.app;

import se.kth.iv1201.recruitmentsystem.domain.Competence;

import java.util.List;

/**
 * A bean for the CompetenceForm
 */
public class CompetenceForm {

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    private List<Competence> competences;
}
