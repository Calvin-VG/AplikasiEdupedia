package com.example.edupedia;

public class MenuInfoPendidikan {
    private String description;
    private String reason;
    private String cost_of_living;
    private String education;
    private String study_provisions;
    private String university;

    public MenuInfoPendidikan(String description, String reason, String cost_of_living, String education, String study_provisions, String university) {
        this.description = description;
        this.reason = reason;
        this.cost_of_living = cost_of_living;
        this.education = education;
        this.study_provisions = study_provisions;
        this.university = university;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCost_of_living() {
        return cost_of_living;
    }

    public void setCost_of_living(String cost_of_living) {
        this.cost_of_living = cost_of_living;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getStudy_provisions() {
        return study_provisions;
    }

    public void setStudy_provisions(String study_provisions) {
        this.study_provisions = study_provisions;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
