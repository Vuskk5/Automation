package asisimAdvanced.pages.manage.doctors;

import asisimAdvanced.models.Doctor;
import asisimAdvanced.pages.manage.AbstractManagementPage;
import org.openqa.selenium.WebDriver;

public class ManageDoctorsPage extends AbstractManagementPage<ManageDoctorsPage, Doctor> {
    protected ManageDoctorsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void add(Doctor object) {

    }

    @Override
    public void delete(Doctor object) {

    }

    @Override
    public void edit(Doctor object) {

    }

    @Override
    public void select(Doctor object) {

    }

    @Override
    protected ManageDoctorsPage getThis() {
        return this;
    }
}
